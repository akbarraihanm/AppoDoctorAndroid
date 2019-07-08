package com.example.appodoctor.activity

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.example.appodoctor.R

class NotificationHelper {

    fun displayNotification(context: Context, title : String, body : String){
        val mBuilder = NotificationCompat.Builder(context, TestNotificationActivity.CHANNEL_ID)
//            .setSmallIcon(R.drawable.ic_android_black_24dp)
            .setSmallIcon(R.drawable.ic_hospital)
//            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_hospital))
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManagerCompat.notify(1,mBuilder.build())
    }
}