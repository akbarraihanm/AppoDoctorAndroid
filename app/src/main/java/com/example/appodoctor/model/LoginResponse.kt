package com.example.appodoctor.model

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("status")
    val status : String? = null,
    @SerializedName("pesan")
    val pesan : String? = null,
    @SerializedName("id_pasien")
    val idPasien : Int? = null,
    @SerializedName("id_dokter")
    var idDokter : Int? = null,
    @SerializedName("user")
    var user : String? = null,
    @SerializedName("nama_dokter")
    var namaDokter : String? = null,
    @SerializedName("api_key")
    var apiKey : String? = null
)