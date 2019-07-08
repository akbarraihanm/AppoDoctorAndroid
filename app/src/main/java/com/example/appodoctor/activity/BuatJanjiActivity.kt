package com.example.appodoctor.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.appodoctor.AppPreferences
import com.example.appodoctor.R
import com.example.appodoctor.model.JadwalModel
import com.example.appodoctor.model.JadwalResponse
import com.example.appodoctor.presenter.BuatJanjiPresenter
import com.example.appodoctor.service.ApiClient
import com.example.appodoctor.service.ApiInterface
import com.example.appodoctor.view.BuatJanjiView
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_buat_janji.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class BuatJanjiActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, BuatJanjiView {

    lateinit var datePickerDialog: DatePickerDialog
    lateinit var buatJanjiPresenter: BuatJanjiPresenter
    val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)

    val callPoli = apiInterface.getPoliName()
    val callDokter = apiInterface.getDokterByPoli()
    val callJadwal = apiInterface.getJadwalByDokter()

    lateinit var poliNameString : String
    lateinit var dokterNameString : String
    lateinit var jamString : String

    lateinit var pref : AppPreferences

    companion object{
        var selectedPoli = "poli"
        var selectedIdPoli = "idpoli"

        var selectedIdDokter = "iddokter"
        var selectedDokter = "dokter"

        var selectedJadwal = "jadwal"

        var idPasien = "idpasien"

        var selectedIdJadwal = "idjadwalasda"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buat_janji)
        pref = AppPreferences(this)
        pref.setPreferences()
        idPasien = pref.getUserId()
        Log.d("idpas", idPasien)

        val intent = Intent(this, KonfirmasiActivity::class.java)

        layoutBuatJanji.visibility = INVISIBLE
        tvDataNull.visibility = INVISIBLE
        tvpilihjadwal.visibility = INVISIBLE
        spJadwal.visibility = INVISIBLE

        supportActionBar?.title = "Buat Janji"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        buatJanjiPresenter = BuatJanjiPresenter(callPoli,callDokter,callJadwal, this,this)
        buatJanjiPresenter.getPoliItem()

        if(selectedJadwal.isEmpty()){
            btLanjut.alpha = .5f
            btLanjut.isEnabled = false
        }

        btLanjut.setOnClickListener {

            intent.putExtra(KonfirmasiActivity.poli_id, selectedIdPoli)
            intent.putExtra(KonfirmasiActivity.poliName, selectedPoli)
            intent.putExtra(KonfirmasiActivity.dokter_id, selectedIdDokter)
            intent.putExtra(KonfirmasiActivity.dokterName, selectedDokter)
            intent.putExtra(KonfirmasiActivity.tanggal_id, selectedIdJadwal)
            intent.putExtra(KonfirmasiActivity.id_pasien, idPasien)

            startActivity(intent)
            finish()

            Log.d("tombollanjut", selectedIdPoli)

        }

    }

    override fun showLoading() {
        pbJanji.visibility = VISIBLE
    }

    override fun hideLoading() {
        pbJanji.visibility = INVISIBLE
    }

    override fun showPoliItem(polName: ArrayList<String>, polId: ArrayList<String>) {

        val spinnerPoliAdapter  = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, polName)
        spPoli.adapter = spinnerPoliAdapter
        spPoli.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                poliNameString = spPoli.selectedItem.toString()
                selectedIdPoli = polId[position]
                Log.d("poliid", selectedIdPoli)
                selectedJadwal = ""
                selectedPoli = poliNameString
                showLoading()
                layoutBuatJanji.visibility = INVISIBLE
                tvSelectedDate.text = selectedJadwal
