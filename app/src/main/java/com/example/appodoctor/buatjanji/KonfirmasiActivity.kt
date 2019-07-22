package com.example.appodoctor.buatjanji

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import com.example.appodoctor.R
import com.example.appodoctor.activity.BuatJanjiActivity
import com.example.appodoctor.model.DokterModel
import com.example.appodoctor.model.DokterResponse
import com.example.appodoctor.model.JadwalModel
import com.example.appodoctor.model.Pasien
import com.example.appodoctor.service.ApiClient
import com.example.appodoctor.service.ApiInterface
import kotlinx.android.synthetic.main.activity_konfirmasi.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KonfirmasiActivity : AppCompatActivity(), KonfirmasiView {

    lateinit var konfirmasiPresenter: KonfirmasiPresenter

    companion object{
        var poli_id = "poliid"
        var poliName = "poliname"
        var dokter_id = "dokid"
        var dokterName = "doktername"
        var tanggal_id = "tanggal"
        var id_pasien = "id pasien"

        var tappo = "tappo"
        var pid = "pid"
        var doid = "doid"
        var poid = "poid"

        var TOKEN_DOC = "token"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konfirmasi)

        linLayKonfirmasi.visibility = INVISIBLE

        supportActionBar?.title = "Cek Data"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        konfirmasiPresenter = KonfirmasiPresenter(this, this)

        poliName = intent.getStringExtra(poliName)
        dokterName = intent.getStringExtra(dokterName)
        tanggal_id = intent.getStringExtra(tanggal_id)
        poli_id = intent.getStringExtra(poli_id)
        dokter_id = intent.getStringExtra(dokter_id)
        id_pasien = intent.getStringExtra(id_pasien)

        tappo =
            tanggal_id
        poid =
            poli_id
        pid =
            id_pasien
        Log.d("idpas", pid)
        Log.d("poliid", poid)
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
        konfirmasiPresenter.getJadwalItem(tappo)
    }

    override fun showItemJadwal(itemJadwal: ArrayList<JadwalModel>) {
        tvTanggal.text = itemJadwal[0].tgl
        tvJam.text = itemJadwal[0].jamMulai+" ~ "+itemJadwal[0].jamSelesai
        getTokenDokter()
        hideLoading()
        btBuatJanji.setOnClickListener {
            var judul = "Notifikasi"
            var body = "Ada Permintaan Janji Baru !"
            konfirmasiPresenter.setKonfirmasi(
                tappo,
                pid,
                dokter_id,
                poli_id
            )
            pushNotification(TOKEN_DOC, judul, body)
        }
    }

    override fun whenPostAppo() {
        Toast.makeText(this, "Berhasil membuat janji",Toast.LENGTH_SHORT).show()
        val i = Intent(this, BuatJanjiActivity::class.java)
        tappo = ""
        pid = ""
        dokter_id = ""
        poli_id = ""
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

    private fun getTokenDokter(){
        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val callToken = apiInterface.getDokterById(dokter_id)
        callToken.enqueue(object : Callback<DokterResponse>{
            override fun onFailure(call: Call<DokterResponse>, t: Throwable) {
                Toast.makeText(this@KonfirmasiActivity, "Gagal ambil token", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<DokterResponse>, response: Response<DokterResponse>) {
                var tokenDokter : ArrayList<DokterModel>
                tokenDokter = response.body()!!.data
                try {
                    TOKEN_DOC = tokenDokter[0]!!.tokenDokter.toString()
                }catch (e:Exception){}
            }

        })
    }

    private fun pushNotification(tokDok : String, title : String, bodyMessage : String){
        val send = ApiClient.pushNotif().create(ApiInterface::class.java)
        val callSend = send.pushNotification(tokDok, title, bodyMessage)
        callSend.enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@KonfirmasiActivity, "Gagal push notif", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {

                }catch (e:Exception){}
            }

        })
    }
}
