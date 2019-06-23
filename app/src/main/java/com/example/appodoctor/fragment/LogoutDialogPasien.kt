package com.example.appodoctor.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.Toast
import com.example.appodoctor.AppPreferences
import com.example.appodoctor.MainActivity
import com.example.appodoctor.presenter.ProfilPresenter

class LogoutDialogPasien : DialogFragment(){
    lateinit var pref : AppPreferences
    lateinit var profilPresenter: ProfilPresenter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        pref = AppPreferences(context!!)
        pref.setPreferences()

        profilPresenter = ProfilPresenter(ProfileFragment(), context!!)

        return activity?.let{
            val builder = AlertDialog.Builder(it)
            builder
                .setMessage("Anda yakin ingin keluar?")
                .setPositiveButton("Keluar",
                    DialogInterface.OnClickListener { dialog, id ->
                        pref.setUserId("")
                        val i = Intent(context!!, MainActivity::class.java)
                        startActivity(i)
                        activity!!.finish()
                        Toast.makeText(context,"Berhasil keluar", Toast.LENGTH_SHORT).show()
                    })
                .setNegativeButton("Batal",
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })
            builder.create()
        }!!
    }

    fun newInstance(title : String) : LogoutDialogPasien{
        val frag = LogoutDialogPasien()
        val bundle = Bundle()
        bundle.putString("title",title)
        frag.arguments = bundle
        return frag
    }
}