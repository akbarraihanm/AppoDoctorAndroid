package com.example.appodoctor

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.example.appodoctor.fragment.HomeFragment
import com.example.appodoctor.fragment.ProfileFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    lateinit var pref : AppPreferences
//    companion object{
//        var GET_TOKEN  = "generated"
//    }

    companion object{
        var CHANNEL_ID = "channel id"
        var CHANNEL_NAME = "channel name"
        var CHANNEL_DESC = "channel desc"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = CHANNEL_DESC
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        pref = AppPreferences(this)
        pref.setPreferences()

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


        bottomNavigation.isClickable=false

        bottomNavigation.setOnNavigationItemSelectedListener { item->
            when(item.itemId){
                R.id.home ->{
                    loadHomeFragment(savedInstanceState)
                    supportActionBar?.title="Beranda"
                }
                R.id.profile ->{
                    loadProfileFragment(savedInstanceState)
                    supportActionBar?.title="Profil"
                }
            }
            true
        }
        bottomNavigation.selectedItemId = R.id.home
    }


    private fun loadHomeFragment(savedInstanceState: Bundle?){
        if(savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.flHome, HomeFragment(), HomeFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadProfileFragment(savedInstanceState: Bundle?){
        if(savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.flHome, ProfileFragment(), ProfileFragment::class.java.simpleName)
                .commit()
        }
    }

}
