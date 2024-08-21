package com.example.wiseforecast.Repositories

import com.example.wiseforecast.Model.CurrentForecastModel
import retrofit2.Response

interface ForecastRepository {
    suspend fun getCurrentForecast(latitude: Double, longitude: Double, units: String): Response<CurrentForecastModel>
}