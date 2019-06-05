package com.example.appodoctor.model

import com.google.gson.annotations.SerializedName

data class JadwalModel (
    @SerializedName("id")
    var idJadwal : String? = null,
    @SerializedName("dokter_id")
    var idDokter : String? = null,
    @SerializedName("poli_id")
    var idPoli : String? = null,
    @SerializedName("nama_dokter")
    var namaDokter : String? = null,
    @SerializedName("tanggal")
    var tgl : String? = null,
    @SerializedName("jam_mulai")
    var jamMulai : String? = null,
    @SerializedName("jam_selesai")
    var jamSelesai : String? = null
)