package com.example.appodoctor

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        pref = AppPreferences(this)
        pref.setPreferences()


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
