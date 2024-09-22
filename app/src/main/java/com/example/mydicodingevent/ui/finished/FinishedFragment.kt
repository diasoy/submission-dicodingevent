package com.example.mydicodingevent.ui.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mydicodingevent.databinding.FragmentFinishedBinding
import com.example.mydicodingevent.ui.EventAdapter

class FinishedFragment : Fragment() {
    private val finishedViewModel by viewModels<FinishedViewModel>()
    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Set up vertical RecyclerView for finished events
        val layoutManagerFinished = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvEventFinished.layoutManager = layoutManagerFinished
        val itemDecorationFinished = DividerItemDecoration(requireContext(), layoutManagerFinished.orientation)
        binding.rvEventFinished.addItemDecoration(itemDecorationFinished)
        val adapterFinished = EventAdapter(EventAdapter.Type.NONE)
        binding.rvEventFinished.adapter = adapterFinished

        finishedViewModel.listEvent.observe(viewLifecycleOwner) { events ->
            adapterFinished.submitList(events)
        }
        finishedViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        // Set up search functionality
        binding.searchViewFinished.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredFinished = finishedViewModel.listEvent.value?.filter {
                    it.name.contains(newText ?: "", ignoreCase = true)
                }
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