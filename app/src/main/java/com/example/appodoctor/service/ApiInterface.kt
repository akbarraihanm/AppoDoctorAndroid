package com.example.appodoctor.service

import com.example.appodoctor.model.DokterResponse
import com.example.appodoctor.model.JadwalResponse
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query

interface ApiInterface {

    @GET("dokterpoli?id=1")
    fun getDokterByPoli() : Call<DokterResponse>
    @GET("dokterpoli")
    fun getDokterByPoliId(@Query("id") poliId : String?) : Call<DokterResponse>
    @GET("jadwaldokter")
    fun getJadwalByDokterId(@Query("id") dokId : String?) : Call<JadwalResponse>
}