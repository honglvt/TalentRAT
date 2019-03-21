package com.hc.calling.callingtransaction

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import com.hc.calling.callingtransaction.util.CallingHistory
import com.hc.calling.callingtransaction.util.ContactUtil
import com.hc.calling.callingtransaction.util.DateUtil
import com.hc.calling.callingtransaction.util.SmsSender

/**
 * Created by ChanHong on 2019/3/18
 *
 */
class MainService : Service() {
    var telephonyManager: TelephonyManager? = null

    override fun onBind(intent: Intent?): IBinder {
        return MyService()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        initPhoneListener()
        registerCallBack(SmsSender.SMS_SEND_ACTION)
        registerCallBack(SmsSender.SMS_DELIVER_ACTION)

        return super.onStartCommand(intent, flags, startId)
    }

    inner class MyService : Binder()

    private fun registerCallBack(action: String) {
        this.registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
            }
        }, IntentFilter(action))
    }

    private fun initPhoneListener() {
        telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        telephonyManager!!.listen(MyPhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE)
    }

    inner class MyPhoneStateListener : PhoneStateListener() {
        override fun onCallStateChanged(state: Int, phoneNumber: String?) {
            super.onCallStateChanged(state, phoneNumber)
            var msg = StringBuilder()
            when (state) {
                TelephonyManager.CALL_STATE_IDLE -> {
                    Log.i("CALL_STATE_IDLE", "-------------->CALL_STATE_IDLE")

                }

                TelephonyManager.CALL_STATE_OFFHOOK -> {
                    Log.i("CALL_STATE_OFFHOOK", "CALL_STATE_OFFHOOK")
                }

                TelephonyManager.CALL_STATE_RINGING -> {
                    msg.append("$phoneNumber")
                    ContactUtil.getContact(this@MainService).forEach {
                        it.forEach {
                            if (it.value.replace(" ", "").equals(phoneNumber)) {
                                msg.append(" " + it.key)
                            }
                        }
                    }
                    msg.append("\n")
                    msg.append("had called u")
                    msg.append("\n")
                    msg.append(DateUtil.GetNowDate("@" + "yyyy:MM:dd:HH:mm:ss"))
                    SmsSender.sendSms(this@MainService, msg.toString())
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}