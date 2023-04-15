package com.example.servicesandbrexample.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.servicesandbrexample.utils.AppState
import com.example.servicesandbrexample.model.repository.Repository

class MainViewModel(private val repository: Repository) : ViewModel() {
    private val liveData = MutableLiveData<AppState>()

    fun getLiveData(): LiveData<AppState> = liveData
    fun getTranslationData() = getDataFromServer()

    private fun getDataFromServer(){
        liveData.value = AppState.Loading
        Thread{
            val response = repository.getTranslationResponse()
            liveData.postValue(AppState.Success(response))
        }.start()
    }
}