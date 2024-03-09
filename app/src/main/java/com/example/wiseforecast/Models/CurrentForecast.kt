package com.example.wiseforecast.Models

import com.google.gson.annotations.SerializedName

data class CurrentForecast(
    @SerializedName("lon")
    val longitude: Double,
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("current")
    val current: Current
)

data class Current(
    @SerializedName("dt")
    val datetime: Long,
    @SerializedName("sunrise")
    val sunrise: Long,
    @SerializedName("sunset")
    val sunset: Long,
    @SerializedName("temp")
    val mainTemperature: Double,
    @SerializedName("feels_like")
    val mainFeelsLike: Double,
    @SerializedName("pressure")
    val mainPressure: Int,
    @SerializedName("humidity")
    val mainHumidity: Int,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("speed")
    val windSpeed: Double,
    @SerializedName("deg")
    val windDegrees: Double,
    @SerializedName("weather")
    val weather: List<Weather>
)

data class Weather(
    @SerializedName("id")
    val weatherID: Int,
    @SerializedName("main")
    val weatherMain: String,
    @SerializedName("description")
    val weatherDescription: String,
    @SerializedName("icon")
    val weatherIconID: Int
)
