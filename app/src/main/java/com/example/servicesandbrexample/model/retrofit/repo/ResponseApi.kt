package com.example.servicesandbrexample.model.retrofit.repo

import com.example.servicesandbrexample.model.retrofit.rest_entities.ResponseDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ResponseApi {
    @GET("lookup")
    fun getTranslation(
        @Query("key") userId: String,
        @Query("lang") lang: String,
        @Query("text") text: String
    ): Call<ResponseDTO>
}