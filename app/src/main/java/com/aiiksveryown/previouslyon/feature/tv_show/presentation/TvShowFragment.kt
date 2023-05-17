package com.aiiksveryown.previouslyon.feature.tv_show.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aiiksveryown.previouslyon.R
import com.aiiksveryown.previouslyon.databinding.FragmentTvShowBinding
import com.aiiksveryown.previouslyon.feature.tv_show.presentation.adapter.TvShowAdapter
import com.aiiksveryown.previouslyon.feature.tv_show.presentation.viewmodel.TvShowViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowFragment : Fragment() {

    private var _binding : FragmentTvShowBinding? = null
    private val binding get() = _binding!!
    private val viewModel : TvShowViewModel by viewModels()
    private lateinit var tvShowAdapter: TvShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvShowAdapter = TvShowAdapter(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView : BottomNavigationView = requireActivity().findViewById(R.id.bottomMenu)
        bottomNavigationView.visibility = View.VISIBLE

        with(binding) {

            fabSearch.setOnClickListener { findNavController().navigate(R.id.action_tvShowFragment_to_tvShowSearchFragment) }

            tvShowRecyclerView.apply {
                adapter = tvShowAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }

            viewModel.getAllTvShows.observe(viewLifecycleOwner) { tvShowList ->
                tvShowAdapter.submitTvShowEntity(tvShowList)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}