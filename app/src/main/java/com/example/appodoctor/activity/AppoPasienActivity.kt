package com.example.appodoctor.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.example.appodoctor.R
import com.example.appodoctor.model.Appointment
import com.example.appodoctor.presenter.AppointmentPresenter
import com.example.appodoctor.presenter.StatusPresenter
import com.example.appodoctor.view.ListAppoView
import com.example.appodoctor.view.StatusView
import kotlinx.android.synthetic.main.activity_appo_pasien.*
import kotlinx.android.synthetic.main.activity_appo_pasien.tvNamaPasien
import kotlinx.android.synthetic.main.fragment_profile.*

class AppoPasienActivity : AppCompatActivity(), StatusView {

    companion object{
        var id_appo = "idappo"
    }

    lateinit var statusPresenter : StatusPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appo_pasien)
        id_appo = intent.getStringExtra(id_appo)

        supportActionBar?.title = "Detail Janji"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        statusPresenter = StatusPresenter(this,this)
        statusPresenter.getStatusByIdAppo(id_appo)

    }

    override fun showStatusId(objItem: ArrayList<Appointment>) {
        tvNamaPasien.text = objItem[0].namaPasien
        tvNoRmPasien.text = objItem[0].noRmPasien
        tvTanggal.text = objItem[0].tgl
        tvStatus.text = objItem[0].status
    }

    override fun showStatusItem(listStatus: ArrayList<Appointment>) {

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
