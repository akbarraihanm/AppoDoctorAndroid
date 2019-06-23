package com.example.appodoctor.view

import com.example.appodoctor.model.Pasien

interface KonfirmasiView {
    fun showLoading()
    fun hideLoading()
    fun showItemPasien(itemPasien : ArrayList<Pasien>)
    fun whenPostAppo()
}