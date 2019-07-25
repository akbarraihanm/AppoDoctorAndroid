package com.example.appodoctor.tambahjadwal

import android.content.Context
import android.widget.Toast
import com.example.appodoctor.model.JadwalModel
import com.example.appodoctor.service.ApiClient
import com.example.appodoctor.service.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddJadwalPresenter (private val context: Context,
                          private val addJadwalView: AddJadwalView
){
    fun postAddJadwal(apiKey : String, dokId : String, tglAdd : String, jamMulai : String, jamSelesai : String){
        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiInterface.postAddJadwal(apiKey,dokId, tglAdd, jamMulai, jamSelesai)

        call.enqueue(object : Callback<JadwalModel>{
            override fun onFailure(call: Call<JadwalModel>, t: Throwable) {
                Toast.makeText(context, "Data wajib diisi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<JadwalModel>, response: Response<JadwalModel>) {
                try {
//                    if(dokId != "" || tglAdd == "" || jamMulai == "" || jamSelesai == ""){
//                        Toast.makeText(context, "Data belum diisi", Toast.LENGTH_SHORT).show()
//                    }
//                    else
                    addJadwalView.whenPostJadwal()
                }catch (e:Exception){}
            }

        })
    }
}