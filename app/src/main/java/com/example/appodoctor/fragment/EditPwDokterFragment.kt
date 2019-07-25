package com.example.appodoctor.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.Toast
import com.example.appodoctor.AppPreferences
import com.example.appodoctor.R
import com.example.appodoctor.presenter.ProfilPresenter
import kotlinx.android.synthetic.main.dialog_editpw.view.*

class EditPwDokterFragment : DialogFragment(){
    lateinit var profilPresenter : ProfilPresenter
    lateinit var pref : AppPreferences

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog_editpw, null)

            pref = AppPreferences(context!!)
            pref.setPreferences()

            val idDok = pref.getUserId()

            profilPresenter = ProfilPresenter(ProfileDokter(), context!!)

            builder.setView(view)
                .setPositiveButton("Ubah") { dialog, id ->
                    if(view.etPassword.text.isEmpty()){
                        Toast.makeText(context, "Password harus diisi", Toast.LENGTH_SHORT).show()
                    } else{
                        var pwBaru = view.etPassword.text.toString()
                        profilPresenter.updatePwDokter(pref.getUserApiKey(),idDok, pwBaru)
                    }
                }
                .setNegativeButton("Batal") { dialog, id ->
                    dialog.cancel()
                }
            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")
    }

    fun newInstance(title : String) : EditPwDokterFragment{
        val frag = EditPwDokterFragment()
        val bundle = Bundle()
        bundle.putString("title", title)
        frag.arguments = bundle
        return frag
    }
}