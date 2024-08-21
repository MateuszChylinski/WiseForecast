package com.example.wiseforecast.HttpRequests

import com.example.wiseforecast.Model.CurrentForecastModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastHttpRequests {

    @GET("weather")
    suspend fun getCurrentForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String,
        @Query("appid") apiKey: String
    ): Response<CurrentForecastModel>
}