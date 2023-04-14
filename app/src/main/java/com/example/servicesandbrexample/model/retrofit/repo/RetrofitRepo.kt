package com.example.servicesandbrexample.model.retrofit.repo

import com.example.servicesandbrexample.model.retrofit.rest_entities.ResponseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitRepo: Callback<ResponseDTO> {
    const val userKey = "dict.1.1.20230330T133806Z.b865f7bbc374cb92.9662f7eb389978730745a0b34117a1f6ef2078cf"
    val api: ResponseApi by lazy {
        val adapter = Retrofit.Builder()
            .baseUrl(ApiUtils.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(ApiUtils.getOkHttpBuilder())
            .build()

        adapter.create(ResponseApi::class.java)
    }
    override fun onResponse(call: Call<ResponseDTO>, response: Response<ResponseDTO>) {}

    override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {}
}