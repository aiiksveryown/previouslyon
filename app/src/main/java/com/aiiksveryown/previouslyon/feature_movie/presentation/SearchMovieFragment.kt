package com.aiiksveryown.previouslyon.feature_movie.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aiiksveryown.previouslyon.R
import com.aiiksveryown.previouslyon.databinding.FragmentSearchMovieBinding
import com.aiiksveryown.previouslyon.feature_movie.presentation.adapter.MovieAdapter
import com.aiiksveryown.previouslyon.feature_movie.presentation.viewmodel.MovieViewModel
import com.aiiksveryown.previouslyon.util.network.Resource
import com.aiiksveryown.previouslyon.util.utils.showSnackbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchMovieFragment : Fragment() {

    private var _binding : FragmentSearchMovieBinding? = null
    private val binding get() = _binding!!
    private val viewModel : MovieViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieAdapter = MovieAdapter(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView : BottomNavigationView = requireActivity().findViewById(R.id.bottomMenu)
        bottomNavigationView.visibility = View.GONE

        with(binding) {

            recyclerViewMovie.apply {
                adapter = movieAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }

            toolBar.setNavigationOnClickListener { findNavController().navigateUp() }

            etSearch.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val searchQuery = etSearch.text.toString()
                    if (searchQuery.isEmpty()) {
                        showSnackbar(requireView(), getString(R.string.empty_search_query))
                    } else {
                        viewModel.movieSearch(searchQuery)
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

            viewModel.movieSearch.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        hideLoading()
                        response.data?.let { movieList ->
                            movieAdapter.submitMovie(movieList)
                        }
                    }
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Failure -> {
                        showSnackbar(requireView(), "${response.message}", Snackbar.LENGTH_LONG)
                        hideLoading()
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