package com.example.appodoctor.adapter

import android.animation.AnimatorInflater
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.appodoctor.R
import com.example.appodoctor.activity.BuatJanjiActivity
import com.example.appodoctor.activity.JadwalActivity
import com.example.appodoctor.model.ListMenuModel
import kotlinx.android.synthetic.main.listview_item.view.*

class ListAdapter(val context : Context, private val listMenuModel : ArrayList<ListMenuModel>) : BaseAdapter(){
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder : ViewHolder
        var convertView = convertView
        if(convertView == null){
            holder = ViewHolder()
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.listview_item, null, true)

            holder.ivMenu = convertView.ivMenu
            holder.tvMenu = convertView.tvMenu
            holder.tvSubMenu = convertView.tvSubMenu
            holder.cvItem = convertView.cvItem

            convertView.tag = holder
        }
        else{
            holder = convertView.tag as ViewHolder
        }

        val stateListAnimator = AnimatorInflater.loadStateListAnimator(context, R.anim.animation)
        holder.cvItem!!.stateListAnimator = stateListAnimator

        holder.ivMenu!!.setImageResource(listMenuModel[position].pict!!)
        holder.tvMenu!!.text = listMenuModel[position].menu.toString()
        holder.tvSubMenu!!.text = listMenuModel[position].subMenu.toString()

        holder.cvItem!!.setOnClickListener {
            if(position == 0){
                val intent = Intent(context, JadwalActivity::class.java)
                context.startActivity(intent)
            }
            else if(position == 1){
                val intent = Intent(context, BuatJanjiActivity::class.java)
                context.startActivity(intent)
            }
            else {
                Toast.makeText(context, "Item dipilih : " + listMenuModel[position].menu.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        }

        return convertView!!
    }

    override fun getItem(position: Int): Any {

        return listMenuModel[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return listMenuModel.size
    }

    private inner class ViewHolder{

        var ivMenu : ImageView? = null
        var tvMenu : TextView? = null
        var tvSubMenu : TextView? = null
        var cvItem : CardView? = null
    }

}

