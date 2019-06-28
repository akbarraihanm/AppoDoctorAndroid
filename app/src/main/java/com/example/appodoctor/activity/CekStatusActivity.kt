package com.example.appodoctor.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.example.appodoctor.AppPreferences
import com.example.appodoctor.R
import com.example.appodoctor.adapter.RvStatusAdapter
import com.example.appodoctor.model.Appointment
import com.example.appodoctor.presenter.StatusPresenter
import com.example.appodoctor.view.StatusView
import kotlinx.android.synthetic.main.activity_cek_status.*

class CekStatusActivity : AppCompatActivity(), StatusView {
    override fun showStatusId(objItem: ArrayList<Appointment>) {

    }

    lateinit var pref : AppPreferences
    lateinit var statusPresenter: StatusPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cek_status)

        tvIfNull.visibility = INVISIBLE

        pref = AppPreferences(this)
        pref.setPreferences()
        var pasId = pref.getUserId()

        rvStatusAppo.layoutManager = LinearLayoutManager(this)

        statusPresenter = StatusPresenter(this,this)
        statusPresenter.getStatusItem(pasId)

        supportActionBar?.title = "Cek Status"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun showLoading() {
        pbStatus.visibility = VISIBLE
    }

    override fun hideLoading() {
        pbStatus.visibility = INVISIBLE
    }

    override fun showStatusItem(listStatus : ArrayList<Appointment>) {
        if(listStatus.isEmpty()){
            hideLoading()
            tvIfNull.visibility = VISIBLE
        }
        else{
            hideLoading()
            rvStatusAppo.adapter = RvStatusAdapter(this,listStatus)
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
