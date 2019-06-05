package com.example.appodoctor

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.example.appodoctor.fragment.HomeFragment
import com.example.appodoctor.fragment.ProfileFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNavigation.isClickable=false

        bottomNavigation.setOnNavigationItemSelectedListener { item->
            when(item.itemId){
                R.id.home ->{
                    showLoading()
                    loadHomeFragment(savedInstanceState)
                    hideLoading()
                }
                R.id.profile ->{
                    showLoading()
                    loadProfileFragment(savedInstanceState)
                    hideLoading()
                }
            }
            true
        }
        bottomNavigation.selectedItemId = R.id.home
    }

    private fun showLoading(){
        pbhome.visibility = VISIBLE
    }

    private fun hideLoading(){
        pbhome.visibility = INVISIBLE
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
