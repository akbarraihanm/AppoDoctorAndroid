package com.example.appodoctor

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class AppPreferences (private val context: Context){
    private lateinit var pref : SharedPreferences

    fun setPreferences(){
        pref = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun setUserLogin(stat : String){
        val editor = this.pref.edit()
        val key = "user_stat_key"
        editor.putString(key, stat)
        editor.apply()
    }

    fun getUserLogin() : String{
        val key = "user_stat_key"
        return pref.getString(key, "")
    }

    fun setNameUser(name : String){
        val editor = this.pref.edit()
        val key = "user_name_key"
        editor.putString(key,name)
        editor.apply()
    }

    fun getNameUser() : String{
        val key = "user_name_key"
        return pref.getString(key,"")
    }

    fun setUserId(input : String){
        val editor = this.pref.edit()
        val key = "user_id_key"
        editor.putString(key,input)
        editor.apply()
    }

    fun getUserId() : String{
        val key = "user_id_key"
        return pref.getString(key, "")
    }
}