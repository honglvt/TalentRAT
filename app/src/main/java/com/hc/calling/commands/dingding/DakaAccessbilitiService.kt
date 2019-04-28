package com.hc.calling.commands.dingding

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import com.orhanobut.logger.Logger

/**
 * Created by ChanHong on 2019/4/23
 *
 */
class DakaAccessibilityService : AccessibilityService() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Logger.i("onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onInterrupt() {

    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Logger.i(event.toString())

    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        Logger.i("onServiceConnected")
    }


}