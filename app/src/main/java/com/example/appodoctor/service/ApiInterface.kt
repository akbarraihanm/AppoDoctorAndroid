package com.example.appodoctor.service

import com.example.appodoctor.model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("{api_key}/dokterpoli?id=1")
    fun getDokterByPoli(@Path("api_key") apiKey : String?) : Call<DokterResponse>
    @GET("{api_key}/dokterpoli")
    fun getDokterByPoliId(@Path("api_key") apiKey: String?,
                          @Query("id") poliId : String?) : Call<DokterResponse>
    @GET("{api_key}/dokterid")
    fun getDokterById(@Path("api_key") apiKey: String?,
                      @Query("id") idDok : String?) : Call<DokterResponse>

    @GET("{api_key}/jadwaldokter")
    fun getJadwalByDokterId(@Path("api_key") apiKey: String?,
                            @Query("id") dokId : String?) : Call<JadwalResponse>
    @GET("{api_key}/jadwaldokter?id=1")
    fun getJadwalByDokter(@Path("api_key") apiKey: String?) : Call<JadwalResponse>
    @GET("jadwal/tgl")
    fun getJadwalByTgl(@Query("tanggal") tgl : String?,
                       @Query("dokter_id") dokId : String?) : Call<JadwalResponse>
    @GET("jadwalid")
    fun getJadwalById(@Query("id") idJadwal : String?) : Call<JadwalResponse>

    @GET("{api_key}/poli")
    fun getPoliName(@Path("api_key") apiKey: String?) : Call<PoliResponse>

    @GET("{api_key}/pasienid")
    fun getPasienData(@Path("api_key") apiKey : String?,
                      @Query("id") pasienId : String?) : Call<PasienResponse>

    @GET("{api_key}/appo/pasien")
    fun getAppoByPasienId(@Path("api_key") apiKey: String?,
                          @Query("id") pasienId: String?) : Call<AppoResponse>

    @GET("{api_key}/appo/dokter")
    fun getAppoByDokterId(@Path("api_key") apiKey: String?,
                          @Query("id") dokterId : String?) : Call<AppoResponse>

    @GET("{api_key}/appo/id")
    fun getAppoById(@Path("api_key") apiKey: String?,
                    @Query("id") appoId : String?) : Call<AppoResponse>

    @GET("{api_key}/appo/jadwal")
    fun getAppoByJadwalId(@Path("api_key") apiKey: String?,
                          @Query("jadwal_id") jadId : String?) : Call<AppoResponse>

    @GET("{api_key}/appo/status")
    fun getAppoByStatus(@Path("api_key") apiKey: String?,
                        @Query("status_appo") statAppo : String?,
                        @Query("dokter_id") idDokt : String?) : Call<AppoResponse>

    @POST("{api_key}/appo")
    @FormUrlEncoded
    fun postAppoData(@Path("api_key") apiKey: String?,
                     @Field("jadwal_id") tappo : String?,
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

    @POST("{api_key}/jadwal")
    @FormUrlEncoded
    fun postAddJadwal(@Path("api_key") apiKey: String?,
                      @Field("dokter_id") dokterId : String?,
                      @Field("tanggal") tglAdd : String?,
                      @Field("jam_mulai") jamMulai : String?,
                      @Field("jam_selesai") jamSelesai : String?) : Call<JadwalModel>

    @POST("send")
    @FormUrlEncoded
    fun pushNotification(@Field("token") token : String?,
                         @Field("title") title : String?,
                         @Field("body") body : String?) : Call<ResponseBody>

    @PUT("{api_key}/pasienid")
    fun putPasienPassword(@Path("api_key") apiKey: String?,
                          @Query("id") pasienId : String?,
                          @Query("password") pw : String?) : Call<PutPwResponse>
    @PUT("{api_key}/dokterid")
    fun putDokterPassword(@Path("api_key") apiKey: String?,
                          @Query("id") dokId : String?,
                          @Query("password") pw : String?) : Call<PutPwResponse>
    @PUT("{api_key}/pasien/token")
    fun putPasienToken(@Path("api_key") apiKey: String?,
                       @Query("norm_pasien") normPas : String?,
                       @Query("token_notif") token : String?) : Call<PutPwResponse>
    @PUT("{api_key}/dokter/token")
    fun putDokterToken(@Path("api_key") apiKey: String?,
                       @Query("notelp") notelpDok : String?,
                       @Query("token_notif") token : String?) : Call<PutPwResponse>
    @PUT("{api_key}/appo/update")
    fun putStatusAppo(@Path("api_key") apiKey: String?,
                      @Query("id") appoId : String?,
                      @Query("status_appo") status : String?,
                      @Query("keterangan") ket : String?) : Call<PutPwResponse>
    @DELETE("{api_key}/jadwalid")
    fun deleteJadwal(@Path("api_key") apiKey: String?,
                     @Query("id") jadwalId : String?) : Call<PutPwResponse>

    @DELETE("{api_key}/appo/delete")
    fun deleteAppo(@Path("api_key") apiKey: String?,
                   @Query("tanggal") tgl : String?) : Call<PutPwResponse>
}