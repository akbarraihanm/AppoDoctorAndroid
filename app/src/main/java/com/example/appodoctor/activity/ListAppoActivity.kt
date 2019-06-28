package com.example.appodoctor.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.example.appodoctor.AppPreferences
import com.example.appodoctor.R
import com.example.appodoctor.adapter.RvListAppoAdapter
import com.example.appodoctor.model.Appointment
import com.example.appodoctor.presenter.AppointmentPresenter
import com.example.appodoctor.view.ListAppoView
import kotlinx.android.synthetic.main.activity_list_appo.*
import kotlinx.android.synthetic.main.activity_list_appo.tvIfNull
import kotlinx.android.synthetic.main.activity_manage_jadwal.*

class ListAppoActivity : AppCompatActivity(), ListAppoView {

    lateinit var appointmentPresenter : AppointmentPresenter
    lateinit var pref : AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_appo)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Daftar Janji"

        pref = AppPreferences(this)
        pref.setPreferences()

        tvDokName.text = pref.getNameUser()

        appointmentPresenter = AppointmentPresenter(this, this)
        rvlistAppo.layoutManager = LinearLayoutManager(this)
        appointmentPresenter.getItemListAppo(pref.getUserId())
    }

    override fun showListAppo(listAppo: ArrayList<Appointment>) {
        showLoading()
        if(listAppo.isEmpty()){
            tvIfNull.text = "Data tidak ada"
            hideLoading()
        }
        else{
            rvlistAppo.adapter = RvListAppoAdapter(this, listAppo)
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
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
