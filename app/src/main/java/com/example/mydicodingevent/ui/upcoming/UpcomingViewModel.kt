package com.example.mydicodingevent.ui.upcoming

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mydicodingevent.data.response.EventResponse
import com.example.mydicodingevent.data.response.ListEventsItem
import com.example.mydicodingevent.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingViewModel : ViewModel() {

    private val _listEvent = MutableLiveData<List<ListEventsItem>>()
    val listEvent: LiveData<List<ListEventsItem>> = _listEvent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        fetchEventsUpcoming()
    }

    private fun fetchEventsUpcoming() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUpcomingEvents()
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listEvent.value = response.body()?.listEvents
                    _error.value = null
                    Log.d("UpcomingViewModel", "Data received: ${response.body()?.listEvents}")
                } else {
                    _error.value = "Failed to load upcoming events"
                    Log.e("UpcomingViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                _error.value = "Network error"
                Log.e("UpcomingViewModel", "onFailure: ${t.message.toString()}")
            }
        })
    }
}