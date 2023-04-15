package com.example.servicesandbrexample.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.servicesandbrexample.R

class ForegroundService: Service() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
        val stackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addNextIntentWithParentStack(launchIntent)
        val pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(this, "jhdfgvj")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setContentTitle("title")
            .setContentText("content text")
            .build()

        startForeground(12345, notification)

        Thread{
            Thread.sleep(3000)
            sendMyBroadcast()
        }.start()
    }

    private fun sendMyBroadcast() {
        val broadcastIntent = Intent()
        broadcastIntent.putExtra(INTENT_SERVICE_DATA, true)
        broadcastIntent.action = INTENT_ACTION_KEY
        sendBroadcast(broadcastIntent)
    }

    private fun createNotificationChannel() {
        val notificationChannel =
        NotificationChannel("jhdfgvj", "Progress", NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    companion object {
        const val INTENT_ACTION_KEY = "com.example.servicesandbrexample.SERVICE_FINISHED_EVENT"
        const val INTENT_SERVICE_DATA = "INTENT_SERVICE_DATA"
        fun start(context: Context){
            val foregroundServiceIntent = Intent(context, ForegroundService::class.java)
            context.startService(foregroundServiceIntent)
        }
        fun stop(context: Context){
            val foregroundServiceIntent = Intent(context, ForegroundService::class.java)
            context.stopService(foregroundServiceIntent)
        }
    }
}