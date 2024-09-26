package com.example.mydicodingevent.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mydicodingevent.database.FavoriteEvent
import com.example.mydicodingevent.databinding.ItemEventFavoriteBinding
import com.example.mydicodingevent.ui.detail.DetailEventActivity

class FavoriteEventAdapter : ListAdapter<FavoriteEvent, FavoriteEventAdapter.FavoriteEventViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteEventViewHolder {
        val binding = ItemEventFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteEventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteEventViewHolder, position: Int) {
        val favoriteEvent = getItem(position)
        holder.bind(favoriteEvent)
    }

    class FavoriteEventViewHolder(private val binding: ItemEventFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteEvent: FavoriteEvent) {
            with(binding) {
                titleEventFavorite.text = favoriteEvent.name
                Glide.with(itemView.context)
                    .load(favoriteEvent.mediaCover)
                    .into(imgEventFavorite)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailEventActivity::class.java)
                    intent.putExtra("EVENT_ID", favoriteEvent.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteEvent>() {
            override fun areItemsTheSame(oldItem: FavoriteEvent, newItem: FavoriteEvent): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FavoriteEvent, newItem: FavoriteEvent): Boolean {
                return oldItem == newItem
            }
        }
    }
}