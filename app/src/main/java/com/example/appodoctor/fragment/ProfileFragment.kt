package com.example.appodoctor.fragment


import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.example.appodoctor.AppPreferences
import com.example.appodoctor.HomeActivity
import com.example.appodoctor.MainActivity

import com.example.appodoctor.R
import com.example.appodoctor.model.Pasien
import com.example.appodoctor.presenter.ProfilPresenter
import com.example.appodoctor.view.ProfilView
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ProfileFragment : Fragment(), ProfilView {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var pref : AppPreferences
    lateinit var profilPresenter : ProfilPresenter

    lateinit var pbProfil : ProgressBar

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
        var view = inflater.inflate(R.layout.fragment_profile, container, false)
        view.layoutProfil.visibility = INVISIBLE

        pbProfil = view.findViewById(R.id.pbProfil)


        pref = AppPreferences(context!!)
        pref.setPreferences()

        profilPresenter = ProfilPresenter(this, context!!)
        profilPresenter.getProfilData(pref.getUserId())

        Log.d("uid",pref.getUserId())
        view.btKeluar.setOnClickListener {
            showLogoutDialog()
        }

        view.btEditPw.setOnClickListener {
            showAlertDialog()
        }
        return view
    }

    fun showAlertDialog(){
        val fm : FragmentManager = fragmentManager!!
        val alertDialog = EditPwDialogFragment().newInstance("Haha")
        alertDialog.show(fm, "fragment alert")
    }

    fun showLogoutDialog(){
        val fm : FragmentManager = fragmentManager!!
        val alertDialog = LogoutDialogPasien().newInstance("hahaha")
        alertDialog.show(fm, "fragment logout")
    }

    override fun showLoading() {
        pbProfil.visibility = VISIBLE
    }

    override fun hideLoading() {
        pbProfil.visibility = INVISIBLE
    }

    override fun showProfilData(profilData: ArrayList<Pasien>) {
        layoutProfil.visibility = INVISIBLE
        hideLoading()
        layoutProfil.visibility = VISIBLE
        tvNamaPasien.text = profilData[0].namaPasien
        tvAlamat.text = profilData[0].alamatPasien
        tvNoRm.text = profilData[0].normPasien
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
