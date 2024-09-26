package com.example.mydicodingevent.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.mydicodingevent.database.FavoriteEvent
import com.example.mydicodingevent.database.FavoriteEventDao
import com.example.mydicodingevent.database.FavoriteEventRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteEventRepository(application: Application) {
    private val mFavoriteEventDao: FavoriteEventDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = FavoriteEventRoomDatabase.getDatabase(application)
        mFavoriteEventDao = db.favoriteEventDao()
    }
    fun getAllFavoriteEvent(): LiveData<List<FavoriteEvent>> = mFavoriteEventDao.getAllFavoriteEvent()
    fun getFavoriteEventById(id: String): LiveData<FavoriteEvent> = mFavoriteEventDao.getFavoriteEventById(id)
    fun insert(favoriteEvent: FavoriteEvent) {
        executorService.execute { mFavoriteEventDao.insert(favoriteEvent) }
    }
    fun delete(favoriteEvent: FavoriteEvent) {
        executorService.execute { mFavoriteEventDao.delete(favoriteEvent) }
    }
}