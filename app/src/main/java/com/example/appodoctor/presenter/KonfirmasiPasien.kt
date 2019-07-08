package com.example.appodoctor.presenter

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.appodoctor.activity.AppoPasienActivity
import com.example.appodoctor.activity.ListAppoActivity
import com.example.appodoctor.model.PutPwResponse
import com.example.appodoctor.service.ApiClient
import com.example.appodoctor.service.ApiInterface
import com.example.appodoctor.view.KonfirPasienView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KonfirmasiPasien (private val context: Context,
                        private val konfirPasienView : KonfirPasienView){
    fun putAppoPasien(idAppo : String, statusAppo : String, ketAppo : String){
        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val callPut = apiInterface.putStatusAppo(idAppo, statusAppo, ketAppo)
        callPut.enqueue(object : Callback<PutPwResponse>{
            override fun onFailure(call: Call<PutPwResponse>, t: Throwable) {
                Toast.makeText(context, "Koneksi gagal", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PutPwResponse>, response: Response<PutPwResponse>) {
                var statusPut = response.body()!!.statusPut
                try {
                    if(statusPut == "success"){
                        Toast.makeText(context, "Berhasil update janji", Toast.LENGTH_SHORT).show()
                        konfirPasienView.whenPutData()
                    }
                    else{
                        Toast.makeText(context, "Tidak dapat update janji", Toast.LENGTH_SHORT).show()
                    }
                }catch (e:Exception){}
            }

        })
    }
}