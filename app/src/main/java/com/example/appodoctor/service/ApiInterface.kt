package com.example.appodoctor.service

import com.example.appodoctor.model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("dokterpoli?id=1")
    fun getDokterByPoli() : Call<DokterResponse>
    @GET("dokterpoli")
    fun getDokterByPoliId(@Query("id") poliId : String?) : Call<DokterResponse>
    @GET("dokterid")
    fun getDokterById(@Query("id") idDok : String?) : Call<DokterResponse>

    @GET("jadwaldokter")
    fun getJadwalByDokterId(@Query("id") dokId : String?) : Call<JadwalResponse>
    @GET("jadwaldokter?id=1")
    fun getJadwalByDokter() : Call<JadwalResponse>
    @GET("jadwal/tgl")
    fun getJadwalByTgl(@Query("tanggal") tgl : String?,
                       @Query("dokter_id") dokId : String?) : Call<JadwalResponse>
    @GET("jadwalid")
    fun getJadwalById(@Query("id") idJadwal : String?) : Call<JadwalResponse>

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

    @GET("appo/jadwal")
    fun getAppoByJadwalId(@Query("jadwal_id") jadId : String?) : Call<AppoResponse>

    @GET("appo/status")
    fun getAppoByStatus(@Query("status_appo") statAppo : String?,
                        @Query("dokter_id") idDokt : String?) : Call<AppoResponse>

    @POST("appo")
    @FormUrlEncoded
    fun postAppoData(@Field("jadwal_id") tappo : String?,
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

    @POST("send")
    @FormUrlEncoded
    fun pushNotification(@Field("token") token : String?,
                         @Field("title") title : String?,
                         @Field("body") body : String?) : Call<ResponseBody>

    @PUT("pasienid")
    fun putPasienPassword(@Query("id") pasienId : String?,
                          @Query("password") pw : String?) : Call<PutPwResponse>
    @PUT("pasien/token")
    fun putPasienToken(@Query("norm_pasien") normPas : String?,
                       @Query("token_notif") token : String?) : Call<PutPwResponse>
    @PUT("dokter/token")
    fun putDokterToken(@Query("notelp") notelpDok : String?,
                       @Query("token_notif") token : String?) : Call<PutPwResponse>
    @PUT("appo/update")
    fun putStatusAppo(@Query("id") appoId : String?,
                      @Query("status_appo") status : String?,
                      @Query("keterangan") ket : String?) : Call<PutPwResponse>
    @DELETE("jadwalid")
    fun deleteJadwal(@Query("id") jadwalId : String?) : Call<PutPwResponse>

    @DELETE("appo/delete")
    fun deleteAppo(@Query("tanggal") tgl : String?) : Call<PutPwResponse>
}