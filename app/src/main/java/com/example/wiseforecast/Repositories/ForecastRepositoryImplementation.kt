package com.example.wiseforecast.Repositories

import com.example.wiseforecast.BuildConfig
import com.example.wiseforecast.HttpRequests.ForecastHttpRequests
import com.example.wiseforecast.Model.CurrentForecastModel
import retrofit2.Response
import javax.inject.Inject

class ForecastRepositoryImplementation @Inject constructor(
    private val forecastHttpRequests: ForecastHttpRequests
) : ForecastRepository {

    override suspend fun getCurrentForecast(
        latitude: Double,
        longitude: Double,
        units: String,
    ): Response<CurrentForecastModel> {
        val response = forecastHttpRequests.getCurrentForecast(
            latitude = latitude,
            longitude = longitude,
            units = units,
            apiKey = BuildConfig.API_KEY
        )
        println("ASDASDD ${response.body()}")
        return response
    }
}