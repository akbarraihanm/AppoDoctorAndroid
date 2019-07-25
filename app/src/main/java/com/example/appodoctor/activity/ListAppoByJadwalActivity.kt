package com.example.appodoctor.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import com.example.appodoctor.AppPreferences
import com.example.appodoctor.R
import com.example.appodoctor.adapter.RvListAppoJadwalAdapter
import com.example.appodoctor.model.Appointment
import com.example.appodoctor.presenter.AppoJadwalPresenter
import com.example.appodoctor.view.AppoJadwalView
import kotlinx.android.synthetic.main.activity_list_appo_by_jadwal.*

class ListAppoByJadwalActivity : AppCompatActivity(), AppoJadwalView {

    companion object{
        var idJadwalForAppo = "idjadwal"
        var tglAppo = "tgl"
        var jamMulai = "jamm"
        var jamSelesai = "jam"
    }

    lateinit var appoJadwalPresenter : AppoJadwalPresenter
    lateinit var pref : AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_appo_by_jadwal)

        pref = AppPreferences(this)
        pref.setPreferences()

        idJadwalForAppo = intent.getStringExtra(idJadwalForAppo)
        tglAppo = intent.getStringExtra(tglAppo)
        jamMulai = intent.getStringExtra(jamMulai)
        jamSelesai = intent.getStringExtra(jamSelesai)

        supportActionBar?.title = tglAppo
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvJam2.text = jamMulai+" - "+jamSelesai

        tvDataNull2.visibility = GONE

        appoJadwalPresenter = AppoJadwalPresenter(this, this)
        rvListAppo2.layoutManager = LinearLayoutManager(this)
        rvListAppo2.adapter = null
        appoJadwalPresenter.getAppoByJadwalItem(pref.getUserApiKey(),idJadwalForAppo)
    }

    override fun showLoading() {
        pbLoadAppo.visibility = VISIBLE
    }

    override fun hideLoading() {
        pbLoadAppo.visibility = GONE
    }

    override fun showItem(listAppo: ArrayList<Appointment>) {
        showLoading()
        if(listAppo.isEmpty()){
            tvDataNull2.visibility = VISIBLE
            hideLoading()
        }
        else{
            rvListAppo2.adapter = RvListAppoJadwalAdapter(this, listAppo)
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
