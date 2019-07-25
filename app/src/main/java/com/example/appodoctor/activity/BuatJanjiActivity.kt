package com.example.appodoctor.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.View.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.appodoctor.AppPreferences
import com.example.appodoctor.R
import com.example.appodoctor.buatjanji.KonfirmasiActivity
import com.example.appodoctor.model.AppoResponse
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

class BuatJanjiActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, BuatJanjiView {

    lateinit var datePickerDialog: DatePickerDialog
    lateinit var buatJanjiPresenter: BuatJanjiPresenter
    val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)

    lateinit var poliNameString : String
    lateinit var dokterNameString : String
    lateinit var jamString : String

    var count = 0

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

        val callPoli = apiInterface.getPoliName(pref.getUserApiKey())
        val callDokter = apiInterface.getDokterByPoli(pref.getUserApiKey())
        val callJadwal = apiInterface.getJadwalByDokter(pref.getUserApiKey())

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
            btLanjut.alpha = .4f
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

        val spinnerPoliAdapter  = ArrayAdapter(applicationContext, R.layout.spinner_item, polName)
        spinnerPoliAdapter.setDropDownViewResource(R.layout.spinner_item)
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
                val callDokter2 = apiInterface2.getDokterByPoliId(pref.getUserApiKey(),polId[position]!!)
                val callPoli = apiInterface.getPoliName(pref.getUserApiKey())
                val callJadwal = apiInterface.getJadwalByDokter(pref.getUserApiKey())
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
            btLanjut.alpha = .4f
            btLanjut.isEnabled = false
        }
//        visibleItems()
        val spinnerDokterAdapter = ArrayAdapter(applicationContext, R.layout.spinner_item, dokName)
        spinnerDokterAdapter.setDropDownViewResource(R.layout.spinner_item)
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
                val callJadwal2 = apiInterface2.getJadwalByDokterId(pref.getUserApiKey(),dokId[position]!!)
                val callPoli = apiInterface.getPoliName(pref.getUserApiKey())
                val callDokter = apiInterface.getDokterByPoli(pref.getUserApiKey())
                buatJanjiPresenter = BuatJanjiPresenter(callPoli,callDokter,callJadwal2, this@BuatJanjiActivity, this@BuatJanjiActivity)
                buatJanjiPresenter.getJadwalItem()
            }

        }
    }

    override fun showJadwalItem(listJadwal: ArrayList<JadwalModel>) {

        var calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val sdf = SimpleDateFormat("yyyy-MM-dd")

        var date : Date? = null

        val currenDate = SimpleDateFormat("yyyy-MM-dd")
        val curDate = currenDate.parse(currenDate.format(Date()))

        if(listJadwal.isEmpty()){
            btSelectDate.text = "Jadwal Tidak Ada"
            btSelectDate.alpha = .4f
            btSelectDate.isEnabled = false
            tvSelectedDate.text = ""
            btSelectDate.visibility = VISIBLE

            btLanjut.alpha = .4f
            btLanjut.isEnabled = false
        }
        else{
            btSelectDate.text = "Pilih Jadwal"
            btSelectDate.alpha = 1F
            btSelectDate.isEnabled = true
            btSelectDate.visibility = VISIBLE
            btSelectDate.setOnClickListener {

                datePickerDialog = DatePickerDialog.newInstance(this, year,month,dayOfMonth)

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

        tvSelectedDate.text = selected
        tvpilihjadwal.visibility = GONE
        spJadwal.visibility = GONE
        showLoading()

        var listJadwal : ArrayList<JadwalModel>

        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val callJadwal = apiInterface.getJadwalByTgl(selectedJadwal, selectedIdDokter)
        callJadwal.clone().enqueue( object : Callback<JadwalResponse>{
            override fun onFailure(call: Call<JadwalResponse>, t: Throwable) {
                Toast.makeText(this@BuatJanjiActivity, "Koneksi gagal", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<JadwalResponse>, response: Response<JadwalResponse>) {
                listJadwal = response.body()!!.data
                val jammulai  = arrayListOf<String>()
                val idJadwal = arrayListOf<String>()
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
                            val formatter = SimpleDateFormat("HH:mm")
                            val format1 = formatter.parse(listJadwal[position].jamMulai)
                            val format2 = formatter.parse(listJadwal[position].jamSelesai)
//                            val formatted1 = formatter.format(format1)
//                            val formatted2 = formatter.format(format2)
                            Log.d("selectedJadwal", selectedIdJadwal)
                            appoByJadwalId(selectedIdJadwal, format1, format2)
//                            setDifferenTime(format1, format2)
//                            setDifferenTime(format1, format2)

                            Log.d("idJadwal", selectedIdJadwal)
                        }

                    }
                }catch (e:Exception){}
            }

        })

        tvpilihjadwal.visibility = VISIBLE
        spJadwal.visibility = VISIBLE
        hideLoading()

    }

    private fun appoByJadwalId(jadwalId : String,time1 : Date, time2 : Date){
        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiInterface.getAppoByJadwalId(pref.getUserApiKey(),jadwalId)
        call.enqueue(object : Callback<AppoResponse>{
            override fun onFailure(call: Call<AppoResponse>, t: Throwable) {
                Toast.makeText(this@BuatJanjiActivity, "Koneksi gagal", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<AppoResponse>, response: Response<AppoResponse>) {
                val listAppo = response.body()!!.data
                count = 0
                try {
                    for(i in listAppo.indices){
                        count++

                    }
                    var different = time2.time - time1.time

                    val miliSecond = 1000
                    val miliMinutes = miliSecond * 60
                    val miliHours = miliMinutes * 60

                    val elapsedHours = different / miliHours

                    val hasil = ""+elapsedHours
                    Log.d("hasilkurang", hasil)
//        +":"+elapsedMinutes+":"+elapsedSeconds

                    val batas  = 6
                    var j = 1

                    for(i in 0..hasil.toInt()){
                        if(hasil == j.toString()){
                            if(count < (batas*j)){
                                btLanjut.alpha = 1f
                                btLanjut.isEnabled = true
                            }
                            else{
                                btLanjut.alpha = .4f
                                btLanjut.isEnabled = false
                                Toast.makeText(applicationContext, "Kuota antrian penuh, silahkan pilih hari lain", Toast.LENGTH_SHORT).show()
                            }
                        }
                        j++
                    }
                    Log.d("countLog",count.toString())
                }catch (e:Exception){}
            }

        })
    }
}

