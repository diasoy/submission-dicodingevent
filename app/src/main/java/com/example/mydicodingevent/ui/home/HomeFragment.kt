package com.example.mydicodingevent.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydicodingevent.databinding.FragmentHomeBinding
import com.example.mydicodingevent.ui.EventAdapter
import com.example.mydicodingevent.ui.finished.FinishedViewModel
import com.example.mydicodingevent.ui.upcoming.UpcomingViewModel

class HomeFragment : Fragment() {
    private val upcomingViewModel by viewModels<UpcomingViewModel>()
    private val finishedViewModel by viewModels<FinishedViewModel>()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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

        upcomingViewModel.listEvent.observe(viewLifecycleOwner) { events ->
            adapterUpcoming.submitList(events)
        }
        finishedViewModel.listEvent.observe(viewLifecycleOwner) { events ->
            adapterFinished.submitList(events)
        }
        upcomingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        finishedViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        // Set up search functionality
        binding.searchViewHome.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredUpcoming = upcomingViewModel.listEvent.value?.filter {
                    it.name.contains(newText ?: "", ignoreCase = true)
                }
                val filteredFinished = finishedViewModel.listEvent.value?.filter {
                    it.name.contains(newText ?: "", ignoreCase = true)
                }
                adapterUpcoming.submitList(filteredUpcoming)
                adapterFinished.submitList(filteredFinished)
                return true
            }
        })

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