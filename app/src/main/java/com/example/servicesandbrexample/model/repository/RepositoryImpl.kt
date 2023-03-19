package com.example.servicesandbrexample.model.repository

import com.example.servicesandbrexample.model.entities.Weather

class RepositoryImpl: Repository {
    override fun getWeatherFromServer(): Weather = Weather()

    override fun getWeatherFromLocalStorage(): Weather = Weather()
}