package com.example.servicesandbrexample.model.repository

import android.util.Log
import com.example.servicesandbrexample.model.entities.Description
import com.example.servicesandbrexample.model.entities.Translation
import com.example.servicesandbrexample.model.retrofit.repo.RetrofitRepo
import com.example.servicesandbrexample.model.retrofit.rest_entities.ResponseDTO

class RepositoryImpl: Repository {
    override fun getTranslationResponse(): ArrayList<Description> {
        val responseDTO: ResponseDTO?
        try {
            responseDTO = RetrofitRepo.api.getTranslation(RetrofitRepo.userKey, "en-ru", "time").execute().body()
        } catch (error: Throwable){
            Log.e("", error.toString())
            return arrayListOf()
        }
        return getTranslation(responseDTO)
    }

    private fun getTranslation(responseDTO: ResponseDTO?): ArrayList<Description> {
        val response = arrayListOf<Description>()
        responseDTO?.def?.forEach { desc ->
            val translation = arrayListOf<Translation>()
            desc.tr?.forEach { trans ->
                translation.add(Translation(trans.text.toString(), trans.pos.toString()))
            }
            response.add(Description(desc.text.toString(), desc.pos.toString(), translation))
        }
        return response
    }
}