package com.example.appodoctor

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.appodoctor.presenter.LoginPresenter
import com.example.appodoctor.view.LoginView
import kotlinx.android.synthetic.main.activity_login_dokter.*

class LoginDokterActivity : AppCompatActivity(), LoginView {

    lateinit var loginPresenter : LoginPresenter
    lateinit var pref : AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_dokter)

        pref = AppPreferences(this)
        pref.setPreferences()
        loginPresenter = LoginPresenter(this, this)

        if(pref.getUserId() != ""){
            if(pref.getUserLogin() == "dokter"){
                val intent = Intent(this, HomeDokterActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        btMasuk.setOnClickListener {
            if(etTlp.text.toString().isEmpty()){
                etTlp.error = "Nomor telepon harus diisi"
            }
            if(etPassword.text.toString().isEmpty()){
                etPassword.error = "Password harus diisi"
            }
            if(etTlp.text.toString().isNotEmpty()&& etPassword.text.toString().isNotEmpty()){
                loginPresenter.loginDokter(etTlp.text.toString(), etPassword.text.toString())
            }
        }

        tvMasukPasien.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    override fun doLogin(id: String, status : String) {
        pref.setUserId(id)
        pref.setUserLogin(status)
        Log.d("uid",pref.getUserId())
        val intent = Intent(this, HomeDokterActivity::class.java)
        startActivity(intent)
        finish()
    }
}
