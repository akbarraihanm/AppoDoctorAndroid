package com.example.appodoctor

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.example.appodoctor.activity.TestNotificationActivity
import com.example.appodoctor.fragment.HomeDocter
import com.example.appodoctor.fragment.ProfileDokter
import kotlinx.android.synthetic.main.activity_home_dokter.*

class HomeDokterActivity : AppCompatActivity() {

    lateinit var pref : AppPreferences
//    companion object{
//        var docToken = "token"
//    }

    companion object{
        var CHANNEL_ID = "channel id"
        var CHANNEL_NAME = "channel name"
        var CHANNEL_DESC = "channel desc"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_dokter)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = CHANNEL_DESC
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        pref = AppPreferences(this)
        pref.setPreferences()

        bottomNavigation.isClickable = false

        bottomNavigation.setOnNavigationItemSelectedListener {item ->
            when(item.itemId){
                R.id.home ->{
                    loadHomeDoctorFragment(savedInstanceState)
                    supportActionBar?.title = "Beranda"
                }
                R.id.profile->{
                    loadProfileDokterFragment(savedInstanceState)
                    supportActionBar?.title = "Profil"
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

}
