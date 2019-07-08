package com.example.appodoctor.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.bumptech.glide.Priority
import com.example.appodoctor.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import kotlinx.android.synthetic.main.activity_test_notification.*

class TestNotificationActivity : AppCompatActivity() {

    companion object{
        var CHANNEL_ID = "channel id"
        var CHANNEL_NAME = "channel name"
        var CHANNEL_DESC = "channel desc"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_notification)
        supportActionBar?.title = "Notify"

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = CHANNEL_DESC
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

//        btNotif.setOnClickListener {
//            displayNotification()
//        }

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener {
                task ->
                if(task.isSuccessful){
                    var token = task.result!!.token
                    tvToken.text = "Token : "+token
                }
                else{
                    tvToken.text = task.exception!!.message
                }
            }
    }

    private fun displayNotification(){
        val mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_android_black_24dp)
            .setContentTitle("Notifikasi Gan")
            .setContentText("Berhasil membuat notifikasi")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManagerCompat = NotificationManagerCompat.from(this)
        notificationManagerCompat.notify(1,mBuilder.build())
    }
}
