package com.example.mydicodingevent.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteEvent(
    @PrimaryKey(autoGenerate = false)
    var id: String = "",
    var name: String = "",
    var mediaCover: String? = null,
)