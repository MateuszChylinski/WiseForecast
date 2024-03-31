package com.example.wiseforecast.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wiseforecast.LocationServices.LocationRequest
import com.example.wiseforecast.Repositories.ForecastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrentForecastViewModel @Inject constructor(
    private val forecastRepository: ForecastRepository,
    private val locationRequest: LocationRequest
) : ViewModel() {
    private val _cityName = MutableLiveData("")
    val cityName get() = _cityName

    fun onCityNameChange(newCityName: String){
        _cityName.value = newCityName
    }
}