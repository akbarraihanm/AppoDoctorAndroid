package com.example.appodoctor.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appodoctor.AppPreferences
import com.example.appodoctor.LoginDokterActivity

import com.example.appodoctor.R
import com.example.appodoctor.activity.ListAppoActivity
import com.example.appodoctor.activity.ManageJadwalActivity
import kotlinx.android.synthetic.main.fragment_home_docter.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeDocter : Fragment() {

    lateinit var pref : AppPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        pref = AppPreferences(context!!)
        pref.setPreferences()
        var id = pref.getUserId()

        Log.d("hahaha",id)

        val view = inflater.inflate(R.layout.fragment_home_docter, container, false)

        view.cvItem.setOnClickListener {
            val i = Intent(context!!, ManageJadwalActivity::class.java)
            startActivity(i)
        }

        view.cvItem2.setOnClickListener {
            val i = Intent(context!!, ListAppoActivity::class.java)
            startActivity(i)
        }
        return view
    }


}
