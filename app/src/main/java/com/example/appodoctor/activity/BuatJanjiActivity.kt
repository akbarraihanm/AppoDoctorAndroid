package com.example.appodoctor.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.util.Log
import android.widget.Toast
import com.example.appodoctor.R
import com.example.appodoctor.model.JadwalModel
import com.example.appodoctor.model.JadwalResponse
import com.example.appodoctor.service.ApiClient
import com.example.appodoctor.service.ApiInterface
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_buat_janji.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class BuatJanjiActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    lateinit var datePickerDialog: DatePickerDialog
    lateinit var tanggal : ArrayList<JadwalModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buat_janji)

//        btSelectDate.setOnClickListener {
//            getCalendarDate()
//        }
    }

    fun getCalendarDate(){
        val apiInterFace = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiInterFace.getJadwalByDokterId()
        var calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        datePickerDialog = DatePickerDialog.newInstance(this, year,month,dayOfMonth)

        val sdf = SimpleDateFormat("yyyy-MM-dd")

        call.clone().enqueue(object : Callback<JadwalResponse>{
            override fun onFailure(call: Call<JadwalResponse>, t: Throwable) {
                Toast.makeText(this@BuatJanjiActivity, "Gagal ambil item", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<JadwalResponse>, response: Response<JadwalResponse>) {
                tanggal = response.body()!!.data
                try {
                    val length = tanggal.count()

                    var date : Date? = null

                    for(i in 0..length){
                        try {
                            date = sdf.parse(tanggal[i].tgl)
                        }catch (e:Exception){}

                        calendar = dateToCalendar(date!!)

                        val dates : ArrayList<Calendar> = arrayListOf()

                        dates.add(calendar)

                        val displayedDay = dates.toArray(arrayOfNulls<Calendar>(dates.size))
                        datePickerDialog.selectableDays = displayedDay
                    }
                    datePickerDialog.show(fragmentManager,"")
                }
                catch (e:Exception){}
            }

        })
    }

    fun dateToCalendar(date: Date) : Calendar{
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        tvSelectedDate.text = ""+dayOfMonth+"-"+(monthOfYear+1)+"-"+year
    }
}

