package com.example.mydicodingevent.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydicodingevent.data.response.Event
import com.example.mydicodingevent.data.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailEventViewModel : ViewModel() {
    private val _eventDetail = MutableLiveData<Event>()
    val eventDetail: LiveData<Event> get() = _eventDetail

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://event-api.dicoding.dev/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    fun fetchEventDetail(id: String) {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                apiService.getDetailEvent(id).execute()
            }
            if (response.isSuccessful) {
                _eventDetail.postValue(response.body()?.event)
            }
        }
    }
}