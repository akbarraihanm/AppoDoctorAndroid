package com.example.appodoctor.view

import com.example.appodoctor.model.JadwalModel

interface JadwalView {
    fun showLoading()
    fun hideLoading()
    fun showJadwalItem(listJadwal : ArrayList<JadwalModel>)
}