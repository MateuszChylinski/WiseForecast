package com.example.wiseforecast.Repositories

import com.example.wiseforecast.BuildConfig
import com.example.wiseforecast.ServerRequests.ForecastRequests

class ForecastRepository (private val forecastRequests: ForecastRequests)
{
    suspend fun requestCurrentForecastByLocation(
        longitude: Double?,
        latitude: Double?,
        units: String?
    ) =
        forecastRequests.currentForecastByLocation(
            longitude = longitude,
            latitude = latitude,
            units = units,
            apiKey = BuildConfig.API_KEY
    )
    suspend fun requestCurrentForecastByCityName(
        cityName: String,
        units: String
    ) =
        forecastRequests.currentForecastByCity(
            cityName = cityName,
            units = units,
            apiKey = BuildConfig.API_KEY
        )
}