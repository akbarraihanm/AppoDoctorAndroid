package com.example.appodoctor.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.EditText
import android.widget.Toast
import com.example.appodoctor.AppPreferences
import com.example.appodoctor.R
import com.example.appodoctor.model.Pasien
import com.example.appodoctor.presenter.ProfilPresenter
import com.example.appodoctor.view.ProfilView
import kotlinx.android.synthetic.main.dialog_editpw.*
import kotlinx.android.synthetic.main.dialog_editpw.view.*
import kotlinx.android.synthetic.main.fragment_profile.*

class EditPwDialogFragment : DialogFragment(){

    lateinit var profilPresenter : ProfilPresenter
    lateinit var pref : AppPreferences

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater

            val view = inflater.inflate(R.layout.dialog_editpw, null)

            pref = AppPreferences(context!!)
            pref.setPreferences()

            var idPas = pref.getUserId()
            Log.d("idPas", idPas)

            profilPresenter = ProfilPresenter(ProfileFragment(), context!!)

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(view)
                // Add action buttons
                .setPositiveButton("Ubah",
                    DialogInterface.OnClickListener { dialog, id ->
                        // sign in the user ...
                        if(view.etPassword.text.isEmpty())
                            Toast.makeText(context, "Password harus diisi", Toast.LENGTH_SHORT).show()
                        else{
                            var pwBaru = view.etPassword.text.toString()
                            Log.d("idPas",pwBaru)
                            profilPresenter.updatePassword(idPas,pwBaru)
                        }
                    })
                .setNegativeButton("Batal",
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun newInstance(title : String) : EditPwDialogFragment{
        val frag = EditPwDialogFragment()
        val bundle = Bundle()
        bundle.putString("title", title)
        frag.arguments = bundle
        return frag
    }

}