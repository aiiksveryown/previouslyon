package com.aiiksveryown.previouslyon.ui.shows_lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.aiiksveryown.previouslyon.databinding.FragmentShowListBinding
import com.aiiksveryown.previouslyon.feature.tv_show.data.api.Media
import com.aiiksveryown.previouslyon.ui.setTitle
import com.aiiksveryown.previouslyon.ui.shows_lists.adapter.LoadUIStateAdapter
import com.aiiksveryown.previouslyon.ui.shows_lists.adapter.ShowsListAdapter
import com.aiiksveryown.previouslyon.util.collect
import com.aiiksveryown.previouslyon.util.setSpanSize
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShowsListFragment : Fragment() {

    private val allMediaAdapter by lazy { ShowsListAdapter(MediaComparator) }

    private var _binding: FragmentShowListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ShowsListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(true, "Tv Shows")
        setMediaAdapter()
    }

    private fun setMediaAdapter() {
        val footerAdapter = LoadUIStateAdapter(allMediaAdapter::retry)
        binding.recyclerMedia.adapter = allMediaAdapter.withLoadStateFooter(footerAdapter)
        val mManager = binding.recyclerMedia.layoutManager as GridLayoutManager
        mManager.setSpanSize<ShowsListAdapter>(footerAdapter, allMediaAdapter, mManager.spanCount)
        collectData()
        collect(flow = allMediaAdapter.loadStateFlow,
            action = { })
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewModel.showList.collect {
                viewModel.showList.value.media.collect() {
                    allMediaAdapter.submitData(it)
                }
            }
        }
    }

    object MediaComparator : DiffUtil.ItemCallback<Media>() {
        override fun areItemsTheSame(oldItem: Media, newItem: Media) =
            oldItem.mediaID == newItem.mediaID

        override fun areContentsTheSame(oldItem: Media, newItem: Media) =
            oldItem == newItem
    }
}
