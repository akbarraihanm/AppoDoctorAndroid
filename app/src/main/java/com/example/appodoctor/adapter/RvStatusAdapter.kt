package com.example.appodoctor.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appodoctor.R
import com.example.appodoctor.model.Appointment
import kotlinx.android.synthetic.main.rvstatus_item.view.*

class RvStatusAdapter (private val context: Context, private val listStatus : ArrayList<Appointment>)
    : RecyclerView.Adapter<RvStatusAdapter.RvStatusViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvStatusViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rvstatus_item, parent, false)
        return RvStatusViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listStatus.size
    }

    override fun onBindViewHolder(holder: RvStatusViewHolder, position: Int) {
        holder.bind(listStatus[position])
    }

    class RvStatusViewHolder(itemView :View) : RecyclerView.ViewHolder(itemView){
        private var ls : Appointment? = null
        fun bind(ls : Appointment){
            this.ls = ls
            with(itemView){
                with(ls){
                    tvNamaPoli.text = namaPoli
                    tvNamaDokter.text = namaDokter
                    tvTanggal.text = tgl
                    tvJam.text = jamMulai+" ~ "+jamSelesai
                    tvStatus.text = status
                    if(ls.keterangan==""){
                        tvKeterangan.text = "   -"
                    }
                    else
                    tvKeterangan.text = keterangan
                }
            }
        }
    }

}