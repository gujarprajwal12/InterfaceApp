package com.psg.interfaceapp

import android.app.Service
import android.content.*
import android.os.IBinder

class InterfaceService : Service() {

    private var remote: IMessageService? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            remote = IMessageService.Stub.asInterface(binder)
        }
        override fun onServiceDisconnected(name: ComponentName?) {
            remote = null
        }
    }

    override fun onCreate() {
        super.onCreate()
        val intent = Intent().apply {
            setClassName(
                "com.psg.serviceapp",
                "com.psg.serviceapp.RemoteService"
            )
        }
        bindService(intent, connection, BIND_AUTO_CREATE)
    }

    private val binder = object : IMessageService.Stub() {
        override fun sendMessage(encryptedMessage: String?): String {
            return remote?.sendMessage(encryptedMessage ?: "") ?: "Service Not Connected"
        }
    }

    override fun onBind(intent: Intent?): IBinder = binder
}
