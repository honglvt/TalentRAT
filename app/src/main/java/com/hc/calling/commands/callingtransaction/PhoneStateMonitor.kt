package com.hc.calling.commands.callingtransaction

import android.content.Context
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log

/**
 * Created by ChanHong on 2019/3/21
 *
 */

class PhoneStateMonitor(context: Context) {
    var telephonyManager: TelephonyManager? = null
    var context: Context? = null

    init {
        this.context = context
        registerPhoneListener()
    }

    private fun registerPhoneListener() {
        telephonyManager = context!!.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        telephonyManager!!.listen(MyPhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE)
    }


    inner class MyPhoneStateListener : PhoneStateListener() {
        override fun onCallStateChanged(state: Int, phoneNumber: String?) {
            super.onCallStateChanged(state, phoneNumber)
            when (state) {
                TelephonyManager.CALL_STATE_IDLE -> {
                    Log.i("CALL_STATE_IDLE", "-------------->CALL_STATE_IDLE")

                }

                TelephonyManager.CALL_STATE_OFFHOOK -> {
                    Log.i("CALL_STATE_OFFHOOK", "CALL_STATE_OFFHOOK")
                }

                TelephonyManager.CALL_STATE_RINGING -> {
                }
            }

        }
    }
}