package com.example.appodoctor.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.appodoctor.R
import com.example.appodoctor.activity.AppoPasienActivity
import com.example.appodoctor.activity.ListAppoActivity
import com.example.appodoctor.model.Appointment
import com.example.appodoctor.model.PutPwResponse
import com.example.appodoctor.service.ApiClient
import com.example.appodoctor.service.ApiInterface
import kotlinx.android.synthetic.main.activity_list_appo.*
import kotlinx.android.synthetic.main.rvlistappo_doctor.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class RvListAppoAdapter (private val context: Context, private val listAppo : ArrayList<Appointment>)
    : RecyclerView.Adapter<RvListAppoAdapter.ListAppoViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ListAppoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rvlistappo_doctor, parent, false)
        return ListAppoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listAppo.size
    }

    override fun onBindViewHolder(holder: ListAppoViewHolder, i: Int) {
        holder.bind(listAppo[i], context, listAppo[i].idAppo.toString())

        Log.d("okok", listAppo[i].idAppo)
    }

    class ListAppoViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        val cvList = itemView.findViewById<CardView>(R.id.cvListAppo)
        val tvNaPas = itemView.findViewById<TextView>(R.id.tvNamaPasien)
        val tvTgl = itemView.findViewById<TextView>(R.id.tvTanggal)
        val tvStat = itemView.findViewById<TextView>(R.id.tvStatus)
        private var la : Appointment? = null
        @SuppressLint("NewApi")
        fun bind(la : Appointment, con : Context, id : String){
            this.la = la
            with(itemView){
                with(la){
                    tvNamaPasien.text = namaPasien
                    tvTanggal.text = tgl
                    tvStatus.text = status
                }
                if(la.status == "Diterima"){
                    cvListAppo.setCardBackgroundColor(resources.getColor(R.color.colorPrimary))
                    tvNamaPasien.setTextColor(resources.getColor(R.color.warnaPutih))
                    tvTanggal.setTextColor(resources.getColor(R.color.warnaPutih))
                    tvStatus.setTextColor(resources.getColor(R.color.warnaPutih))
                }
                else if(la.status == "Dibatalkan"){
                    cvListAppo.setCardBackgroundColor(resources.getColor(R.color.colorAccent))
                    tvNamaPasien.setTextColor(resources.getColor(R.color.warnaPutih))
                    tvTanggal.setTextColor(resources.getColor(R.color.warnaPutih))
                    tvStatus.setTextColor(resources.getColor(R.color.warnaPutih))
                }
                else if(la.status == "Menunggu"){
                    cvListAppo.setCardBackgroundColor(resources.getColor(R.color.warnaPutih))
                    tvNamaPasien.setTextColor(resources.getColor(R.color.warnaHitam))
                    tvTanggal.setTextColor(resources.getColor(R.color.warnaHitam))
                    tvStatus.setTextColor(resources.getColor(R.color.warnaHitam))
                }
            }
            itemView.cvListAppo.setOnClickListener {
                val i = Intent(con, AppoPasienActivity::class.java)
                i.putExtra(AppoPasienActivity.id_appo, id)
                con.startActivity(i)
                (con as ListAppoActivity).finish()
            }
        }

    }

}