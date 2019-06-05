package com.example.appodoctor.view

import com.example.appodoctor.model.DokterModel

interface DokterView {
    fun showLoading()
    fun hideLoading()
    fun showDokterItem(listDokter : ArrayList<DokterModel>)
}