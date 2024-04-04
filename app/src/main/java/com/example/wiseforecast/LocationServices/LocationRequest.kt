package com.example.wiseforecast.LocationServices

import android.location.Location

interface LocationRequest {
    suspend fun getCurrentLocation(): Location?
}