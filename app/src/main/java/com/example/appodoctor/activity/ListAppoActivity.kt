package com.example.appodoctor.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.text.format.Time
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.View.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.appodoctor.AppPreferences
import com.example.appodoctor.R
import com.example.appodoctor.adapter.RvListAppoAdapter
import com.example.appodoctor.model.*
import com.example.appodoctor.presenter.AppointmentPresenter
import com.example.appodoctor.service.ApiClient
import com.example.appodoctor.service.ApiInterface
import com.example.appodoctor.view.ListAppoView
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_list_appo.*
import kotlinx.android.synthetic.main.activity_list_appo.tvIfNull
import kotlinx.android.synthetic.main.activity_manage_jadwal.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ListAppoActivity : AppCompatActivity(), ListAppoView {

// Canceled Implements
//    , DatePickerDialog.OnDateSetListener

    lateinit var appointmentPresenter : AppointmentPresenter
    lateinit var pref : AppPreferences
//    lateinit var datePickerDialog: DatePickerDialog
//
//    lateinit var jamString : String
//    lateinit var tglDipilih : String
//    lateinit var statDipilih : String

    companion object{
        var selectedIdJadwal = "kok"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_appo)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Daftar Janji"

//        tvSelectDate.visibility = GONE
//        spStatus.visibility = GONE
//        spJam.visibility = GONE

        pref = AppPreferences(this)
        pref.setPreferences()
        appointmentPresenter = AppointmentPresenter(this, this)

        swipeRefreshLayout.isRefreshing = true

        tvDokName.text = pref.getNameUser()

//        btTampil.visibility = GONE
//        btBatal.visibility = GONE
//
//        rgRadio.setOnCheckedChangeListener { group, checkedId ->
//            btTampil.visibility = VISIBLE
//            btBatal.visibility = VISIBLE
//        }
//        btTampil.setOnClickListener {
//            var rbId = rgRadio.checkedRadioButtonId
//            if(rbId == R.id.rbJadwal){
//                swipeRefreshLayout.isRefreshing = false
//                swipeRefreshLayout.isEnabled = false
//                spStatus.visibility = GONE
//                tvSelectDate.visibility = VISIBLE
//                tvSelectDate.setOnClickListener {
//                    viewByJadwal()
//                }
//            }
//            if(rbId == R.id.rbStatus){
//                swipeRefreshLayout.isRefreshing = false
//                swipeRefreshLayout.isEnabled = false
//                tvSelectDate.visibility = GONE
//                spJam.visibility = GONE
//                spStatus.visibility = VISIBLE
//                viewByStatus()
//            }
//        }
//        btBatal.setOnClickListener {
//            rgRadio.clearCheck()
//            swipeRefreshLayout.isEnabled = true
//            tvSelectDate.visibility = GONE
//            spStatus.visibility = GONE
//            spJam.visibility = GONE
//            btTampil.visibility = GONE
//            btBatal.visibility = GONE
//            tvIfNull.text = ""
//            tvSelectDate.text = "-Pilih Jadwal-"
//            showLoading()
//            rvlistAppo.adapter = null
//            appointmentPresenter.getItemListAppo(pref.getUserId())
//        }

        rvlistAppo.layoutManager = LinearLayoutManager(this)
        appointmentPresenter.getItemListAppo(pref.getUserId())
        swipeRefreshLayout.setOnRefreshListener {
            appointmentPresenter.getItemListAppo(pref.getUserId())
        }
    }

//    private fun viewByJadwal(){
//
//        var listJadwal : ArrayList<JadwalModel>
//        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
//        val call = apiInterface.getJadwalByDokterId(pref.getUserId())
//        call.clone().enqueue(object : Callback<JadwalResponse>{
//            override fun onFailure(call: Call<JadwalResponse>, t: Throwable) {
//                Toast.makeText(this@ListAppoActivity, "Gagal ambil item", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onResponse(call: Call<JadwalResponse>, response: Response<JadwalResponse>) {
//                listJadwal = response.body()!!.data
//                try {
//                    var calendar = Calendar.getInstance()
//                    val year = calendar.get(Calendar.YEAR)
//                    val month = calendar.get(Calendar.MONTH)
//                    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
//
//                    datePickerDialog = DatePickerDialog.newInstance(this@ListAppoActivity, year,month,dayOfMonth)
//
//                    val sdf = SimpleDateFormat("yyyy-MM-dd")
//
//                    var date : Date? = null
//
//                    val currenDate = SimpleDateFormat("yyyy-MM-dd")
//                    val curDate = currenDate.parse(currenDate.format(Date()))
//
//                    for(i in listJadwal.indices){
//                        try {
//                            date = sdf.parse(listJadwal[i].tgl)
//                            Log.d("isineIki",date.toString())
//                            Log.d("isineIki2",curDate.toString())
//                        }catch (e:Exception){}
//
//                        if(date!! > curDate){
//                            calendar = dateToCalendar(date!!)
//
//                            val dates : ArrayList<Calendar> = arrayListOf()
//
//                            dates.add(calendar)
//
//                            val displayedDay = dates.toArray(arrayOfNulls<Calendar>(dates.size))
//
//                            datePickerDialog.selectableDays = displayedDay
//                        }
//                    }
//                    datePickerDialog.minDate = calendar
//                    datePickerDialog.show(fragmentManager,"")
//                }catch (e:Exception){}
//            }
//
//        })
//    }

