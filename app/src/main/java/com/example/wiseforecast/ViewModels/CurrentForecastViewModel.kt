package com.example.wiseforecast.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wiseforecast.LocationServices.LocationRequest
import com.example.wiseforecast.Models.CurrentForecast
import com.example.wiseforecast.Repositories.ForecastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CurrentForecastViewModel @Inject constructor(
    private val forecastRepository: ForecastRepository,
    private val locationRequest: LocationRequest
) : ViewModel() {
    var cityName by mutableStateOf("")
        private set

    private val _location = MutableStateFlow<Map<String, Double>>(mutableMapOf())
    private val location: StateFlow<Map<String, Double>> = _location

    private val _currentForecastByLocation = MutableLiveData<Response<CurrentForecast>>()
    val currentForecast get() = _currentForecastByLocation

    private val _currentForecastByCity = MutableLiveData<Response<CurrentForecast>>()
    val currentForecastByCity get() = _currentForecastByCity

    fun onCityNameChange(newCityName: String) {
        cityName = newCityName
    }

    fun fetchUserLocation() {
        viewModelScope.launch {
            locationRequest.getCurrentLocation().let {
                _location.emit(
                    mutableMapOf(
                        "latitude" to (it?.latitude ?: 00.00),
                        "longitude" to (it?.longitude ?: 00.00)
                    )
                )
            }
        }
    }

    fun fetchCurrentForecastByLocation() {
        viewModelScope.launch {
            location.collect { state ->
                if (state["latitude"] != null &&
                    state["longitude"] != null
                ) {
                    try {
                        _currentForecastByLocation.value =
                            forecastRepository.requestCurrentForecastByLocation(
                                latitude = state["latitude"],
                                longitude = state["longitude"],
                                //TODO
                                units = "metric"
                            )
                    } catch (exception: Exception) {
                        println("Exception in api call by location ${exception.message}")
                    }
                }
            }
        }
    }

    fun funFetchForecastByCity() {
        viewModelScope.launch {
            try {
                if (cityName.isNotEmpty()) {
                    _currentForecastByCity.value =
                        forecastRepository.requestCurrentForecastByCityName(
                            cityName = cityName.trim(),
                            //TODO
                            units = "metric"
                        )
                }
            } catch (exception: Exception) {
                println("Exception in api call by city name ${exception.message}")
            }
        }
    }
}