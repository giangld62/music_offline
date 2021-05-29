package com.unica.musicplayer.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class MyApplication : Application() {
    companion object{
        var CHANNEL_ID = "channel_service"
    }
    override fun onCreate() {
        super.onCreate()
        createChannelNotification()
    }

    private fun createChannelNotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID,"Music",NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }
}