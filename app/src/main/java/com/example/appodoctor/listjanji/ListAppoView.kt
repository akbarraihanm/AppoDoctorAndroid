package com.example.appodoctor.listjanji

import com.example.appodoctor.model.Appointment
import com.example.appodoctor.model.JadwalModel

interface ListAppoView {
//    fun showListAppo(listAppo : ArrayList<Appointment>)
    fun showListAppo(listJadwal : ArrayList<JadwalModel>)
    fun showLoading()
    fun hideLoading()
}