package com.example.appodoctor

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import com.example.appodoctor.fragment.HomeDocter
import com.example.appodoctor.fragment.ProfileDokter
import kotlinx.android.synthetic.main.activity_home_dokter.*

class HomeDokterActivity : AppCompatActivity() {

    lateinit var pref : AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_dokter)

        pref = AppPreferences(this)
        pref.setPreferences()

        bottomNavigation.isClickable = false

        bottomNavigation.setOnNavigationItemSelectedListener {item ->
            when(item.itemId){
                R.id.home ->{
                    loadHomeDoctorFragment(savedInstanceState)
                    hideLoading()
                }
                R.id.profile->{
                    loadProfileDokterFragment(savedInstanceState)
                    hideLoading()
                }
            }
            true
        }
        bottomNavigation.selectedItemId = R.id.home
    }

    private fun loadHomeDoctorFragment(savedInstanceState: Bundle?){
        if(savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.flHomdeDoc, HomeDocter(), HomeDocter::class.java.simpleName)
                .commit()
        }
    }

    private fun loadProfileDokterFragment(savedInstanceState: Bundle?){
        if(savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.flHomdeDoc, ProfileDokter(), ProfileDokter::class.java.simpleName)
                .commit()
        }
    }

    private fun showLoading(){
        pbhome.visibility = VISIBLE
    }

    private fun hideLoading(){
        pbhome.visibility = INVISIBLE
    }
}
