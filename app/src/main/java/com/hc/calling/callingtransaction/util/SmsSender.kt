package com.hc.calling.callingtransaction.util

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager

/**
 * Created by ChanHong on 2019/3/18
 *
 */
class SmsSender(context: Context) {
    companion object {
        const val SMS_SEND_ACTION = "SMS_SEND_ACTION"
        const val SMS_DELIVER_ACTION = "SMS_DELIVER_ACTION"
        val sentIntent = Intent(SMS_SEND_ACTION)
        val deliveryIntent = Intent(SMS_DELIVER_ACTION)
        fun sendSms(context: Context, msg: String) {
            val sentPIntent = PendingIntent.getBroadcast(
                context, 0,
                sentIntent, 0
            )
            val deliveryPItent = PendingIntent.getBroadcast(
                context, 0,
                deliveryIntent, 0
            )
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage("13157132327", null, msg, sentPIntent, deliveryPItent)
        }
    }
}