package com.example.appodoctor.view

import com.example.appodoctor.model.Appointment

interface ListAppoView {
    fun showListAppo(listAppo : ArrayList<Appointment>)
    fun showLoading()
    fun hideLoading()
}