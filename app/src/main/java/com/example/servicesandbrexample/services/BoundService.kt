package com.example.servicesandbrexample.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class BoundService : Service() {
    private val iBinder: IBinder = ServiceBinder()
    private var f1: Long = 0
    private var f2: Long = 1

    override fun onBind(intent: Intent?): IBinder = iBinder

    private val nextF: Long
        get() {
            val result = f1 + f2
            f1 = f2
            f2 = result
            return result
        }

    internal inner class ServiceBinder : Binder() {
        private val service: BoundService
            get() = this@BoundService
        val nextF: Long
            get() = service.nextF
    }
}