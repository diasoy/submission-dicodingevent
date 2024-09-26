package com.example.mydicodingevent.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.mydicodingevent.database.FavoriteEvent

class FavoriteEventDiffCallback(private val oldFavoriteEvent: List<FavoriteEvent>, private val newFavoriteEvent: List<FavoriteEvent>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavoriteEvent.size
    override fun getNewListSize(): Int = newFavoriteEvent.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavoriteEvent[oldItemPosition].id == newFavoriteEvent[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavoriteEvent = oldFavoriteEvent[oldItemPosition]
        val newFavoriteEvent = newFavoriteEvent[newItemPosition]
        return oldFavoriteEvent.name == newFavoriteEvent.name && oldFavoriteEvent.mediaCover == newFavoriteEvent.mediaCover
    }
}