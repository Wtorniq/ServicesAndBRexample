package com.example.servicesandbrexample.utils

import com.example.servicesandbrexample.model.entities.Description


sealed class AppState{
    data class Success(val response: ArrayList<Description>): AppState()
    data class Error(val error: Throwable): AppState()
    object Loading: AppState()
}
