package com.example.appodoctor.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import com.example.appodoctor.AppPreferences
import com.example.appodoctor.R
import com.example.appodoctor.adapter.RvManageJadwalAdapter
import com.example.appodoctor.model.JadwalModel
import com.example.appodoctor.presenter.JadwalPresenter
import com.example.appodoctor.view.JadwalView
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

        layoutManage.visibility = INVISIBLE

        rvJadwal.layoutManager = LinearLayoutManager(this)

        jadwalPresenter = JadwalPresenter(this, this)
        jadwalPresenter.getJadwalItem(id)

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

    override fun showJadwalItem(listJadwal: ArrayList<JadwalModel>) {
        showLoading()
        if(listJadwal.isEmpty()){
            tvIfNull.visibility = VISIBLE
            tvIfNull.text = "Data Tidak Ada"
            hideLoading()
        }
        else{
            layoutManage.visibility = VISIBLE
            tvNamaDokter.text = listJadwal[0].namaDokter
            rvJadwal.adapter = RvManageJadwalAdapter(this, listJadwal)
            hideLoading()
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
