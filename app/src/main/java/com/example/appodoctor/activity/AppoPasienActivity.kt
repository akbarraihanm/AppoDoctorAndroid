package com.example.appodoctor.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import com.example.appodoctor.HomeDokterActivity
import com.example.appodoctor.R
import com.example.appodoctor.fragment.HomeDocter
import com.example.appodoctor.model.*
import com.example.appodoctor.presenter.AppointmentPresenter
import com.example.appodoctor.presenter.KonfirmasiPasien
import com.example.appodoctor.presenter.StatusPresenter
import com.example.appodoctor.service.ApiClient
import com.example.appodoctor.service.ApiInterface
import com.example.appodoctor.view.KonfirPasienView
import com.example.appodoctor.view.ListAppoView
import com.example.appodoctor.view.StatusView
import kotlinx.android.synthetic.main.activity_appo_pasien.*
import kotlinx.android.synthetic.main.activity_appo_pasien.tvNamaPasien
import kotlinx.android.synthetic.main.fragment_profile.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppoPasienActivity : AppCompatActivity(), StatusView, KonfirPasienView {

    companion object{
        var id_appo = "idappo"
        var TOKEN_PAS = "topas"
        var ID_PASIEN = "idpas"
    }

    lateinit var statusPresenter : StatusPresenter
    lateinit var konfirmasiPasien : KonfirmasiPasien

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appo_pasien)
        id_appo = intent.getStringExtra(id_appo)

        val context : Context = this

        supportActionBar?.title = "Detail Janji"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        linearLayout.visibility = INVISIBLE

        konfirmasiPasien = KonfirmasiPasien(this, this)

        statusPresenter = StatusPresenter(this,this)
        statusPresenter.getStatusByIdAppo(id_appo)

    }

    override fun showStatusId(objItem: ArrayList<Appointment>) {
        getTokenPasien(objItem[0].pasienId.toString())
        linearLayout.visibility = VISIBLE
//        var pasId = objItem[0].pasienId
//        ID_PASIEN = pasId!!
        tvNamaPasien.text = objItem[0].namaPasien
        tvNoRmPasien.text = objItem[0].noRmPasien
        tvTanggal.text = objItem[0].tgl
        tvJam.text = objItem[0].jamMulai+" ~ "+objItem[0].jamSelesai
        tvStatus.text = objItem[0].status
        if(objItem[0].status == "Menunggu"){
            btBatal.visibility = INVISIBLE
            tvKeterangan.visibility = INVISIBLE
        }
        else if(objItem[0].status == "Diterima"){
            tvKeterangan.visibility = INVISIBLE
            btTerima.visibility = INVISIBLE
            btBatal.visibility = VISIBLE

            etKeterangan.hint = "Alasan dibatalkan"
        }
        else if(objItem[0].status == "Dibatalkan"){
            btTerima.visibility = INVISIBLE
            btBatal.visibility = INVISIBLE
            etKeterangan.visibility = INVISIBLE
            tvKeterangan.visibility = VISIBLE

            tvKeterangan.text = objItem[0].keterangan
        }

        btTerima.setOnClickListener {
            val statusAppo = "Diterima"
            if(etKeterangan.text.isEmpty()){
//                etKeterangan.error = "Wajib diisi"
                val keteranganAppo = "-"
                val judul = "Notifikasi"
                val pesan = "Update status permintaan janji : "+statusAppo
                konfirmasiPasien.putAppoPasien(id_appo, statusAppo, keteranganAppo)
                Handler().postDelayed({
                    pushNotification(TOKEN_PAS, judul, pesan)
                }, 2000)
            }
            else {
                val keteranganAppo = etKeterangan.text.toString()
                val judul = "Notifikasi"
                val pesan = "Update status permintaan janji : "+statusAppo
                konfirmasiPasien.putAppoPasien(id_appo, statusAppo, keteranganAppo)
                Handler().postDelayed({
                    pushNotification(TOKEN_PAS, judul, pesan)
                }, 2000)
            }
        }

        btBatal.setOnClickListener {
            val statusAppo = "Dibatalkan"
            if(etKeterangan.text.isEmpty()){
                etKeterangan.error = "Wajib diisi"
            }
            else {
                val keteranganAppo = etKeterangan.text.toString()
                val judul = "Notifikasi"
                val pesan = "Update status permintaan janji : "+statusAppo
                konfirmasiPasien.putAppoPasien(id_appo, statusAppo, keteranganAppo)
                Handler().postDelayed({
                    pushNotification(TOKEN_PAS, judul, pesan)
                }, 2000)
            }
        }

        hideLoading()
    }

    private fun getTokenPasien(id : String){
        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val callToken = apiInterface.getPasienData(id)
        callToken.enqueue(object : Callback<PasienResponse> {
            override fun onFailure(call: Call<PasienResponse>, t: Throwable) {
                Toast.makeText(this@AppoPasienActivity, "Gagal ambil token", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PasienResponse>, response: Response<PasienResponse>) {
                val tokenPasien : ArrayList<Pasien> = response.body()!!.data
                try {
                    TOKEN_PAS = tokenPasien[0].tokenPasien.toString()
                }catch (e:Exception){}
            }

        })
    }


    private fun pushNotification(tokPas : String, title : String, bodyMessage : String){
        val send = ApiClient.pushNotif().create(ApiInterface::class.java)
        val callSend = send.pushNotification(tokPas, title, bodyMessage)
        callSend.enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@AppoPasienActivity, "Gagal push notif", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                try {
//
//                }catch (e:Exception){}
            }

        })
    }

    override fun showStatusItem(listStatus: ArrayList<Appointment>) {

    }

    override fun whenPutData() {
        val i = Intent(this, ListAppoActivity::class.java)
        startActivity(i)
        finish()
    }

    override fun showLoading() {
        pbLoadDetail.visibility = VISIBLE
    }

    override fun hideLoading() {
        pbLoadDetail.visibility = INVISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return when(item?.itemId){
            android.R.id.home ->{
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