//                invisibleItems()
                val apiInterface2 = ApiClient.getClient().create(ApiInterface::class.java)
                val callDokter2 = apiInterface2.getDokterByPoliId(polId[position]!!)
                buatJanjiPresenter = BuatJanjiPresenter(callPoli,callDokter2,callJadwal,this@BuatJanjiActivity,this@BuatJanjiActivity)
                buatJanjiPresenter.getDokterItem()
                buatJanjiPresenter.getJadwalItem()
            }
        }
    }

    override fun showDokterItem(dokName: ArrayList<String>, dokId: ArrayList<String>) {
        hideLoading()
        layoutBuatJanji.visibility = VISIBLE
        if(selectedJadwal.isEmpty()){
            btLanjut.alpha = .5f
            btLanjut.isEnabled = false
        }
//        visibleItems()
        val spinnerDokterAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, dokName)
        spDokter.adapter = spinnerDokterAdapter
        spDokter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                dokterNameString = spDokter.selectedItem.toString()
                selectedJadwal = ""
                tvSelectedDate.text = selectedJadwal
                selectedDokter = dokterNameString
                Log.d("poliid", selectedIdPoli)
                selectedIdDokter = dokId[position]
                if(selectedJadwal.isEmpty()){
                    btLanjut.alpha = .5f
                    btLanjut.isEnabled = false
                    spJadwal.visibility = INVISIBLE
                    tvpilihjadwal.visibility = INVISIBLE
                }
                btSelectDate.visibility = INVISIBLE
                val apiInterface2 = ApiClient.getClient().create(ApiInterface::class.java)
                val callJadwal2 = apiInterface2.getJadwalByDokterId(dokId[position]!!)
                buatJanjiPresenter = BuatJanjiPresenter(callPoli,callDokter,callJadwal2, this@BuatJanjiActivity, this@BuatJanjiActivity)
                buatJanjiPresenter.getJadwalItem()
            }

        }
    }

    override fun showJadwalItem(listJadwal: ArrayList<JadwalModel>) {

        if(listJadwal.isEmpty()){
            btSelectDate.text = "Jadwal Tidak Ada"
            btSelectDate.alpha = .5f
            btSelectDate.isEnabled = false
            tvSelectedDate.text = ""
            btSelectDate.visibility = VISIBLE

            btLanjut.alpha = .5f
            btLanjut.isEnabled = false
        }
        else{
            btSelectDate.text = "Pilih Jadwal"
            btSelectDate.alpha = 1F
            btSelectDate.isEnabled = true
            btSelectDate.visibility = VISIBLE
            btSelectDate.setOnClickListener {
                var calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

                datePickerDialog = DatePickerDialog.newInstance(this, year,month,dayOfMonth)

                val sdf = SimpleDateFormat("yyyy-MM-dd")

                var date : Date? = null

                val currenDate = SimpleDateFormat("yyyy-MM-dd")
                val curDate = currenDate.parse(currenDate.format(Date()))

                for(i in listJadwal.indices){
                    try {
                        date = sdf.parse(listJadwal[i].tgl)
                        Log.d("isineIki",date.toString())
                        Log.d("isineIki2",curDate.toString())
                    }catch (e:Exception){}

                    if(date!! > curDate){
                        calendar = dateToCalendar(date!!)

                        val dates : ArrayList<Calendar> = arrayListOf()

                        dates.add(calendar)

                        val displayedDay = dates.toArray(arrayOfNulls<Calendar>(dates.size))

                        datePickerDialog.selectableDays = displayedDay
                    }
                }
                datePickerDialog.minDate = calendar
                datePickerDialog.show(fragmentManager,"")

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

    private fun dateToCalendar(date: Date) : Calendar{
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        var selected = ""+year+"-"+(monthOfYear+1)+"-"+dayOfMonth
        selectedJadwal = selected

        btLanjut.alpha = 1f
        btLanjut.isEnabled = true

        tvSelectedDate.text = selected

        tvpilihjadwal.visibility = VISIBLE
        spJadwal.visibility = VISIBLE
        var listJadwal : ArrayList<JadwalModel>

        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val callJadwal = apiInterface.getJadwalByTgl(selectedJadwal, selectedIdDokter)
        callJadwal.clone().enqueue( object : Callback<JadwalResponse>{
            override fun onFailure(call: Call<JadwalResponse>, t: Throwable) {
                Toast.makeText(this@BuatJanjiActivity, "Gagal ambil item", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<JadwalResponse>, response: Response<JadwalResponse>) {
                listJadwal = response.body()!!.data
                var jammulai  = arrayListOf<String>()
                var jamselesai = arrayListOf<String>()
                var idJadwal = arrayListOf<String>()
                try {
                    for(i in listJadwal.indices){
                        jammulai.add(listJadwal[i].jamMulai!!+" ~ "+listJadwal[i].jamSelesai)
//                        jamselesai.add(listJadwal[i].jamSelesai!!)
                        idJadwal.add(listJadwal[i].idJadwal!!)
                    }
                    val spinnerAdapter = ArrayAdapter(this@BuatJanjiActivity, android.R.layout.simple_spinner_dropdown_item, jammulai)
                    spJadwal.adapter = spinnerAdapter
                    spJadwal.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }

                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            jamString = spJadwal.selectedItem.toString()
                            selectedIdJadwal = idJadwal[position]
                            Log.d("idJadwal", selectedIdJadwal)
                        }

                    }
                }catch (e:Exception){}
            }

        })

    }
}

