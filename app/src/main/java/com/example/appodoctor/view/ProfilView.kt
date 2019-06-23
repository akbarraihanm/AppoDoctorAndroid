package com.example.appodoctor.view

import com.example.appodoctor.model.Pasien

interface ProfilView{
    fun showLoading()
    fun hideLoading()
    fun showProfilData(profilData : ArrayList<Pasien>)
}