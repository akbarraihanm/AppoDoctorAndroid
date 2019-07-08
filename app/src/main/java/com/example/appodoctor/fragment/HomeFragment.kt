package com.example.appodoctor.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.ViewGroup
import com.example.appodoctor.AppPreferences
import com.example.appodoctor.HomeActivity

import com.example.appodoctor.R
import com.example.appodoctor.activity.TestNotificationActivity
import com.example.appodoctor.adapter.ListAdapter
import com.example.appodoctor.model.ListMenuModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *1
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var listAdapter: ListAdapter
    lateinit var pref : AppPreferences

    companion object{
        var tokenPasien = "asdasd"
    }
//    lateinit var listMenuModel : ArrayList<ListMenuModel>

    private val listMenu = arrayOf("Jadwal Dokter","Buat Janji", "Cek Status")
    private val listSubMenu = arrayOf("Melihat jadwal dokter", "Membuat permintaan \njanji", "Melihat status \npermintaan janji")
    private val listPictMenu = intArrayOf(R.mipmap.ic_jadwal, R.mipmap.ic_create, R.mipmap.ic_check)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_home, container, false)
        pref = AppPreferences(context!!)
        pref.setPreferences()

        Log.d("tokenASD", tokenPasien)
        view.btTestNotif.visibility = GONE

//        view.btTestNotif.setOnClickListener {
//            val i = Intent(context!!, TestNotificationActivity::class.java)
//            startActivity(i)
//        }

//        listMenuModel = populateList()
        listAdapter = ListAdapter(context!!, populateList())
        view.lvHomeFragment.adapter = listAdapter

        return view
    }

    private fun populateList() : ArrayList<ListMenuModel>{
        val list = ArrayList<ListMenuModel>()

        for(i in 0..2){
            val listMenuModel = ListMenuModel(i,param1,param2)
            listMenuModel.pict = listPictMenu[i]
            listMenuModel.menu = listMenu[i]
            listMenuModel.subMenu = listSubMenu[i]
            list.add(listMenuModel)
        }

        return list
    }

}
