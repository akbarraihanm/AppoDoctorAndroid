package com.example.appodoctor.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.Toast
import com.example.appodoctor.AppPreferences
import com.example.appodoctor.MainActivity
import com.example.appodoctor.model.PutPwResponse
import com.example.appodoctor.presenter.ProfilPresenter
import com.example.appodoctor.service.ApiClient
import com.example.appodoctor.service.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogoutDialogPasien : DialogFragment(){
    lateinit var pref : AppPreferences
    lateinit var profilPresenter: ProfilPresenter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        pref = AppPreferences(context!!)
        pref.setPreferences()

        profilPresenter = ProfilPresenter(ProfileFragment(), context!!)

        return activity?.let{
            val builder = AlertDialog.Builder(it)
            builder
                .setMessage("Anda yakin ingin keluar?")
                .setPositiveButton("Keluar",
                    DialogInterface.OnClickListener { dialog, id ->
                        pref.setUserId("")
                        var normPasien = ProfileFragment.norm
                        updateToken(pref.getUserApiKey(),normPasien)
                        val i = Intent(context!!, MainActivity::class.java)
                        startActivity(i)
                        activity!!.finish()
                        ProfileFragment.norm = ""
                        Toast.makeText(context,"Berhasil keluar", Toast.LENGTH_SHORT).show()
                    })
                .setNegativeButton("Batal",
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })
            builder.create()
        }!!
    }

    private fun updateToken(apiKey : String, normPasien: String) {
        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val callUpToken = apiInterface.putPasienToken(apiKey, normPasien, "kosong")

        callUpToken.enqueue(object : Callback<PutPwResponse> {
            override fun onFailure(call: Call<PutPwResponse>, t: Throwable) {
//                Toast.makeText(context, "Gagal update token\n"+t, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PutPwResponse>, response: Response<PutPwResponse>) {
                var statusPut = response.body()
                try {
                    if(statusPut!!.statusPut == "success"){
//                        Toast.makeText(context, "Token berhasil ditambah", Toast.LENGTH_SHORT).show()
                    }
                }catch (e:Exception){}
            }

        })
    }

    fun newInstance(title : String) : LogoutDialogPasien{
        val frag = LogoutDialogPasien()
        val bundle = Bundle()
        bundle.putString("title",title)
        frag.arguments = bundle
        return frag
    }
}