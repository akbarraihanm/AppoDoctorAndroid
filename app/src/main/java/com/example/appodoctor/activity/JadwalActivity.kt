package com.example.appodoctor.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.appodoctor.R
import com.example.appodoctor.adapter.RvDokterAdapter
import com.example.appodoctor.model.DokterModel
import com.example.appodoctor.presenter.DokterPresenter
import com.example.appodoctor.service.ApiClient
import com.example.appodoctor.service.ApiInterface
import com.example.appodoctor.view.DokterView
import kotlinx.android.synthetic.main.activity_jadwal.*
import kotlinx.android.synthetic.main.activity_jadwal.view.*

class JadwalActivity : AppCompatActivity(), DokterView {

    lateinit var poliNameString : String
    lateinit var dokterPresenter: DokterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jadwal)
        supportActionBar?.title = "Daftar Dokter"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rvDokter.layoutManager = LinearLayoutManager(this)

        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiInterface.getDokterByPoli()

        val poliId = resources.getStringArray(R.array.poliId)
        val poliName = resources.getStringArray(R.array.poliName)

        val spinnerPoliAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, poliName)
        spinnerPoli.adapter = spinnerPoliAdapter

        spinnerPoli.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                poliNameString = spinnerPoli.selectedItem.toString()
                tvIfNull.visibility = INVISIBLE
                showLoading()
                rvDokter.adapter = null

                val apiInterface2 = ApiClient.getClient().create(ApiInterface::class.java)
                val call = apiInterface2.getDokterByPoliId(poliId[position].replace("f",""))
                dokterPresenter = DokterPresenter(call, this@JadwalActivity, this@JadwalActivity)
                dokterPresenter.getDokterItem()

            }

        }

        dokterPresenter = DokterPresenter(call, this, this)
        dokterPresenter.getDokterItem()

    }

    override fun showDokterItem(listDokter: ArrayList<DokterModel>) {
        if(listDokter.isEmpty()){
            tvIfNull.visibility = VISIBLE
            tvIfNull.text = "Data tidak ada"
        }
        else {

            rvDokter.adapter = RvDokterAdapter(this, listDokter){
                hideLoading()
            }
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

    override fun showLoading(){
        pbJadwal.visibility = VISIBLE
    }

    override fun hideLoading(){
        pbJadwal.visibility = INVISIBLE
    }

}
