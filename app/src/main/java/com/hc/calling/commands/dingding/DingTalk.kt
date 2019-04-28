package com.hc.calling.commands.dingding

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.os.SystemClock
import com.hc.calling.commands.Executor
import com.hc.calling.util.DateUtil
import com.orhanobut.logger.Logger
import java.util.*

/**
 * Created by ChanHong on 2019/4/23
 *
 */

class DingTalk : Executor {
    override fun execute(data: Array<Any>) {
    }

    companion object {

        @SuppressLint("NewApi")
        fun initAlarmManager(context: Context) {
            val now = Calendar.getInstance().get(Calendar.HOUR) >= 8

            Logger.i("is morning: $now")
            val duration = DateUtil.getNextEveningDuration(now) //距离打卡时间还剩多久
            val triggerAtTime = SystemClock.elapsedRealtime() + duration //下次打卡具体时间
            val intent = Intent(context, AlarmReciver::class.java)
            val pi = PendingIntent.getBroadcast(context, 0, intent, 0)

            (context.getSystemService(Context.ALARM_SERVICE) as AlarmManager).apply {
                setAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi)
            }

        }

    }


    class AlarmReciver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Logger.i(" receive alarm successfully")
            wakeUpScreen(context!!)
            jump2DingDing(context)
            initAlarmManager(context)
        }

        @SuppressLint("InvalidWakeLockTag", "WakelockTimeout")
        private fun wakeUpScreen(context: Context) {
            (context.getSystemService(Context.POWER_SERVICE) as PowerManager)
                .newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "TAG_a2dn")
                .acquire()
        }


        private fun jump2DingDing(context: Context) {
            context.startActivity(
                context
                    .packageManager
                    .getLaunchIntentForPackage("com.alibaba.android.rimet")
            )
        }
    }


}