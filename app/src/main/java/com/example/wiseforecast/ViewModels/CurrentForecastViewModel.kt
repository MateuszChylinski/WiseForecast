package com.example.wiseforecast.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wiseforecast.Repositories.ForecastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentForecastViewModel @Inject constructor(
    private val forecastRepository: ForecastRepository
): ViewModel() {
}