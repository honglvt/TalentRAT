package com.hc.calling.commands.shadow.util

import android.annotation.SuppressLint
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.media.CamcorderProfile
import android.media.MediaRecorder
import android.os.Handler
import android.view.Surface
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by ChanHong on 2019/3/28
 *
 */
class VideoRecoder(private val mFilePath: String) {


    val mMediaRecorder = MediaRecorder()

    fun initMediaRecorde() {
        mMediaRecorder.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setVideoSource(MediaRecorder.VideoSource.SURFACE)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setVideoEncodingBitRate(5 * 1024 * 1024)
            //每秒30帧
            setVideoEncoder(MediaRecorder.VideoEncoder.H264)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setVideoFrameRate(15)
            val prifile = CamcorderProfile.get(CamcorderProfile.QUALITY_1080P)
            setVideoSize(prifile.videoFrameWidth, prifile.videoFrameHeight)
            setOutputFile(mFilePath)
            setOrientationHint(90)
        }

    }

    fun requestRecord(
        cameraDevice: CameraDevice,
        surfaces: MutableList<Surface>,
        handler: Handler,
        recordCompeled: (filePath: String) -> Unit
    ) {
        initMediaRecorde()
        mMediaRecorder.prepare()
        val captureRequest = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_RECORD)
        captureRequest.addTarget(mMediaRecorder.surface)
        surfaces.add(mMediaRecorder.surface)
        cameraDevice.createCaptureSession(surfaces, object : CameraCaptureSession.StateCallback() {
            override fun onConfigureFailed(session: CameraCaptureSession?) {
                Logger.i("video onConfigureFailed")
            }

            @SuppressLint("CheckResult")
            override fun onConfigured(session: CameraCaptureSession?) {
                startRecord()
                session!!.setRepeatingRequest(captureRequest.build(), null, handler)

                //close the camera
                Observable.timer(10, TimeUnit.SECONDS)
                    .subscribe {
                        Logger.i("$it..........")
                        mMediaRecorder.stop()
                        mMediaRecorder.release()
                        cameraDevice.close()
                        recordCompeled(mFilePath)
                    }
            }

        }, handler)
    }

    fun startRecord() {
        mMediaRecorder.start()
    }

}