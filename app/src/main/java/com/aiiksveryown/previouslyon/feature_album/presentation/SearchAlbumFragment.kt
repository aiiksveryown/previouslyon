package com.aiiksveryown.previouslyon.feature_album.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.aiiksveryown.previouslyon.R
import com.aiiksveryown.previouslyon.databinding.FragmentSearchAlbumBinding
import com.aiiksveryown.previouslyon.feature_album.presentation.viewmodel.AlbumViewModel
import com.aiiksveryown.previouslyon.feature_album.presentation.adapter.AlbumAdapter
import com.aiiksveryown.previouslyon.util.network.Resource
import com.aiiksveryown.previouslyon.util.utils.showSnackbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchAlbumFragment : Fragment() {

    private var _binding: FragmentSearchAlbumBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AlbumViewModel by viewModels()
    private lateinit var albumAdapter: AlbumAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        albumAdapter = AlbumAdapter(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView: BottomNavigationView =
            requireActivity().findViewById(R.id.bottomMenu)
        bottomNavigationView.visibility = View.GONE

        with(binding) {
            toolBar.setNavigationOnClickListener { findNavController().navigateUp() }
            recyclerViewAlbum.apply {
                adapter = albumAdapter
                layoutManager = GridLayoutManager(requireContext(), 2)
            }

            etSearch.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val searchQuery = etSearch.text.toString()
                    if (searchQuery.isEmpty()) {
                        showSnackbar(requireView(), getString(R.string.empty_search_query))
                    } else {
                        viewModel.searchAlbum(searchQuery)
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

            viewModel.albumSearch.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        hideLoading()
                        response.data?.let { album ->
                            albumAdapter.submitAlbum(album)
                        }
                    }
                    is Resource.Failure -> {
                        hideLoading()
                        showSnackbar(requireView(), "${response.message}", Snackbar.LENGTH_LONG)
                    }
                    is Resource.Loading -> {
                        showLoading()
                    }
                }
            }
        }

    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}