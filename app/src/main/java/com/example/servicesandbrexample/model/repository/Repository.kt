package com.example.servicesandbrexample.model.repository

import com.example.servicesandbrexample.model.entities.Weather

interface Repository {
    fun getWeatherFromServer(): Weather
    fun getWeatherFromLocalStorage(): Weather
}