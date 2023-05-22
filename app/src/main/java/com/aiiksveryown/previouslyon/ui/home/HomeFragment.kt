package com.aiiksveryown.previouslyon.ui.home

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aiiksveryown.previouslyon.R
import com.aiiksveryown.previouslyon.databinding.FragmentHomeBinding
import com.aiiksveryown.previouslyon.ui.home.adapter.TrendAdapter
import com.aiiksveryown.previouslyon.ui.setTitle
import com.aiiksveryown.previouslyon.util.network.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(true, resources.getString(R.string.explore_label))

        collectEvent()
        setUpUi()
        binding.recyclerTrend.adapter = TrendAdapter()
    }

    private fun setUpUi() {
        binding.buttonRetry.setOnClickListener {
            binding.groupLostConnection.isVisible = false
            binding.animationLoading.isVisible = true
            viewModel.getDailyTrending()
        }
        binding.cardSeries.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToShowsListFragment())
        }
    }

    private fun collectEvent() {
        viewModel.dailyTrending.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    binding.animationLoading.isVisible = false
                    binding.recyclerTrend.isVisible = true
                    (binding.recyclerTrend.adapter as TrendAdapter).updateList(response.data!!)
                }

                is Resource.Failure -> {
                    binding.groupLostConnection.isVisible = true
                    binding.animationLoading.isVisible = false
                    binding.recyclerTrend.isVisible = false

                }

                is Resource.Loading -> binding.animationLoading.isVisible = false
            }
        }
    }
}