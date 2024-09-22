package com.example.mydicodingevent.data.retrofit

import com.example.mydicodingevent.data.response.DetailEventResponse
import com.example.mydicodingevent.data.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("events?active=1")
    fun getUpcomingEvents(): Call<EventResponse>

    @GET("events?active=0")
    fun getFinishedEvents(): Call<EventResponse>

    @GET("events/{id}")
    fun getDetailEvent(@Path("id") id: String): Call<DetailEventResponse>
}
