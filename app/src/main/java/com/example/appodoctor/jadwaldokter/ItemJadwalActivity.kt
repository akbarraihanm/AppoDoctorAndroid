package com.example.appodoctor.jadwaldokter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.example.appodoctor.R
import com.example.appodoctor.adapter.RvJadwalAdapter
import com.example.appodoctor.model.JadwalModel
import kotlinx.android.synthetic.main.activity_item_jadwal.*

class ItemJadwalActivity : AppCompatActivity(), JadwalView {
    override fun whenDelete() {

    }

    companion object{
        var string_id = "string_id"
    }

    lateinit var jadwalPresenter: JadwalPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_jadwal)

        string_id = intent.getStringExtra(string_id)
        supportActionBar?.title = "Jadwal"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rvJadwal.layoutManager = LinearLayoutManager(this)

        tvIfNull.visibility = INVISIBLE
        tvNaDokWhenLoading.visibility = INVISIBLE
        jadwalPresenter = JadwalPresenter(this, this)
        jadwalPresenter.getJadwalItem(string_id)
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

    private fun showLoading() {
        pbLoadJadwal.visibility = VISIBLE
    }

    private fun hideLoading() {
        pbLoadJadwal.visibility = INVISIBLE
    }

    override fun showJadwalItem(listJadwal: ArrayList<JadwalModel>) {
        if(listJadwal.isEmpty()){
            tvIfNull.visibility = VISIBLE
            tvIfNull.text = "Data tidak ada"
            hideLoading()
        }
        else {
            tvNaDokWhenLoading.visibility = VISIBLE
            tvNamaDokter.text = listJadwal[0].namaDokter
            rvJadwal.adapter = RvJadwalAdapter(this, listJadwal)
            hideLoading()
        }
    }
}
