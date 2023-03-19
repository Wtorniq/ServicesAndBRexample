package com.example.servicesandbrexample.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.servicesandbrexample.AppState
import com.example.servicesandbrexample.model.repository.Repository

class MainViewModel(private val repository: Repository) : ViewModel() {
    private val liveData = MutableLiveData<AppState>()

    fun getLiveData(): LiveData<AppState> = liveData
    fun getWeather() = getDataFromLocalSource()

    private fun getDataFromLocalSource(){
        liveData.value = AppState.Loading
        Thread{
            liveData.postValue(AppState.Success(repository.getWeatherFromLocalStorage()))
        }.start()
    }
}