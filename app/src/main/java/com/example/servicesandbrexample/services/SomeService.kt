package com.example.servicesandbrexample.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder

class SomeService: Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //...поток UI
        Thread{
            //...
        }.start()

        /*
        START_NOT_STICKY – сервис не будет перезапущен после того, как был убит системой
        START_STICKY – сервис будет перезапущен после того, как был убит системой
        START_REDELIVER_INTENT – сервис будет перезапущен после того, как был убит системой.
        Кроме этого, сервис снова получит все вызовы startService, которые не были завершены методом stopSelf(startId).*/
        return START_NOT_STICKY
    }
    override fun onBind(intent: Intent?): IBinder? = null

    companion object {
        fun start(context: Context){
            val someServiceIntent = Intent(context, SomeService::class.java)
            context.startService(someServiceIntent)
        }
        fun stop(context: Context){
            val someServiceIntent = Intent(context, SomeService::class.java)
            context.stopService(someServiceIntent)
        }
    }
}