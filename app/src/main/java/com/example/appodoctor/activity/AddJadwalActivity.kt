package com.example.appodoctor.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import com.example.appodoctor.AppPreferences
import com.example.appodoctor.HomeDokterActivity
import com.example.appodoctor.R
import com.example.appodoctor.fragment.HomeDocter
import com.example.appodoctor.model.JadwalModel
import com.example.appodoctor.presenter.AddJadwalPresenter
import com.example.appodoctor.presenter.JadwalPresenter
import com.example.appodoctor.view.AddJadwalView
import com.example.appodoctor.view.JadwalView
import kotlinx.android.synthetic.main.activity_add_jadwal.*
import java.util.*
import kotlin.collections.ArrayList

class AddJadwalActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, AddJadwalView {

    lateinit var pref : AppPreferences
    lateinit var addJadwalPresenter: AddJadwalPresenter
    lateinit var datePickerDialog: DatePickerDialog

    lateinit var timePickerDialog: TimePickerDialog

//    companion object{
//        var tgl = "a"
//        var jammulai = "b"
//        var jamselesai = "c"
//    }

    var tgl : String = ""
    var jammulai : String = ""
    var jamselesai : String = ""

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_jadwal)
        supportActionBar?.title = "Tambah Jadwal"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        pref = AppPreferences(this)
        pref.setPreferences()

        addJadwalPresenter = AddJadwalPresenter(this, this)



        tvTgl.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                onDateSet(view, year, month, dayOfMonth)
            }, year,month,day)


            datePickerDialog.datePicker.minDate = System.currentTimeMillis()
            datePickerDialog.show()
        }

        tvJamMulai.setOnClickListener {
            val time = Calendar.getInstance()
            val jam = time.get(Calendar.HOUR_OF_DAY)
            val menit = time.get(Calendar.MINUTE)

            timePickerDialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                onTimeSet(view, hourOfDay, minute)
            },jam, menit,true)

            timePickerDialog.show()
        }

        tvJamSelesai.setOnClickListener {
            val time = Calendar.getInstance()
            val jam2 = time.get(Calendar.HOUR_OF_DAY)
            val menit2 = time.get(Calendar.MINUTE)

            timePickerDialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, jam2, menit2 ->
                onTimeSet2(view, jam2, menit2)
            }, jam2, menit2, true)

            timePickerDialog.show()
        }

        btAdd.setOnClickListener {
            addJadwalPresenter.postAddJadwal(pref.getUserId(), tgl, jammulai, jamselesai)
        }

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        var selectedDate = ""+year+"-"+(month+1)+"-"+dayOfMonth
        tvTgl.text = selectedDate
        tgl = selectedDate
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        if (tvJamMulai.text.isNotEmpty()){
            if(hourOfDay < 10){
                if(minute < 10){
                    var selectedJam = "0"+hourOfDay+":"+"0"+minute
                    tvJamMulai.text = selectedJam
                    jammulai = selectedJam
                }
                else{
                    var selectedJam = "0"+hourOfDay+":"+minute
                    tvJamMulai.text = selectedJam
                    jammulai = selectedJam
                }
            }
            else if(hourOfDay >= 10) {
                if(minute < 10){
                    var selectedJam = ""+hourOfDay+":"+"0"+minute
                    tvJamMulai.text = selectedJam
                    jammulai = selectedJam
                }
                else {
                    var selectedJam = "" + hourOfDay + ":" + minute
                    tvJamMulai.text = selectedJam
                    jammulai = selectedJam
                }
            }
        }
    }

    fun onTimeSet2(view: TimePicker?, hourOfDay: Int, minute: Int) {
        if (tvJamSelesai.text.isNotEmpty()){
            if(hourOfDay < 10){
                if(minute < 10){
                    var selectedJam = "0"+hourOfDay+":"+"0"+minute
                    tvJamSelesai.text = selectedJam
                    jamselesai = selectedJam
                }
                else{
                    var selectedJam = "0"+hourOfDay+":"+minute
                    tvJamSelesai.text = selectedJam
                    jamselesai = selectedJam
                }
            }
            else if(hourOfDay >= 10) {
                if(minute < 10){
                    var selectedJam = ""+hourOfDay+":"+"0"+minute
                    tvJamSelesai.text = selectedJam
                    jamselesai = selectedJam
                }
                else {
                    var selectedJam = "" + hourOfDay + ":" + minute
                    tvJamSelesai.text = selectedJam
                    jamselesai = selectedJam
                }
            }
        }
    }

    override fun whenPostJadwal() {
        val i = Intent(this, ManageJadwalActivity::class.java)
        startActivity(i)
        finish()
        Toast.makeText(this, "Berhasil tambah jadwal", Toast.LENGTH_SHORT).show()

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
