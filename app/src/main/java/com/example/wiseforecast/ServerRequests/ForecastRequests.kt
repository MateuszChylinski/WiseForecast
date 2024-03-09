package com.example.wiseforecast.ServerRequests

import com.example.wiseforecast.Models.CurrentForecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastRequests {

    @GET("weather")
    suspend fun currentForecastByLocation(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String,
        @Query("appid") apiKey: String
    ): Response<CurrentForecast>

    @GET("weather")
    suspend fun currentForecastByCity(
        @Query("q") cityName: String,
        @Query("units") units: String,
        @Query("appid") apiKey: String
    ): Response<CurrentForecast>

}