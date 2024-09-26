package com.example.mydicodingevent.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydicodingevent.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {
    private val favoriteViewModel by viewModels<FavoriteViewModel>()
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FavoriteEventAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val layoutManagerFavorite = LinearLayoutManager(this.context)
        binding.rvEventFavorite.layoutManager = layoutManagerFavorite
        val itemDecorationFavorite = DividerItemDecoration(requireContext(), layoutManagerFavorite.orientation)
        binding.rvEventFavorite.addItemDecoration(itemDecorationFavorite)
        adapter = FavoriteEventAdapter()
        binding.rvEventFavorite.adapter = adapter

        favoriteViewModel.getAllFavoriteEvent().observe(viewLifecycleOwner) { favoriteEvents ->
            adapter.submitList(favoriteEvents)
        }

        favoriteViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
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