package com.example.appodoctor.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appodoctor.R
import com.example.appodoctor.model.JadwalModel
import kotlinx.android.synthetic.main.rvjadwal_item.view.*

class RvJadwalAdapter (private val context: Context, private val listJadwal : ArrayList<JadwalModel>)
    : RecyclerView.Adapter<RvJadwalAdapter.RvJadwalViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvJadwalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rvjadwal_item, parent, false)
        return RvJadwalViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listJadwal.size
    }

    override fun onBindViewHolder(holder: RvJadwalViewHolder, position: Int) {
        holder.bind(listJadwal[position])
    }

    class RvJadwalViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private var lj : JadwalModel? = null
        fun bind(lj : JadwalModel){
            this.lj = lj
            with(itemView){
                with(lj){
                    tvDate.text = tgl
                    tvJamMulai.text = jamMulai
                    tvJamSelesai.text = jamSelesai
                }
            }
        }
    }

}