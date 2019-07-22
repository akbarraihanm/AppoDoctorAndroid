package com.example.appodoctor.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appodoctor.R
import com.example.appodoctor.konfirmasijanji.AppoPasienActivity
import com.example.appodoctor.model.Appointment
import kotlinx.android.synthetic.main.rvappojadwal_item.view.*

class RvListAppoJadwalAdapter (private val context: Context, private var listAppo : ArrayList<Appointment>)
    : RecyclerView.Adapter<RvListAppoJadwalAdapter.ListAppoJadwalViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ListAppoJadwalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rvappojadwal_item, parent, false)
        return ListAppoJadwalViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listAppo.size
    }

    override fun onBindViewHolder(holder: ListAppoJadwalViewHolder, i: Int) {
        holder.bind(listAppo[i], context, listAppo[i].idAppo.toString())
    }

    class ListAppoJadwalViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private var la : Appointment? = null

        fun bind(la : Appointment, con : Context, id : String){
            this.la = la
            with(itemView){
                with(la){
                    tvNamaPasien2.text = namaPasien
                    tvStatus2.text = status
                }
                if(la.status == "Diterima"){
                    cvAppoJadwal.setCardBackgroundColor(resources.getColor(R.color.colorPrimary))
                    tvNamaPasien2.setTextColor(resources.getColor(R.color.warnaPutih))
                    tvStatus2.setTextColor(resources.getColor(R.color.warnaPutih))
                }
                else if(la.status == "Dibatalkan"){
                    cvAppoJadwal.setCardBackgroundColor(resources.getColor(R.color.colorAccent))
                    tvNamaPasien2.setTextColor(resources.getColor(R.color.warnaPutih))
                    tvStatus2.setTextColor(resources.getColor(R.color.warnaPutih))
                }
                else if(la.status == "Menunggu"){
                    cvAppoJadwal.setCardBackgroundColor(resources.getColor(R.color.warnaPutih))
                    tvNamaPasien2.setTextColor(resources.getColor(R.color.warnaHitam))
                    tvStatus2.setTextColor(resources.getColor(R.color.warnaHitam))
                }
                cvAppoJadwal.setOnClickListener {
                    val i = Intent(con, AppoPasienActivity::class.java)
                    i.putExtra(AppoPasienActivity.id_appo, id)
                    con.startActivity(i)
                }
            }
        }
    }

}