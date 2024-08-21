package com.example.wiseforecast.Modules

import com.example.wiseforecast.Repositories.ForecastRepository
import com.example.wiseforecast.Repositories.ForecastRepositoryImplementation
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideRepository(repositoryImplementation: ForecastRepositoryImplementation): ForecastRepository
}