package com.example.mydicodingevent.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mydicodingevent.database.FavoriteEvent
import com.example.mydicodingevent.repository.FavoriteEventRepository

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val mFavoriteEventRepository: FavoriteEventRepository = FavoriteEventRepository(application)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllFavoriteEvent(): LiveData<List<FavoriteEvent>> {
        _isLoading.value = true
        val favoriteEvents = mFavoriteEventRepository.getAllFavoriteEvent()
        _isLoading.value = false
        return favoriteEvents
    }
}