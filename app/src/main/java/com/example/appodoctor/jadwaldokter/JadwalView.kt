package com.example.appodoctor.jadwaldokter

import com.example.appodoctor.model.JadwalModel

interface JadwalView {
    fun showJadwalItem(listJadwal : ArrayList<JadwalModel>)
    fun whenDelete()

}
