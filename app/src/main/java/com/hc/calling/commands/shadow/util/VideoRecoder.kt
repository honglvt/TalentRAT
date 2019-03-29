package com.hc.calling.commands.shadow.util

import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.media.CamcorderProfile
import android.media.MediaRecorder
import android.os.Handler
import android.view.Surface
import com.orhanobut.logger.Logger

/**
 * Created by ChanHong on 2019/3/28
 *
 */
class VideoRecoder {


    private val mMediaRecorder = MediaRecorder()


    fun initMediaRecorde(outputFilePath: String): VideoRecoder {
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE)
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mMediaRecorder.setVideoEncodingBitRate(5*1024*1024)
        //每秒30帧
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264)
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mMediaRecorder.setVideoFrameRate(15)
        val prifile = CamcorderProfile.get(CamcorderProfile.QUALITY_1080P)
        mMediaRecorder.setVideoSize(prifile.videoFrameWidth, prifile.videoFrameHeight)
        mMediaRecorder.setOutputFile(outputFilePath)

        mMediaRecorder.setOrientationHint(90)
        mMediaRecorder.prepare()

        mMediaRecorder.setOnInfoListener(object : MediaRecorder.OnInfoListener {
            override fun onInfo(mr: MediaRecorder?, what: Int, extra: Int) {
                Logger.i(what.toString())
            }

        })

        mMediaRecorder.setOnErrorListener { mr, what, extra ->
            Logger.e(what.toString())
        }
        return this
    }

    fun getSurface(): Surface {
        return mMediaRecorder.surface
    }

    fun requestRecord(
        cameraDevice: CameraDevice,
        surfaces: MutableList<Surface>,
        handler: Handler
    ): VideoRecoder {
        val captureRequest = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_RECORD)
        captureRequest.addTarget(getSurface())
        surfaces.add(getSurface())
        cameraDevice.createCaptureSession(surfaces, object : CameraCaptureSession.StateCallback() {
            override fun onConfigureFailed(session: CameraCaptureSession?) {
                Logger.i("video onConfigureFailed")
            }

            override fun onConfigured(session: CameraCaptureSession?) {
                startRecord()
                session!!.setRepeatingRequest(captureRequest.build(), null, handler)
            }

        }, handler)
        return this
    }

    fun startRecord() {
        mMediaRecorder.start()
    }

}