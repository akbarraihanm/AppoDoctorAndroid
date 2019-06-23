package com.example.appodoctor.presenter

import android.content.Context
import android.widget.Toast
import com.example.appodoctor.model.*
import com.example.appodoctor.view.BuatJanjiView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class BuatJanjiPresenter(private val callPoli : Call<PoliResponse>,
                         private val callDokter : Call<DokterResponse>,
                         private val callJadwal : Call<JadwalResponse>,
                         private val context: Context,
                         private val buatJanjiView: BuatJanjiView){
    fun getPoliItem(){
        var listPoli : ArrayList<Poli>
        callPoli.enqueue(object : Callback<PoliResponse>{
            override fun onFailure(call: Call<PoliResponse>, t: Throwable) {
                Toast.makeText(context, "Gagal ambil item", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PoliResponse>, response: Response<PoliResponse>) {
                listPoli = response.body()!!.data
                try {
                    buatJanjiView.showLoading()
                    var poliName : ArrayList<String> = arrayListOf()
                    var poliId : ArrayList<String> = arrayListOf()
                    for (i in listPoli.indices){
                        poliName.add(listPoli[i].namaPoli!!)
                        poliId.add(listPoli[i].idPoli!!)
                    }
                    buatJanjiView.showPoliItem(poliName,poliId)
                }catch (e:Exception){}
            }

        })
    }

    fun getDokterItem(){
        var listDokter : ArrayList<DokterModel>
        callDokter.enqueue(object : Callback<DokterResponse>{
            override fun onFailure(call: Call<DokterResponse>, t: Throwable) {
                Toast.makeText(context, "Gagal ambil item", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<DokterResponse>, response: Response<DokterResponse>) {
                listDokter = response.body()!!.data
                try {
                    var dokterName : ArrayList<String> = arrayListOf()
                    var dokterId : ArrayList<String> = arrayListOf()
                    for(i in listDokter.indices){
                        dokterName.add(listDokter[i].namaDokter!!)
                        dokterId.add(listDokter[i].idDokter!!)
                    }
                    buatJanjiView.showDokterItem(dokterName, dokterId)
                }catch (e:Exception){}
            }

        })
    }

    fun getJadwalItem(){
        var listJadwal : ArrayList<JadwalModel>
        callJadwal.clone().enqueue(object : Callback<JadwalResponse>{
            override fun onFailure(call: Call<JadwalResponse>, t: Throwable) {
                Toast.makeText(context, "Gagal ambil item", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<JadwalResponse>, response: Response<JadwalResponse>) {
                listJadwal = response.body()!!.data
                try {
                    buatJanjiView.showJadwalItem(listJadwal)
                }catch (e:Exception){}
            }

        })
    }
}