package com.example.appodoctor.presenter

import android.content.Context
import android.widget.Toast
import com.example.appodoctor.model.LoginResponse
import com.example.appodoctor.service.ApiClient
import com.example.appodoctor.service.ApiInterface
import com.example.appodoctor.view.LoginView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter(private val loginView: LoginView,
                     private val context: Context){
    fun login(norm : String?, pwPasien : String?){
        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val callLogin = apiInterface.postLogin(norm, pwPasien)

        callLogin.enqueue(object : Callback<LoginResponse>{
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(context, "Koneksi gagal", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                var statLogin = response.body()
                try {
                    if(statLogin!!.status == "sukses"){
                        loginView.doLogin(statLogin.idPasien.toString(), statLogin.user.toString(), "", statLogin.apiKey.toString())
                        Toast.makeText(context, "Berhasil masuk",Toast.LENGTH_SHORT).show()
                    }
                    else Toast.makeText(context, "Nomor rekam medis atau password salah", Toast.LENGTH_SHORT).show()
                }catch (e:Exception){}
            }

        })
    }

    fun loginDokter(tlp : String?, pwDokter : String?){
        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val callLoginDokter = apiInterface.postLoginDokter(tlp, pwDokter)

        callLoginDokter.enqueue(object : Callback<LoginResponse>{
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(context, "Koneksi gagal", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                var statLogin = response.body()
                try {
                    if(statLogin!!.status == "sukses"){
                        loginView.doLogin(statLogin.idDokter.toString(), statLogin.user.toString(), statLogin.namaDokter.toString(), statLogin.apiKey.toString())
                        Toast.makeText(context, "Berhasil masuk",Toast.LENGTH_SHORT).show()
                    }
                    else Toast.makeText(context, "Nomor telepon atau password salah", Toast.LENGTH_SHORT).show()
                }catch (e:Exception){}
            }

        })
    }
}