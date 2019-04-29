package com.hc.calling.commands.xiaoenai

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Path
import android.graphics.Rect
import android.os.Bundle
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.orhanobut.logger.Logger
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * Created by ChanHong on 2019/4/23
 *
 */
class EnaiAccessbilitiService : AccessibilityService() {
    var deeping = 0
    @Volatile
    var send = false

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Logger.i("onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onInterrupt() {

    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Logger.i(event!!.eventType.toString() + "\n" + event.className)
        val node = event!!.source ?: return
        GlobalScope.launch {
            goLover(node, event)
        }
    }
    /**
     * find
     */

    /**
     * find Bottom Tab
     */

    @Synchronized
    private suspend fun goLover(parent: AccessibilityNodeInfo, event: AccessibilityEvent) {
        if (event.className != null && event.className.toString() == "com.xiaoenai.app.presentation.home.view.activity.HomeActivity") {
            val tvSweeter = parent.findAccessibilityNodeInfosByText("蜜语")
            if (tvSweeter.isEmpty()) return
            Logger.i("tv size is :" + tvSweeter.size.toString())
            click(tvSweeter.first())
            Logger.i(event.toString())
            parent.recycle()
        }
        if (event.className == "com.xiaoenai.app.classes.chat.ChatActivity") {
            // 第一次进入蜜语界面 输入 "我爱你"
            foreachParent(parent)
        }
     }

    private suspend fun foreachParent(node: AccessibilityNodeInfo) {
        for (i in 0 until node.childCount) {
            Logger.i(node.getChild(i).className.toString())
            if (node.getChild(i).className.toString() == "android.widget.EditText") {
                val arguments = Bundle()
                arguments.putCharSequence(
                    AccessibilityNodeInfo
                        .ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "我爱你"
                )
                node.getChild(i)!!.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)
                performGlobalAction(GLOBAL_ACTION_BACK)
                node.getChild(i).recycle()
                break
            }
            foreachParent(
                node.getChild(i)
            )
        }
    }

    /**
     * click event
     */

    @SuppressLint("NewApi")
    private fun click(view: AccessibilityNodeInfo) {
        val rect = Rect()
        view.getBoundsInScreen(rect)
        val path = Path()
        path.moveTo(rect.exactCenterX(), rect.exactCenterY())
        val gustrue =
            GestureDescription.Builder()
                .addStroke(GestureDescription.StrokeDescription(path, 100, 50)).build()
        dispatchGesture(gustrue, object : AccessibilityService.GestureResultCallback() {
            override fun onCancelled(gestureDescription: GestureDescription?) {
                super.onCancelled(gestureDescription)
                Logger.i("onCancelled")
            }

            override fun onCompleted(gestureDescription: GestureDescription?) {
                super.onCompleted(gestureDescription)
                Logger.i("onCompleted")

            }
        }, null)
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        Logger.i("onServiceConnected")
    }


}