package com.hc.calling.keeper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hc.calling.commands.gps.GPS
import com.hc.calling.socket.SocketConductor

/**
 * Created by ChanHong on 2019/3/19
 *
 */
class KeepLiveReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent!!.action) {
            Intent.ACTION_SCREEN_OFF,
            Intent.ACTION_SCREEN_ON -> {
                GPS.getGPS(context!!) {}
                if (!SocketConductor.instance.socket!!.connected()) {
                    SocketConductor.instance.socket!!.connect()
                }

            }
        }
    }
}