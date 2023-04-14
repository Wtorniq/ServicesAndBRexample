package com.example.servicesandbrexample.model.retrofit.repo

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object ApiUtils {
    private val baseUrlMainPart = "https://dictionary.yandex.net/api/"
    private val baseUrlVersion = "v1/"
    private val baseUrlJson = "dicservice.json/"
    val baseUrl = "$baseUrlMainPart$baseUrlVersion$baseUrlJson"

    fun getOkHttpBuilder(): OkHttpClient{
        val httpClient = OkHttpClient.Builder()
        httpClient.apply {
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
/*            addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .method(original.method(), original.body())
                    .build()
                chain.proceed(request)
            }*/
        }
        return httpClient.build()
    }
}