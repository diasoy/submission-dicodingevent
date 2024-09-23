package com.example.mydicodingevent.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydicodingevent.databinding.FragmentUpcomingBinding
import com.example.mydicodingevent.ui.EventAdapter

class UpcomingFragment : Fragment() {
    private val upcomingViewModel by viewModels<UpcomingViewModel>()
    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Set up vertical RecyclerView for upcoming events
        val layoutManagerUpcoming = LinearLayoutManager(this.context)
        binding.rvEventUpcoming.layoutManager = layoutManagerUpcoming
        val itemDecorationUpcoming = DividerItemDecoration(requireContext(), layoutManagerUpcoming.orientation)
        binding.rvEventUpcoming.addItemDecoration(itemDecorationUpcoming)
        val adapterUpcoming = EventAdapter(EventAdapter.Type.NONE)
        binding.rvEventUpcoming.adapter = adapterUpcoming

        upcomingViewModel.listEvent.observe(viewLifecycleOwner) { events ->
            adapterUpcoming.submitList(events)
        }
        upcomingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        upcomingViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
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