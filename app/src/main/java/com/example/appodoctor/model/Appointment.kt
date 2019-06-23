package com.example.appodoctor.model

import com.google.gson.annotations.SerializedName

data class Appointment (
    @SerializedName("id")
    var idAppo : String? = null,
    @SerializedName("nama_poli")
    var namaPoli : String? = null,
    @SerializedName("nama_dokter")
    var namaDokter : String? = null,
    @SerializedName("tanggal")
    var tgl : String? = null,
    @SerializedName("status_appo")
    var status : String? = null,
    @SerializedName("keterangan")
    var keterangan : String? = null
)