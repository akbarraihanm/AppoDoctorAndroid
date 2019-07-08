package com.example.appodoctor.presenter

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.appodoctor.MainActivity
import com.example.appodoctor.fragment.ProfileFragment
import com.example.appodoctor.model.*
import com.example.appodoctor.service.ApiClient
import com.example.appodoctor.service.ApiInterface
import com.example.appodoctor.view.ProfilView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.Exception

class ProfilPresenter (private val profilView: ProfilView,
                       private val context: Context){
    val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
    lateinit var profilData : ArrayList<Pasien>

    fun getProfilData(idPasien : String){

        val callProfilData = apiInterface.getPasienData(idPasien)

        callProfilData.enqueue(object : Callback<PasienResponse>{
            override fun onFailure(call: Call<PasienResponse>, t: Throwable) {
                Toast.makeText(context, "Koneksi gagal", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PasienResponse>, response: Response<PasienResponse>) {
                profilData = response.body()!!.data
                profilView.showLoading()
                try {
                    profilView.showProfilData(profilData)
                }catch (e:Exception){}
            }

        })
    }

    fun getProfilDokter(idDokter : String){
        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val callProfil = apiInterface.getDokterById(idDokter)
        var itemDokter : ArrayList<DokterModel>
        callProfil.enqueue(object : Callback<DokterResponse>{
            override fun onFailure(call: Call<DokterResponse>, t: Throwable) {
                Toast.makeText(context, "Gagal ambil item", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<DokterResponse>, response: Response<DokterResponse>) {
                itemDokter = response.body()!!.data
                try {
                    profilView.showLoading()
                    profilView.showProfilDokter(itemDokter)
                }catch (e:Exception){}
            }

        })
    }

    fun updatePassword(idPasien: String,pwPasien : String){
        val putPassword = apiInterface.putPasienPassword(idPasien,pwPasien)

        putPassword.enqueue(object : Callback<PutPwResponse>{
            override fun onFailure(call: Call<PutPwResponse>, t: Throwable) {
                Toast.makeText(context, ""+t, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PutPwResponse>, response: Response<PutPwResponse>) {
                var statPut = response.body()
                try {
                    if(statPut!!.statusPut == "success"){
//                        getProfilData(idPasien)
                        Toast.makeText(context, "Password berhasil diubah", Toast.LENGTH_SHORT).show()
                        val i = Intent(context, ProfileFragment::class.java)
                        context.startActivity(i)
                    }
                    else
                        Toast.makeText(context, "Tidak dapat ubah password", Toast.LENGTH_SHORT).show()
                }catch (e:Exception){}
            }

        })
    }
}