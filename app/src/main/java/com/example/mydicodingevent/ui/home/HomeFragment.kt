package com.example.mydicodingevent.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydicodingevent.databinding.FragmentHomeBinding
import com.example.mydicodingevent.ui.EventAdapter

class HomeFragment : Fragment() {
    private val homeViewModel by viewModels<HomeViewModel>()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Set up horizontal RecyclerView for upcoming events
        val layoutManagerUpcoming = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvEventUpcoming.layoutManager = layoutManagerUpcoming
        val itemDecorationUpcoming = DividerItemDecoration(requireContext(), layoutManagerUpcoming.orientation)
        binding.rvEventUpcoming.addItemDecoration(itemDecorationUpcoming)
        val adapterUpcoming = EventAdapter(EventAdapter.Type.UPCOMING)
        binding.rvEventUpcoming.adapter = adapterUpcoming

        // Set up vertical RecyclerView for finished events
        val layoutManagerFinished = LinearLayoutManager(this.context)
        binding.rvEventFinished.layoutManager = layoutManagerFinished
        val itemDecorationFinished = DividerItemDecoration(requireContext(), layoutManagerFinished.orientation)
        binding.rvEventFinished.addItemDecoration(itemDecorationFinished)
        val adapterFinished = EventAdapter(EventAdapter.Type.FINISHED)
        binding.rvEventFinished.adapter = adapterFinished

        // Observe upcoming events
        homeViewModel.upcomingEvents.observe(viewLifecycleOwner) { events ->
            adapterUpcoming.submitList(events)
        }

        // Observe finished events
        homeViewModel.finishedEvents.observe(viewLifecycleOwner) { events ->
            adapterFinished.submitList(events)
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        homeViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }

        return root
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}