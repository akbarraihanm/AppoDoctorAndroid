package com.example.appodoctor.listjanji

import android.content.Context
import android.widget.Toast
import com.example.appodoctor.model.JadwalModel
import com.example.appodoctor.model.JadwalResponse
import com.example.appodoctor.service.ApiClient
import com.example.appodoctor.service.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppointmentPresenter (private val context: Context,
                            private val listAppoView: ListAppoView
){

    fun getItemListAppo(apiKey : String, idDokter : String){
        var listJadwal : ArrayList<JadwalModel>
        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val callItem = apiInterface.getJadwalByDokterId(apiKey,idDokter)

        callItem.enqueue(object : Callback<JadwalResponse>{
            override fun onFailure(call: Call<JadwalResponse>, t: Throwable) {
                Toast.makeText(context, "Gagal ambil item", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<JadwalResponse>, response: Response<JadwalResponse>) {
                listJadwal = response.body()!!.data
                try {
                    listAppoView.showListAppo(listJadwal)
                }catch (e:Exception){}
            }

        })
    }
}