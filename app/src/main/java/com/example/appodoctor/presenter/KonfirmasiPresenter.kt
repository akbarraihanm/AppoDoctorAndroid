package com.example.appodoctor.presenter

import android.content.Context
import android.widget.Toast
import com.example.appodoctor.model.PostAppo
import com.example.appodoctor.model.Pasien
import com.example.appodoctor.model.PasienResponse
import com.example.appodoctor.service.ApiClient
import com.example.appodoctor.service.ApiInterface
import com.example.appodoctor.view.KonfirmasiView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KonfirmasiPresenter (private val konfirmasiView: KonfirmasiView,
                           private val context: Context){

    fun getPasienItem(idPas : String){
        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val callPasienItem = apiInterface.getPasienData(idPas)
        var itemPasien : ArrayList<Pasien>

        callPasienItem.enqueue(object : Callback<PasienResponse>{
            override fun onFailure(call: Call<PasienResponse>, t: Throwable) {
                Toast.makeText(context, "Gagal ambil item",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PasienResponse>, response: Response<PasienResponse>) {
                try {
                    konfirmasiView.showLoading()
                    itemPasien = response.body()!!.data
                    konfirmasiView.showItemPasien(itemPasien)
                }catch (e:Exception){}
            }

        })
    }

    fun setKonfirmasi(tgl : String, pasId : String, dokId : String, polId : String){
        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val callPostConfirm = apiInterface.postAppoData(tgl,pasId,dokId,polId)

        callPostConfirm.enqueue(object : Callback<PostAppo>{
            override fun onFailure(call: Call<PostAppo>, t: Throwable) {
                Toast.makeText(context, "Koneksi gagal",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PostAppo>, response: Response<PostAppo>) {
                try {
                    konfirmasiView.whenPostAppo()
                }catch (e:Exception){
                    Toast.makeText(context,""+e,Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
}