package com.hc.calling.keeper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by ChanHong on 2019/3/19
 *
 */
class KeepLiveReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent!!.action) {
            Intent.ACTION_SCREEN_OFF -> {

            }

            Intent.ACTION_SCREEN_ON -> {

            }
        }
    }
}