package com.example.appodoctor.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.appodoctor.AppPreferences
import com.example.appodoctor.R
import com.example.appodoctor.adapter.RvManageJadwalAdapter
import com.example.appodoctor.model.JadwalModel
import com.example.appodoctor.jadwaldokter.JadwalPresenter
import com.example.appodoctor.tambahjadwal.AddJadwalActivity
import com.example.appodoctor.jadwaldokter.JadwalView
import kotlinx.android.synthetic.main.activity_manage_jadwal.*

class ManageJadwalActivity : AppCompatActivity(), JadwalView {

    lateinit var jadwalPresenter : JadwalPresenter
    lateinit var pref : AppPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_jadwal)
        supportActionBar?.title = "Atur Jadwal"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val anim = AnimationUtils.loadAnimation(this, R.anim.rotate)
        btAdd.animation = anim

        pref = AppPreferences(this)
        pref.setPreferences()
        var id = pref.getUserId()
        Log.d("hahaha",id)

        swManage.isRefreshing = true

        swManage.setOnRefreshListener {
            jadwalPresenter = JadwalPresenter(this, this)
            jadwalPresenter.getJadwalItem(pref.getUserApiKey(),id)
        }

        layoutManage.visibility = INVISIBLE

        rvJadwal.layoutManager = LinearLayoutManager(this)

        jadwalPresenter = JadwalPresenter(this, this)
        jadwalPresenter.getJadwalItem(pref.getUserApiKey(),id)

//        swManage.isRefreshing = false

        btAdd.setOnClickListener {
            val i = Intent(this, AddJadwalActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun showLoading() {
        pbLoadJadwal.visibility = VISIBLE
    }

    private fun hideLoading() {
        pbLoadJadwal.visibility = INVISIBLE
    }

    override fun whenDelete() {
        pref = AppPreferences(this)
        pref.setPreferences()
        var id = pref.getUserId()
        rvJadwal.adapter = null
        showLoading()
        Handler().postDelayed({
//            finish()
//            startActivity(intent)
            Toast.makeText(this, "Berhasil hapus jadwal", Toast.LENGTH_SHORT).show()
            jadwalPresenter.getJadwalItem(pref.getUserApiKey(),id)
        }, 1500)
    }

    override fun showJadwalItem(listJadwal: ArrayList<JadwalModel>) {
        showLoading()
        swManage.isRefreshing = true
        pref = AppPreferences(this)
        pref.setPreferences()
        if(listJadwal.isEmpty()){
//            swManage.isRefreshing = true
            tvIfNull.visibility = VISIBLE
            layoutManage.visibility = VISIBLE
            tvNamaDokter.text = pref.getNameUser()
            tvIfNull.text = "Data Tidak Ada"
            hideLoading()
            swManage.isRefreshing = false
        }
        else{
//            swManage.isRefreshing = true
            layoutManage.visibility = VISIBLE
            tvNamaDokter.text = pref.getNameUser()
            rvJadwal.adapter = RvManageJadwalAdapter(this, listJadwal,this)
            hideLoading()
            swManage.isRefreshing = false
        }
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
