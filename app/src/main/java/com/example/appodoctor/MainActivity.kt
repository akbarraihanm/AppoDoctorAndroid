package com.example.appodoctor

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.appodoctor.model.Firebase
import com.example.appodoctor.model.Pasien
import com.example.appodoctor.model.PutPwResponse
import com.example.appodoctor.presenter.LoginPresenter
import com.example.appodoctor.service.ApiClient
import com.example.appodoctor.service.ApiInterface
import com.example.appodoctor.view.LoginView
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_test_notification.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), LoginView {

    lateinit var loginPresenter: LoginPresenter
    lateinit var pref : AppPreferences

    companion object{
        var NODE_PASIENS = "pasiens"
        var TOKENKU = "tokenku"
        var NORM = "norm"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Masuk"

        pref = AppPreferences(this)
        pref.setPreferences()
        loginPresenter = LoginPresenter(this,this)

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
            if(etNoRm.text.toString().isEmpty()){
                etNoRm.error = "Nomor rekam medis harus diisi"
            }
            if(etPassword.text.toString().isEmpty()){
                etPassword.error = "Password harus diisi"
            }
            if(etNoRm.text.toString().isNotEmpty()&& etPassword.text.toString().isNotEmpty()){
                NORM = etNoRm.text.toString()
                Log.d("norm", NORM)

                FirebaseInstanceId.getInstance().instanceId
                    .addOnCompleteListener {
                            task ->
                        if(task.isSuccessful){
                            var token = task.result!!.token
                            TOKENKU = token
                            saveToken(token, etNoRm.text.toString())
                            Log.d("norm", TOKENKU)
                            updateToken(NORM, TOKENKU)
                        }
                        else{
                        }
                    }
                loginPresenter.login(etNoRm.text.toString(), etPassword.text.toString())
            }
        }

        tvMasukDokter.setOnClickListener {
            val i = Intent(this, LoginDokterActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun saveToken(token: String, noRm: String) {
        val firebase = Firebase(noRm,token)
        val dbFirebase = FirebaseDatabase.getInstance().getReference(NODE_PASIENS)
        val key = dbFirebase.push().key
        dbFirebase.child(""+key).setValue(firebase)
    }

    override fun doLogin(id: String, status : String, name : String) {
        pref.setUserId(id)
        pref.setUserLogin(status)
        Log.d("uid",pref.getUserId())
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun updateToken(norm : String, tokenPas : String){
        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val callUpToken = apiInterface.putPasienToken(norm, tokenPas)

        callUpToken.enqueue(object : Callback<PutPwResponse>{
            override fun onFailure(call: Call<PutPwResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Gagal update token\n"+t, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PutPwResponse>, response: Response<PutPwResponse>) {
                var statusPut = response.body()
                try {
                    if(statusPut!!.statusPut == "success"){
                        Toast.makeText(this@MainActivity, "Token berhasil ditambah", Toast.LENGTH_SHORT).show()
                    }
                }catch (e:Exception){}
            }

        })
    }
}
