package com.example.appodoctor.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import com.example.appodoctor.AppPreferences
import com.example.appodoctor.R
import com.example.appodoctor.activity.ListAppoByJadwalActivity
import com.example.appodoctor.model.AppoResponse
import com.example.appodoctor.model.JadwalModel
import com.example.appodoctor.service.ApiClient
import com.example.appodoctor.service.ApiInterface
import kotlinx.android.synthetic.main.rvlistappo_doctor.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

//Canceled Arraylist
//private val listAppo : ArrayList<Appointment>
class RvListAppoAdapter (private val context: Context, private val listJadwal : ArrayList<JadwalModel>)
    : RecyclerView.Adapter<RvListAppoAdapter.ListAppoViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ListAppoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rvlistappo_doctor, parent, false)
        return ListAppoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listJadwal.size
    }

    override fun onBindViewHolder(holder: ListAppoViewHolder, i: Int) {
        holder.bind(listJadwal[i], context, listJadwal[i].idJadwal.toString())
        Log.d("okok", listJadwal[i].idJadwal)
    }

    class ListAppoViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {

        companion object{
            var totalMenunggu = "0"
            var totalDiterima = "1"
            var totalDitolak = "2"
        }

        lateinit var pref : AppPreferences
//Canceled Model Object
//        private var la : Appointment? = null
        private var jd : JadwalModel? = null
        @SuppressLint("NewApi")
        fun bind(jd : JadwalModel, con : Context, id : String){
            this.jd = jd
            pref = AppPreferences(con)
            pref.setPreferences()
            with(itemView){
                with(jd){
//                    tvNamaPasien.text = namaPasien
                    tvTanggal.text = tgl
                    tvJamMulai.text = jamMulai
                    tvJamSelesai.text = jamSelesai
                    tvMenunggu.text = "0"
                    tvDiterima.text = "0"
                    tvDibatalkan.text = "0"

                    val currentDate = SimpleDateFormat("yyyy-MM-dd")
//                    val currentTime = SimpleDateFormat("HH:mm")
                    val curDate = currentDate.format(Date())

                    if(tgl!! >= curDate){
                        cvListAppo.visibility = VISIBLE
                        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
                        val call = apiInterface.getAppoByJadwalId(pref.getUserApiKey(),idJadwal)
                        call.enqueue(object : Callback<AppoResponse>{
                            override fun onFailure(call: Call<AppoResponse>, t: Throwable) {
                                Toast.makeText(context, "Koneksi gagal", Toast.LENGTH_SHORT).show()
                            }

                            override fun onResponse(call: Call<AppoResponse>, response: Response<AppoResponse>) {
                                var listAppo = response.body()!!.data
                                tvAntrian.text = listAppo.count().toString()
                                var countMenunggu = 0
                                var countDiterima = 0
                                var countDibatalkan = 0
                                var countAntrian = 0
                                try {

                                    for (i in listAppo.indices){
                                        if(listAppo.isEmpty()){
                                            tvMenunggu.text = "0"
                                            tvDiterima.text = "0"
                                            tvDibatalkan.text = "0"
                                        }
                                        else {
//                                            for (j in listAppo[i].status!!.indices) {
//
//
//                                            }
                                            if (listAppo[i].status == "Menunggu") {
                                                countMenunggu++
                                            }
                                            tvMenunggu.text = countMenunggu.toString()
                                            if (listAppo[i].status == "Diterima") {
                                                countDiterima++
                                            }
                                            tvDiterima.text = countDiterima.toString()
                                            if (listAppo[i].status == "Dibatalkan") {
                                                countDibatalkan++
                                            }
                                            tvDibatalkan.text = countDibatalkan.toString()
                                        }
                                    }
                                }catch (e:Exception){}
                            }

                        })
                    }
                    else if(tgl!! < curDate){
                        cvListAppo.visibility = GONE
                    }

//                    tvStatus.text = status
                }
            }
            itemView.cvListAppo.setOnClickListener {
                val i = Intent(con, ListAppoByJadwalActivity::class.java)
                i.putExtra(ListAppoByJadwalActivity.tglAppo, jd.tgl)
                i.putExtra(ListAppoByJadwalActivity.idJadwalForAppo, id)
                i.putExtra(ListAppoByJadwalActivity.jamMulai, jd.jamMulai)
                i.putExtra(ListAppoByJadwalActivity.jamSelesai, jd.jamSelesai)
                con.startActivity(i)
            }
        }

    }

}