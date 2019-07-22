package com.example.appodoctor.buatjanji

import com.example.appodoctor.model.JadwalModel
import com.example.appodoctor.model.Pasien

interface KonfirmasiView {
    fun showLoading()
    fun hideLoading()
    fun showItemPasien(itemPasien : ArrayList<Pasien>)
    fun showItemJadwal(itemJadwal : ArrayList<JadwalModel>)
    fun whenPostAppo()
}