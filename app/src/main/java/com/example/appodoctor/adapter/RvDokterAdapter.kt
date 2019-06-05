package com.example.appodoctor.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appodoctor.HomeActivity
import com.example.appodoctor.R
import com.example.appodoctor.activity.ItemJadwalActivity
import com.example.appodoctor.activity.JadwalActivity
import com.example.appodoctor.model.DokterModel
import kotlinx.android.synthetic.main.rvdokter_item.view.*

class RvDokterAdapter (private val context: Context, private val listDokter : ArrayList<DokterModel>, private val listener : (DokterModel)->Unit)
    : RecyclerView.Adapter<RvDokterAdapter.RvDokterViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvDokterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rvdokter_item, parent, false)
        return RvDokterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listDokter.size
    }

    override fun onBindViewHolder(holder: RvDokterViewHolder, position: Int) {
        holder.bind(listDokter[position])
        holder.bind2(context, listDokter[position].idDokter.toString())
    }
    class RvDokterViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private var ld : DokterModel? = null
        fun bind(ld : DokterModel){
            this.ld = ld
            with(itemView){
                with(ld){

                    tvNamaDokter.text = namaDokter
                    tvSpesialis.text = namaPoli
                }
            }
        }
        fun bind2(con : Context, id : String){
            val cvClick = itemView.cvDokterItem
            cvClick.cvDokterItem.setOnClickListener {
                val intent = Intent(JadwalActivity@con, ItemJadwalActivity::class.java)
                intent.putExtra(ItemJadwalActivity.string_id, id)
                cvClick.context.startActivity(intent)
            }
        }
    }

}