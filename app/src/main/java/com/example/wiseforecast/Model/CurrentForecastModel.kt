package com.example.wiseforecast.Model

import com.google.gson.annotations.SerializedName

data class CurrentForecastModel(
    @SerializedName("base")
    private val base: String,
    @SerializedName("visibility")
    private val visibility: Int,
    @SerializedName("dt")
    private val datetime: Long,
    @SerializedName("timezone")
    private val timezone: Int,
    @SerializedName("id")
    private val id: Int,
    @SerializedName("name")
    private val name: String,
    @SerializedName("cod")
    private val code: Int,

    @SerializedName("coord")
    private val coord: Coord,
    @SerializedName("weather")
    private val weather: List<Weather>,
    @SerializedName("main")
    private val main: Main,
    @SerializedName("wind")
    private val wind: Wind,
    @SerializedName("clouds")
    private val clouds: Clouds,
    @SerializedName("sys")
    private val sys: Sys
)

data class Coord(
    @SerializedName("lon")
    private val longitude: Double,
    @SerializedName("lat")
    private val latitude: Double
)

data class Weather(
    @SerializedName("")
    private val id: Int,
    @SerializedName("main")
    private val mainDescription: String,
    @SerializedName("description")
    private val fullDescription: String,
    @SerializedName("icon")
    private val icon: String,
)

data class Main(
    @SerializedName("temp")
    private val temperature: Double,
    @SerializedName("feels_like")
    private val feelsLike: Double,
    @SerializedName("temp_min")
    private val minimalTemperature: Double,
    @SerializedName("temp_max")
    private val maximalTemperature: Double,
    @SerializedName("pressure")
    private val pressure: Int,
    @SerializedName("humidity")
    private val humidity: Int,
    @SerializedName("sea_level")
    private val seaLevel: Int,
    @SerializedName("grnd_level")
    private val groundLevel: Int
)

data class Wind(
    @SerializedName("speed")
    private val windSpeed: Double,
    @SerializedName("deg")
    private val windDegrees: Int,
)

data class Clouds(
    @SerializedName("all")
    private val cloudiness: Int
)

data class Sys(
    @SerializedName("type")
    private val type: Int,  //Internal parameter
    @SerializedName("id")
    private val id: Int, //Internal parameter
    @SerializedName("country")
    private val country: String, //Country code (GB, JP etc.)
    @SerializedName("sunrise")
    private val sunriseDT: Long, //Sunrise time, unix, UTC
    @SerializedName("sunset")
    private val sunsetDT: Long //Sunset time, unix, UTC
)
