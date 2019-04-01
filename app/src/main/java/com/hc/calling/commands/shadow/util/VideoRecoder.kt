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
class VideoRecoder(private val recordCompeled: (filePath: String) -> Unit) {


    private val mMediaRecorder = MediaRecorder()
    private var filePath: String? = null

    fun initMediaRecorde(outputFilePath: String) {
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE)
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mMediaRecorder.setVideoEncodingBitRate(5 * 1024 * 1024)
        //每秒30帧
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264)
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mMediaRecorder.setVideoFrameRate(15)
        val prifile = CamcorderProfile.get(CamcorderProfile.QUALITY_1080P)
        mMediaRecorder.setVideoSize(prifile.videoFrameWidth, prifile.videoFrameHeight)
        mMediaRecorder.setOutputFile(outputFilePath)
        filePath = outputFilePath
        mMediaRecorder.setOrientationHint(90)
        mMediaRecorder.prepare()

        mMediaRecorder.setOnInfoListener(object : MediaRecorder.OnInfoListener {
            override fun onInfo(mr: MediaRecorder?, what: Int, extra: Int) {
                Logger.i(what.toString())
            }

        })

    }

    fun getSurface(): Surface {
        return mMediaRecorder.surface
    }

    fun requestRecord(
        cameraDevice: CameraDevice,
        surfaces: MutableList<Surface>,
        handler: Handler
    ) {
        val captureRequest = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_RECORD)
        captureRequest.addTarget(getSurface())
        surfaces.add(getSurface())
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
                        recordCompeled(filePath!!)
                    }
            }

        }, handler)
    }

    fun startRecord() {
        mMediaRecorder.start()
    }

}