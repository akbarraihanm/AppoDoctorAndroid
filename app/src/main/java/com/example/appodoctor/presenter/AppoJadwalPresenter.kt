package com.example.appodoctor.presenter

import android.content.Context
import android.widget.Toast
import com.example.appodoctor.model.AppoResponse
import com.example.appodoctor.service.ApiClient
import com.example.appodoctor.service.ApiInterface
import com.example.appodoctor.view.AppoJadwalView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppoJadwalPresenter (private val context: Context, private val appoJadwalView : AppoJadwalView){
    fun getAppoByJadwalItem(idJadwal : String){
        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiInterface.getAppoByJadwalId(idJadwal)
        call.enqueue(object : Callback<AppoResponse>{
            override fun onFailure(call: Call<AppoResponse>, t: Throwable) {
                Toast.makeText(context, "Koneksi gagal", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<AppoResponse>, response: Response<AppoResponse>) {
                var listAppo = response.body()!!.data
                try {
                    appoJadwalView.showItem(listAppo)
                }catch (e:Exception){}
            }
        })
    }
}