package com.example.appodoctor.presenter

import android.content.Context
import android.widget.Toast
import com.example.appodoctor.model.DokterModel
import com.example.appodoctor.model.DokterResponse
import com.example.appodoctor.view.DokterView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class DokterPresenter (private val call : Call<DokterResponse>,
                       private val context: Context,
                       private val dokView : DokterView){
    fun getDokterItem(){
        var listDokter : ArrayList<DokterModel>
        call.enqueue(object : Callback<DokterResponse>{
            override fun onFailure(call: Call<DokterResponse>, t: Throwable) {
                Toast.makeText(context, "Gagal ambil item", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<DokterResponse>?, response: Response<DokterResponse>?) {
                listDokter = response!!.body()!!.data
                dokView.showLoading()
                try {
                    dokView.showDokterItem(listDokter)
                }catch (e: Exception){

                }
                dokView.hideLoading()
            }

        })
    }
}