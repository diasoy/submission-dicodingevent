package com.example.mydicodingevent.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteEventDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteEvent: FavoriteEvent)
    @Delete
    fun delete(favoriteEvent: FavoriteEvent)
    @Query("SELECT * from favoriteEvent ORDER BY id ASC")
    fun getAllFavoriteEvent(): LiveData<List<FavoriteEvent>>
    @Query("SELECT * FROM favoriteEvent WHERE id = :id")
    fun getFavoriteEventById(id: String): LiveData<FavoriteEvent>
}