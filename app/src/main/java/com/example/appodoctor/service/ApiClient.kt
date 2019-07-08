package com.example.appodoctor.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object{
        fun getClient() : Retrofit{
            val baseURL = "http://appodoc.akbarraihanm.com/api/"
            return Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        fun pushNotif() : Retrofit{
            val baseURL = "https://appodoctor.firebaseapp.com/api/"
            return  Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}