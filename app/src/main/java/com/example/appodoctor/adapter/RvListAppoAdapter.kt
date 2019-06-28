package com.example.appodoctor.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appodoctor.R
import com.example.appodoctor.activity.AppoPasienActivity
import com.example.appodoctor.model.Appointment
import kotlinx.android.synthetic.main.rvlistappo_doctor.view.*

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
        private var la : Appointment? = null
        fun bind(la : Appointment, con : Context, id : String){
            this.la = la
            with(itemView){
                with(la){
                    tvNamaPasien.text = namaPasien
                    tvTanggal.text = tgl
                }
            }
            itemView.cvListAppo.setOnClickListener {
                val i = Intent(con, AppoPasienActivity::class.java)
                i.putExtra(AppoPasienActivity.id_appo, id)
                con.startActivity(i)
            }
        }
    }

}