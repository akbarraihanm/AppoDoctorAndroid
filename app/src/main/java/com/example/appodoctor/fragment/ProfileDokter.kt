package com.example.appodoctor.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import com.example.appodoctor.AppPreferences
import com.example.appodoctor.LoginDokterActivity

import com.example.appodoctor.R
import com.example.appodoctor.model.DokterModel
import com.example.appodoctor.model.Pasien
import com.example.appodoctor.model.PutPwResponse
import com.example.appodoctor.presenter.ProfilPresenter
import com.example.appodoctor.service.ApiClient
import com.example.appodoctor.service.ApiInterface
import com.example.appodoctor.view.ProfilView
import kotlinx.android.synthetic.main.fragment_profile_dokter.*
import kotlinx.android.synthetic.main.fragment_profile_dokter.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ProfileDokter : Fragment(), ProfilView {

    lateinit var pref : AppPreferences
    lateinit var profilPresenter : ProfilPresenter
    companion object{
        var notelpDokter = "asd"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile_dokter, container, false)
        pref = AppPreferences(context!!)
        pref.setPreferences()

        view.layoutProfil.visibility = INVISIBLE

        profilPresenter = ProfilPresenter(this, context!!)
        profilPresenter.getProfilDokter(pref.getUserId())

        view.btKeluar.setOnClickListener {
            showLogoutDialog()
        }
        view.btEditPw.setOnClickListener {
            showEditPwDialog()
        }

        return view
    }

    private fun showEditPwDialog() {
        val fm : FragmentManager? = fragmentManager
        val alertDialog = EditPwDokterFragment().newInstance("editow")
        alertDialog.show(fm, "fragment edit pw")
    }

    private fun showLogoutDialog(){
        val fm : FragmentManager? = fragmentManager
        val alertDialog = LogoutDialogDokter().newInstance("hehe")
        alertDialog.show(fm, "Fragment Logout")
    }

    override fun showLoading() {
        pbProfil.visibility = VISIBLE
    }

    override fun hideLoading() {
        pbProfil.visibility = INVISIBLE
    }

    override fun showProfilData(profilData: ArrayList<Pasien>) {

    }

    override fun showProfilDokter(profilDokter: ArrayList<DokterModel>) {
        notelpDokter = profilDokter[0].noTelp!!
        layoutProfil.visibility = VISIBLE
        tvNamaDokter.text = profilDokter[0].namaDokter
        tvNoTelp.text = profilDokter[0].noTelp
        tvNamaPoli.text = profilDokter[0].namaPoli
        hideLoading()
    }

}