//    private fun viewByStatus(){
//        var statusAppo = arrayListOf<String>("Menunggu", "Diterima", "Dibatalkan")
//        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, statusAppo)
//        spStatus.adapter = spinnerAdapter
//        spStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//            }
//
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                statDipilih = spStatus.selectedItem.toString()
//                rvlistAppo.adapter = null
//                showLoading()
//                tvIfNull.text = ""
//                val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
//                val call = apiInterface.getAppoByStatus(statDipilih, pref.getUserId())
//                call.enqueue(object : Callback<AppoResponse>{
//                    override fun onFailure(call: Call<AppoResponse>, t: Throwable) {
//                        Toast.makeText(this@ListAppoActivity, "Koneksi gagal",Toast.LENGTH_SHORT).show()
//                    }
//
//                    override fun onResponse(call: Call<AppoResponse>, response: Response<AppoResponse>) {
//                        var listAppo = response.body()!!.data
//                        try {
//                            if(listAppo.isEmpty()){
//                                tvIfNull.text = "Data tidak ada"
//                                hideLoading()
//                            }
//                            else{
//                                rvlistAppo.adapter = RvListAppoAdapter(this@ListAppoActivity, listAppo)
//                                hideLoading()
//                            }
//                        }catch (e:Exception){}
//                    }
//
//                })
//            }
//
//        }
//    }

//    private fun dateToCalendar(date: Date) : Calendar{
//        val calendar = Calendar.getInstance()
//        calendar.time = date
//        return calendar
//    }

//    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
//        var selected = ""+year+"-"+(monthOfYear+1)+"-"+dayOfMonth
//        tvSelectDate.text = selected
//        tglDipilih = selected
//        spJam.visibility = VISIBLE
//        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
//        val call = apiInterface.getJadwalByTgl(tglDipilih, pref.getUserId())
//        var listJam : ArrayList<JadwalModel>
//        call.clone().enqueue(object : Callback<JadwalResponse>{
//            override fun onFailure(call: Call<JadwalResponse>, t: Throwable) {
//                Toast.makeText(this@ListAppoActivity, "Koneksi gagal", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onResponse(call: Call<JadwalResponse>, response: Response<JadwalResponse>) {
//                listJam = response.body()!!.data
//                var viewJam = arrayListOf<String>()
//                var idjam = arrayListOf<String>()
//                try {
//                    for(i in listJam.indices){
//                        viewJam.add(listJam[i].jamMulai!!+" ~ "+listJam[i].jamSelesai!!)
//                        idjam.add(listJam[i].idJadwal!!)
//                    }
//                    val spinnerAdapter = ArrayAdapter(this@ListAppoActivity, android.R.layout.simple_spinner_dropdown_item, viewJam)
//                    spJam.adapter = spinnerAdapter
//                    spJam.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//                        override fun onNothingSelected(parent: AdapterView<*>?) {
//
//                        }
//
//                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                            jamString = spJam.selectedItem.toString()
//                            selectedIdJadwal = idjam[position]
//                            rvlistAppo.adapter = null
//                            tvIfNull.text = ""
//                            showLoading()
//                            val apiJadwal = ApiClient.getClient().create(ApiInterface::class.java)
//                            val call = apiJadwal.getAppoByJadwalId(selectedIdJadwal)
//                            call.enqueue(object : Callback<AppoResponse>{
//                                override fun onFailure(call: Call<AppoResponse>, t: Throwable) {
//                                    Toast.makeText(this@ListAppoActivity, "Koneksi gagal", Toast.LENGTH_SHORT).show()
//                                }
//
//                                override fun onResponse(call: Call<AppoResponse>, response: Response<AppoResponse>) {
//                                    var listAppo = response.body()!!.data
//                                    try {
//                                        if(listAppo.isEmpty()){
//                                            tvIfNull.text = "Data tidak ada"
//                                            hideLoading()
//                                        }
//                                        else{
//                                            rvlistAppo.adapter = RvListAppoAdapter(this@ListAppoActivity, listAppo)
//                                            hideLoading()
//                                        }
//                                    }catch (e:Exception){}
//                                }
//
//                            })
//                        }
//
//                    }
//                }catch (e:Exception){}
//            }
//
//        })
//    }

//    Canceled arraylist by Appointment
//    listAppo: ArrayList<Appointment>
    override fun showListAppo(listJadwal: ArrayList<JadwalModel>) {
        showLoading()
//        val currentDate = SimpleDateFormat("yyyy-MM-dd")
//        val currentTime = SimpleDateFormat("HH:mm")
//        val curDate = currentDate.format(Date())
//        val curTime = currentTime.format(Date().time)
//        for(i in listAppo.indices){
//            if(listAppo[i].tgl!! < curDate){
//                val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
//                val callApi = apiInterface.deleteAppo(listAppo[i].tgl)
//                callApi.enqueue(object : Callback<PutPwResponse> {
//                    override fun onFailure(call: Call<PutPwResponse>, t: Throwable) {
//                        Toast.makeText(this@ListAppoActivity, ""+t, Toast.LENGTH_SHORT).show()
//                    }
//
//                    override fun onResponse(call: Call<PutPwResponse>, response: Response<PutPwResponse>) {
//                        try {
//
//                        }catch (e:Exception){}
//                    }
//
//                })
//            }
//            else
//                swipeRefreshLayout.isRefreshing = true
//        }
        if(listJadwal.isEmpty()){
            tvIfNull.text = "Data tidak ada"
            swipeRefreshLayout.isRefreshing = false
            hideLoading()
        }
        else{
            rvlistAppo.adapter = RvListAppoAdapter(this, listJadwal)
            swipeRefreshLayout.isRefreshing = false
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
                selectedIdJadwal = ""
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
