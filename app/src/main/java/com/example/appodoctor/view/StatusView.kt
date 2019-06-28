package com.example.appodoctor.view

import com.example.appodoctor.model.Appointment

interface StatusView {
    fun showLoading()
    fun hideLoading()
    fun showStatusItem(listStatus : ArrayList<Appointment>)
    fun showStatusId(objItem : ArrayList<Appointment>)
}