package com.example.appodoctor.view

import com.example.appodoctor.model.JadwalModel

interface BuatJanjiView {
    fun showLoading()
    fun hideLoading()
    fun showPoliItem(polName : ArrayList<String>, polId : ArrayList<String>)
    fun showDokterItem(dokName : ArrayList<String>, dokId : ArrayList<String>)
    fun showJadwalItem(listJadwal: ArrayList<JadwalModel>)
}