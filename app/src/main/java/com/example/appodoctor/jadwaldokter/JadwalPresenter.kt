package com.example.appodoctor.jadwaldokter

import android.content.Context
import android.widget.Toast
import com.example.appodoctor.model.JadwalModel
import com.example.appodoctor.model.JadwalResponse
import com.example.appodoctor.model.PutPwResponse
import com.example.appodoctor.service.ApiClient
import com.example.appodoctor.service.ApiInterface
//import com.example.appodoctor.tambahjadwal.AddJadwalView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JadwalPresenter(private val context: Context,
                      private val jadView : JadwalView
){
    fun getJadwalItem(id : String){
        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiInterface.getJadwalByDokterId(id)

        var listJadwal : ArrayList<JadwalModel>
        call.enqueue(object : Callback<JadwalResponse>{
            override fun onFailure(call: Call<JadwalResponse>, t: Throwable) {
                Toast.makeText(context, "Gagal ambil item", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<JadwalResponse>, response: Response<JadwalResponse>) {
                listJadwal = response.body()!!.data
                try {
                    jadView.showJadwalItem(listJadwal)
                }catch (e : Exception){}
            }

        })
    }
    fun deleteJadwalItem(idJadwal : String){
        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val callDelete = apiInterface.deleteJadwal(idJadwal)

        callDelete.enqueue(object : Callback<PutPwResponse>{
            override fun onFailure(call: Call<PutPwResponse>, t: Throwable) {
                Toast.makeText(context, "Gagal hapus item", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PutPwResponse>, response: Response<PutPwResponse>) {
                try {
                    jadView.whenDelete()
                }catch (e:Exception){}
            }

        })
    }

}

