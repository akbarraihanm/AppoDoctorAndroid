package com.example.appodoctor.cekstatus

import android.content.Context
import android.widget.Toast
import com.example.appodoctor.model.AppoResponse
import com.example.appodoctor.model.Appointment
import com.example.appodoctor.service.ApiClient
import com.example.appodoctor.service.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StatusPresenter (private val statusView: StatusView,
                       private val context: Context){
    fun getStatusItem(apiKey : String, id : String){
        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val callStatusItem = apiInterface.getAppoByPasienId(apiKey,id)
        var listStatus : ArrayList<Appointment>

        callStatusItem.enqueue(object : Callback<AppoResponse>{
            override fun onFailure(call: Call<AppoResponse>, t: Throwable) {
                Toast.makeText(context, "Koneksi gagal", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<AppoResponse>, response: Response<AppoResponse>) {
                listStatus = response.body()!!.data
                statusView.showLoading()
                try {
                    statusView.showStatusItem(listStatus)
                }catch (e:Exception){}
            }

        })
    }
    fun getStatusByIdAppo(apiKey: String, id : String){
        var objItem : ArrayList<Appointment>
        val apiInterface  = ApiClient.getClient().create(ApiInterface::class.java)
        val callStatus = apiInterface.getAppoById(apiKey,id)

        callStatus.enqueue(object : Callback<AppoResponse>{
            override fun onFailure(call: Call<AppoResponse>, t: Throwable) {
                Toast.makeText(context, "Gagal ambil item", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<AppoResponse>, response: Response<AppoResponse>) {
                objItem = response.body()!!.data
                try {
                    statusView.showLoading()
                    statusView.showStatusId(objItem)
                }catch (e:Exception){}
            }

        })
    }
}