package com.example.mydicodingevent.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mydicodingevent.data.response.ListEventsItem
import com.example.mydicodingevent.databinding.ItemEventBinding
import com.example.mydicodingevent.databinding.ItemEventFinishedBinding
import com.example.mydicodingevent.databinding.ItemEventUpcomingBinding
import com.example.mydicodingevent.ui.detail.DetailEventActivity

class EventAdapter(private val type: Type) : ListAdapter<ListEventsItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    enum class Type {
        UPCOMING, FINISHED, NONE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (type) {
            Type.UPCOMING -> {
                val binding = ItemEventUpcomingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                UpcomingViewHolder(binding)
            }
            Type.FINISHED -> {
                val binding = ItemEventFinishedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                FinishedViewHolder(binding)
            }
            Type.NONE -> {
                val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                EventViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val event = getItem(position)
        when (holder) {
            is UpcomingViewHolder -> holder.bind(event)
            is FinishedViewHolder -> holder.bind(event)
            is EventViewHolder -> holder.bind(event)
        }
    }

    inner class UpcomingViewHolder(private val binding: ItemEventUpcomingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            Glide.with(itemView.context)
                .load(event.imageLogo)
                .into(binding.imgEvent)
            binding.titleEvent.text = event.name
            binding.root.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, DetailEventActivity::class.java).apply {
                    putExtra("EVENT_ID", event.id.toString())
                }
                context.startActivity(intent)
            }
        }
    }

    inner class FinishedViewHolder(private val binding: ItemEventFinishedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            Glide.with(itemView.context)
                .load(event.imageLogo)
                .into(binding.imgEvent)
            binding.titleEvent.text = event.name
            binding.root.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, DetailEventActivity::class.java).apply {
                    putExtra("EVENT_ID", event.id.toString())
                }
                context.startActivity(intent)
            }
        }
    }

    inner class EventViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            Glide.with(itemView.context)
                .load(event.imageLogo)
                .into(binding.imgEvent)
            binding.titleEvent.text = event.name
            binding.root.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, DetailEventActivity::class.java).apply {
                    putExtra("EVENT_ID", event.id.toString())
                }
                context.startActivity(intent)
            }
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}