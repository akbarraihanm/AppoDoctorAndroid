package com.example.appodoctor.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appodoctor.R
import com.example.appodoctor.model.JadwalModel
import com.example.appodoctor.jadwaldokter.JadwalPresenter
import com.example.appodoctor.jadwaldokter.JadwalView
import kotlinx.android.synthetic.main.rvmanage_jadwal.view.*

class RvManageJadwalAdapter (private val context: Context, private val listJadwal : ArrayList<JadwalModel>, private val jadwalViewRv: JadwalView)
    : RecyclerView.Adapter<RvManageJadwalAdapter.ManageJadwalViewHolder>(){

    companion object{
        var id_Jadwal = "id_jadwal"
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ManageJadwalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rvmanage_jadwal, parent, false)
        return ManageJadwalViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listJadwal.size
    }

    override fun onBindViewHolder(holder: ManageJadwalViewHolder, i: Int) {
        holder.bind(listJadwal[i], context,jadwalViewRv, listJadwal[i].idJadwal.toString())
    }

    class ManageJadwalViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        private var lj : JadwalModel? = null
        fun bind(lj : JadwalModel, con : Context, jadwalView : JadwalView, idJadwal : String){
            var deleteJadPres = JadwalPresenter(con, jadwalView)
            this.lj = lj
            with(itemView){
                with(lj){
//                     id_Jadwal = idJadwal.toString()
                    tvDate.text = tgl
                    tvJamMulai.text = jamMulai
                    tvJamSelesai.text = jamSelesai
                }
                btHapus.setOnClickListener {
                    deleteJadPres.deleteJadwalItem(idJadwal)
                }
            }
        }
    }

}