package com.example.appodoctor.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.example.appodoctor.R
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_test_notif_doctor.*
import kotlinx.android.synthetic.main.activity_test_notification.*

class TestNotifDoctor : AppCompatActivity() {

    companion object{
        var CHANNEL_ID = "channel id"
        var CHANNEL_NAME = "channel name"
        var CHANNEL_DESC = "channel desc"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_notif_doctor)
        supportActionBar?.title = "Notify"

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = CHANNEL_DESC
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener {
                    task ->
                if(task.isSuccessful){
                    var token = task.result!!.token
                    tvTokenDoc.text = "Token : "+token
                }
                else{
                    tvTokenDoc.text = task.exception!!.message
                }
            }
    }

    private fun displayNotification(){
        val mBuilder = NotificationCompat.Builder(this, TestNotificationActivity.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_android_black_24dp)
            .setContentTitle("Notifikasi Gan")
            .setContentText("Berhasil membuat notifikasi")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManagerCompat = NotificationManagerCompat.from(this)
        notificationManagerCompat.notify(1,mBuilder.build())
    }
}
