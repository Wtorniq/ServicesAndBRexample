package com.example.servicesandbrexample.model.entities

import com.example.servicesandbrexample.model.entities.City

data class Weather(
    val city : City = getDefaultCity(),
    val temp : Int = 0,
    val feelLike : Int = 0
)

fun getDefaultCity(): City = City("Moscow", 55.751244, 37.618423)
