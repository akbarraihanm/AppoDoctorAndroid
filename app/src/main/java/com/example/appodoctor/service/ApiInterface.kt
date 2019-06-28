package com.example.appodoctor.service

import com.example.appodoctor.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("dokterpoli?id=1")
    fun getDokterByPoli() : Call<DokterResponse>
    @GET("dokterpoli")
    fun getDokterByPoliId(@Query("id") poliId : String?) : Call<DokterResponse>

    @GET("jadwaldokter")
    fun getJadwalByDokterId(@Query("id") dokId : String?) : Call<JadwalResponse>
    @GET("jadwaldokter?id=1")
    fun getJadwalByDokter() : Call<JadwalResponse>

    @GET("poli")
    fun getPoliName() : Call<PoliResponse>

    @GET("pasienid")
    fun getPasienData(@Query("id") pasienId : String?) : Call<PasienResponse>

    @GET("appo/pasien")
    fun getAppoByPasienId(@Query("id") pasienId: String?) : Call<AppoResponse>

    @GET("appo/dokter")
    fun getAppoByDokterId(@Query("id") dokterId : String?) : Call<AppoResponse>

    @GET("appo/id")
    fun getAppoById(@Query("id") appoId : String?) : Call<AppoResponse>

    @POST("appo")
    @FormUrlEncoded
    fun postAppoData(@Field("tanggal") tappo : String?,
                     @Field("pasien_id") pid : String?,
                     @Field("dokter_id") doid : String?,
                     @Field("poli_id") poid : String?) : Call<PostAppo>

    @POST("pasien/login")
    @FormUrlEncoded
    fun postLogin(@Field("norm_pasien") norm : String?,
                  @Field("password") password : String?) : Call<LoginResponse>

    @POST("dokter/login")
    @FormUrlEncoded
    fun postLoginDokter(@Field("notelp") notelp : String?,
                            @Field("password") pw : String?) : Call<LoginResponse>

    @POST("jadwal")
    @FormUrlEncoded
    fun postAddJadwal(@Field("dokter_id") dokterId : String?,
                      @Field("tanggal") tglAdd : String?,
                      @Field("jam_mulai") jamMulai : String?,
                      @Field("jam_selesai") jamSelesai : String?) : Call<JadwalModel>

    @PUT("pasienid")
    fun putPasienPassword(@Query("id") pasienId : String?,
                          @Query("password") pw : String?) : Call<PutPwResponse>
    @DELETE("jadwalid")
    fun deleteJadwal(@Query("id") jadwalId : String?) : Call<PutPwResponse>
}