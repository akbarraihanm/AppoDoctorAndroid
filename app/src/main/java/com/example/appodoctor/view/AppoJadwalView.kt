package com.example.appodoctor.view

import com.example.appodoctor.model.Appointment

interface AppoJadwalView {
    fun showLoading()
    fun hideLoading()
    fun showItem(listAppo : ArrayList<Appointment>)
}
