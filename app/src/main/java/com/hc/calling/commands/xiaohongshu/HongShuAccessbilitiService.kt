package com.hc.calling.commands.xiaohongshu

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Path
import android.graphics.Rect
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Toast
import com.orhanobut.logger.Logger
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay


/**
 * Created by ChanHong on 2019/4/23
 *
 */
class HongShuAccessbilitiService : AccessibilityService() {
    var deeping = 0
    @Volatile
    var send = false
    var clickZan = false
    var clickMine = false
    var clickSetting = false
    var clickOut = false
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Logger.i("onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onInterrupt() {

    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Logger.i(event!!.eventType.toString() + "\n" + event.className)
        val node = event!!.source ?: return
        val nodeInfo = node.className.toString()
        Logger.i(nodeInfo)

//        //点赞
//        if (event.className != null && event.className.toString() == "com.xingin.xhs.index.IndexNewActivity") {
//            val zan = node.findAccessibilityNodeInfosByViewId("com.xingin.xhs:id/a7z")
//            click(zan.first())
//            click(zan[1])
//            clickZan = true
//        }

        //点赞结束切换账号
        val mine = node.findAccessibilityNodeInfosByViewId("com.xingin.xhs:id/a3o")
        if (mine.isNotEmpty()) {
            click(mine.first())
            clickMine = true
        }
//
//        //点击设置
//        if (clickMine) {
//            val setting = node.findAccessibilityNodeInfosByViewId("com.xingin.xhs:id/a2w")
//            click(setting.first())
//            clickSetting = true
//        }
        //登出账户
        if (event.className != null && event.className.toString() == "com.xingin.xhs.activity.SettingActivity") {
            val setting = node.findAccessibilityNodeInfosByViewId("com.xingin.xhs:id/bcf")
            if (setting.isNotEmpty()) {
                val loginOut = setting.first().getChild(13)
                Logger.i(loginOut.toString())
                click(loginOut)
                Toast.makeText(this, "点击了登出账户", Toast.LENGTH_SHORT).show()

            }
        }

        //确认登出
        confirmLoginOut(node)

    }

    private fun confirmLoginOut(nodeInfo: AccessibilityNodeInfo) {
        val cancel = nodeInfo.findAccessibilityNodeInfosByViewId("android:id/button2")
        if (cancel.isNotEmpty()) {
            click(cancel.first())
            Toast.makeText(this, "点击了取消退出", Toast.LENGTH_SHORT).show()
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

    private fun findByText(nodeInfo: AccessibilityNodeInfo, text: String) {
        for (i in 0 until nodeInfo.childCount) {
            val child = nodeInfo.getChild(i)
            Logger.i("child:$child")
            if (child.className.toString() == "android.widget.TextView" && child.text != null && child.text.toString() == text) {
                Logger.i(child.toString())
                break
            } else {
                findByText(child, text)
            }
        }
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        Logger.i("onServiceConnected")
        GlobalScope.async {
            delay(2000)
            jump2HongShu(this@HongShuAccessbilitiService)
        }
    }

    private fun jump2HongShu(context: Context) {
        context.startActivity(
            context
                .packageManager
                .getLaunchIntentForPackage("com.xingin.xhs")
        )
    }

}