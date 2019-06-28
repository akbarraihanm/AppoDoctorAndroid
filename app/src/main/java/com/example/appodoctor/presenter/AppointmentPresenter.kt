package com.example.appodoctor.presenter

import android.content.Context
import android.widget.Toast
import com.example.appodoctor.model.AppoResponse
import com.example.appodoctor.model.Appointment
import com.example.appodoctor.service.ApiClient
import com.example.appodoctor.service.ApiInterface
import com.example.appodoctor.view.ListAppoView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppointmentPresenter (private val context: Context,
                            private val listAppoView: ListAppoView){
    fun getItemListAppo(idDokter : String){
        var listAppo : ArrayList<Appointment>
        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val callItem = apiInterface.getAppoByDokterId(idDokter)

        callItem.enqueue(object : Callback<AppoResponse>{
            override fun onFailure(call: Call<AppoResponse>, t: Throwable) {
                Toast.makeText(context, "Gagal ambil item", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<AppoResponse>, response: Response<AppoResponse>) {
                listAppo = response.body()!!.data
                try {
                    listAppoView.showListAppo(listAppo)
                }catch (e:Exception){}
            }

        })
    }
}