package com.example.appodoctor.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import com.example.appodoctor.HomeActivity
import com.example.appodoctor.R
import com.example.appodoctor.model.Pasien
import com.example.appodoctor.presenter.KonfirmasiPresenter
import com.example.appodoctor.view.KonfirmasiView
import kotlinx.android.synthetic.main.activity_konfirmasi.*

class KonfirmasiActivity : AppCompatActivity(), KonfirmasiView {

    lateinit var konfirmasiPresenter: KonfirmasiPresenter

    companion object{
        var poli_id = "poliid"
        var poliName = "poliname"
        var dokter_id = "dokid"
        var dokterName = "doktername"
        var tanggal = "tanggal"
        var id_pasien = "id pasien"

        var tappo = "tappo"
        var pid = "pid"
        var doid = "doid"
        var poid = "poid"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konfirmasi)

        linLayKonfirmasi.visibility = INVISIBLE

        supportActionBar?.title = "Cek Data"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        konfirmasiPresenter = KonfirmasiPresenter(this,this)

        poliName = intent.getStringExtra(poliName)
        dokterName = intent.getStringExtra(dokterName)
        tanggal = intent.getStringExtra(tanggal)
        poli_id = intent.getStringExtra(poli_id)
        dokter_id = intent.getStringExtra(dokter_id)
        id_pasien = intent.getStringExtra(id_pasien)

        tappo = tanggal
        poid = poli_id
        pid = id_pasien
        Log.d("idpas", pid)
        Log.d("poliid",poid)
        doid = dokter_id

        konfirmasiPresenter.getPasienItem(id_pasien)


        btBatal.setOnClickListener {
            val intent = Intent(this, BuatJanjiActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this, "Permintaan telah dibatalkan",Toast.LENGTH_SHORT).show()
        }
    }

    override fun showLoading() {
        pbKonfirmasi.visibility = VISIBLE
    }

    override fun hideLoading() {
        linLayKonfirmasi.visibility = VISIBLE
        pbKonfirmasi.visibility = INVISIBLE
    }

    override fun showItemPasien(itemPasien: ArrayList<Pasien>) {
        hideLoading()
        tvNamaPasien.text = itemPasien[0].namaPasien

        tvNamaPoli.text = poliName
        tvNamaDokter.text = dokterName
        tvTanggal.text = tanggal
        hideLoading()
        btBuatJanji.setOnClickListener {

            konfirmasiPresenter.setKonfirmasi(tappo, pid, dokter_id, poli_id)
        }
    }

    override fun whenPostAppo() {
        Toast.makeText(this, "Berhasil membuat janji",Toast.LENGTH_SHORT).show()
        val i = Intent(this, BuatJanjiActivity::class.java)
        startActivity(i)
        finish()
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
