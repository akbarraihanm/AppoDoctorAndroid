package com.example.appodoctor.presenter

import android.content.Context
import android.widget.Toast
import com.example.appodoctor.model.JadwalModel
import com.example.appodoctor.model.JadwalResponse
import com.example.appodoctor.view.JadwalView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JadwalPresenter(private val call : Call<JadwalResponse>,
                      private val context: Context,
                      private val jadView : JadwalView){
    fun getJadwalItem(){
        var listJadwal : ArrayList<JadwalModel>
        call.enqueue(object : Callback<JadwalResponse>{
            override fun onFailure(call: Call<JadwalResponse>, t: Throwable) {
                Toast.makeText(context, "Gagal ambil item", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<JadwalResponse>, response: Response<JadwalResponse>) {
                listJadwal = response.body()!!.data
                jadView.showLoading()
                try {
                    jadView.showJadwalItem(listJadwal)
                }catch (e : Exception){}
            }

        })
    }
}