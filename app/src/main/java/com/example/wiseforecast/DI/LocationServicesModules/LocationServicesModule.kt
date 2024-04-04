package com.example.wiseforecast.DI.LocationServicesModules

import android.app.Application
import com.example.wiseforecast.LocationServices.LocationRequest
import com.example.wiseforecast.LocationServices.LocationRequestImplementation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationServicesModule {

    @Singleton
    @Provides
    fun provideFusedLocationProviderClient(application: Application):
            FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    @Singleton
    @Provides
    fun provideLocationRequests(
        fusedLocationProviderClient: FusedLocationProviderClient,
        application: Application
    ): LocationRequest = LocationRequestImplementation(
        fusedLocationProviderClient = fusedLocationProviderClient,
        application = application
    )
}