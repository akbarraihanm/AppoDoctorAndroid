package com.example.appodoctor.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object{
        private var numVar = "72318591234"
        fun getClient() : Retrofit{
            val baseURL = "https://testingapiappo.000webhostapp.com/api/$numVar/"
            return Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}