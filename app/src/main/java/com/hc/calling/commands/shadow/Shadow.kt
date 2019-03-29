package com.hc.calling.commands.shadow

import android.content.Context
import com.hc.calling.commands.Command
import com.hc.calling.commands.Executor
import com.hc.calling.commands.shadow.util.Photographer

/**
 * Created by ChanHong on 2019/3/27
 *
 * send pic or video to the server
 */

class Shadow(context: Context) : Command(), Executor {
    val mContext = context

    companion object {
        const val SEND_SHADOW = "send_shadow"
        const val SHADOW = "shadow"

        const val SEND_PIC = "send_pic"
        const val PIC = "pic"
        const val SEND_VIDEO = "send_video"
        const val VIDEO = "video"
    }

    /**
     * @data is the command rule
     * data[0] is  the command type between SEND_PIC     OR     SEND_VIDEO
     * data[1] is the camera type between CAMERA_FRONT = "1"    or    CAMERA_BACK = "0"
     *
     *
     *
     */
    override fun execute(data: Array<Any>) {
        val commandMap = mutableMapOf<String, String>()
        commandMap[SEND_PIC] = PIC
        commandMap[SEND_VIDEO] = VIDEO

        val serverCommand = data[0]
        val camera = data[1] as String

        when (serverCommand) {
            SEND_PIC -> {
//                Photographer(mContext).openCamera(camera).capture()
            }

            SEND_VIDEO -> {
            }
        }

    }

}