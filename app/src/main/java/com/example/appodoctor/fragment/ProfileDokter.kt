package com.example.appodoctor.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appodoctor.AppPreferences
import com.example.appodoctor.LoginDokterActivity

import com.example.appodoctor.R
import kotlinx.android.synthetic.main.fragment_profile_dokter.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ProfileDokter : Fragment() {

    lateinit var pref : AppPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile_dokter, container, false)
        pref = AppPreferences(context!!)
        pref.setPreferences()

        view.btKeluar.setOnClickListener {

            pref.setUserId("")

            val i = Intent(context!!, LoginDokterActivity::class.java)
            startActivity(i)
            activity!!.finish()
        }

        return view
    }


}
