package com.example.appodoctor.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object{
        fun getClient() : Retrofit{
            val baseURL = "http://appodoc.akbarraihanm.com/api/72318591234/"
            return Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}