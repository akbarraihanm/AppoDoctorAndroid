package com.example.appodoctor.listjanji

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View.*
import com.example.appodoctor.AppPreferences
import com.example.appodoctor.R
import com.example.appodoctor.adapter.RvListAppoAdapter
import com.example.appodoctor.model.*
import kotlinx.android.synthetic.main.activity_list_appo.*
import kotlinx.android.synthetic.main.activity_list_appo.tvIfNull
import kotlin.collections.ArrayList

class ListAppoActivity : AppCompatActivity(), ListAppoView {

// Canceled Implements
//    , DatePickerDialog.OnDateSetListener

    lateinit var appointmentPresenter : AppointmentPresenter
    lateinit var pref : AppPreferences

    companion object{
        var selectedIdJadwal = "kok"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_appo)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Daftar Janji"

        pref = AppPreferences(this)
        pref.setPreferences()
        appointmentPresenter = AppointmentPresenter(this, this)

        swipeRefreshLayout.isRefreshing = true

        tvDokName.text = pref.getNameUser()

        rvlistAppo.layoutManager = LinearLayoutManager(this)
        appointmentPresenter.getItemListAppo(pref.getUserApiKey(), pref.getUserId())
        swipeRefreshLayout.setOnRefreshListener {
            appointmentPresenter.getItemListAppo(pref.getUserApiKey(),pref.getUserId())
        }
    }

//    Canceled arraylist by Appointment
//    listAppo: ArrayList<Appointment>
    override fun showListAppo(listJadwal: ArrayList<JadwalModel>) {
        showLoading()
        if(listJadwal.isEmpty()){
            tvIfNull.text = "Data tidak ada"
            swipeRefreshLayout.isRefreshing = false
            hideLoading()
        }
        else{
            rvlistAppo.adapter = RvListAppoAdapter(this, listJadwal)
            swipeRefreshLayout.isRefreshing = false
            hideLoading()
        }
    }

    override fun showLoading() {
        pbLoadAppo.visibility = VISIBLE
    }

    override fun hideLoading() {
        pbLoadAppo.visibility = INVISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return when(item?.itemId){
            android.R.id.home ->{
                selectedIdJadwal = ""
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
