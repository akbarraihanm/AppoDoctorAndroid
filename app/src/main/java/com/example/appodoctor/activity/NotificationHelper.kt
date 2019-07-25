package com.example.appodoctor.activity

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.widget.Toast
import com.example.appodoctor.AppPreferences
import com.example.appodoctor.HomeActivity
import com.example.appodoctor.HomeDokterActivity
import com.example.appodoctor.R
import com.example.appodoctor.cekstatus.CekStatusActivity
import com.example.appodoctor.konfirmasijanji.AppoPasienActivity
import com.example.appodoctor.model.AppoResponse
import com.example.appodoctor.service.ApiClient
import com.example.appodoctor.service.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationHelper {
    lateinit var pref : AppPreferences

    var id_appo : String? = null

    fun displayNotification(context: Context, title : String, body : String){

        pref = AppPreferences(context)
        pref.setPreferences()
        if(pref.getUserLogin() == "dokter") {
            val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
            val call = apiInterface.getAppoByDokterId(pref.getUserApiKey(),pref.getUserId())
            call.enqueue(object : Callback<AppoResponse> {
                override fun onFailure(call: Call<AppoResponse>, t: Throwable) {
                    Toast.makeText(context, "" + t, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<AppoResponse>, response: Response<AppoResponse>) {
                    var listId = response.body()!!.data
                    try {
                        id_appo = listId[0].idAppo.toString()
                        val intent = Intent(context, AppoPasienActivity::class.java)
                        intent.putExtra(AppoPasienActivity.id_appo, id_appo)
                        val pendingIntent = PendingIntent.getActivity(context,1, intent, PendingIntent.FLAG_CANCEL_CURRENT)

                        val mBuilder = NotificationCompat.Builder(context, HomeDokterActivity.CHANNEL_ID)
//            .setSmallIcon(R.drawable.ic_android_black_24dp)
                            .setSmallIcon(R.drawable.ic_hospital)
//            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_hospital))
                            .setContentTitle(title)
                            .setContentText(body)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)

                        val notificationManagerCompat = NotificationManagerCompat.from(context)
                        notificationManagerCompat.notify(1,mBuilder.build())
                    } catch (e: Exception) {
                    }
                }

            })
        }
        else{
            val intent = Intent(context, CekStatusActivity::class.java)
            val penIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT)

            val mBuilder = NotificationCompat.Builder(context, HomeActivity.CHANNEL_ID)
//            .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setSmallIcon(R.drawable.ic_hospital)
//            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_hospital))
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(penIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            val notificationManagerCompat = NotificationManagerCompat.from(context)
            notificationManagerCompat.notify(1,mBuilder.build())
        }

//        getIdAppo(context)
    }

//    fun getIdAppo(context : Context){
//        pref = AppPreferences(context)
//        pref.setPreferences()
//        if(pref.getUserLogin() == "dokter") {
//            val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
//            val call = apiInterface.getAppoByDokterId(pref.getUserId())
//            call.enqueue(object : Callback<AppoResponse> {
//                override fun onFailure(call: Call<AppoResponse>, t: Throwable) {
//                    Toast.makeText(context, "" + t, Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onResponse(call: Call<AppoResponse>, response: Response<AppoResponse>) {
//                    var listId = response.body()!!.data
//                    try {
//                        id_appo = listId[0].idAppo.toString()
//                    } catch (e: Exception) {
//                    }
//                }
//
//            })
//        }
//    }
}