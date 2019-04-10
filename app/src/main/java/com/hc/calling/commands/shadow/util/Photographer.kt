package com.hc.calling.commands.shadow.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.media.ImageReader
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.util.Size
import android.view.Surface
import com.orhanobut.logger.Logger
import java.util.concurrent.Semaphore

/**
 * Created by ChanHong on 2019/3/20
 *@captureSessionCreatedCallback when the session created you can capture or record a video
 * @imgSaveComplete when the img saved you can do what u want to do
 */

class Photographer(
    context: Context,
    private val captureSessionCreatedCallback: (
        builder: CaptureRequest.Builder,
        session: CameraCaptureSession,
        device: CameraDevice,
        imageReader: ImageReader,
        surfaces: MutableList<Surface>,
        handler: Handler
    ) -> Unit
) {
    var mContext: Context = context
    var mCameraManager = mContext.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    var mCameraDevice: CameraDevice? = null
    var mPreviewSize: Size? = null
    var mCaptureSession: CameraCaptureSession? = null
    var mHandler: Handler? = null
    var surfaces = mutableListOf<Surface>()
    private var captureRequestBuilder: CaptureRequest.Builder? = null
    private var imageReader: ImageReader? = null
    private var cameraOpenCloseLock = Semaphore(1)

    init {
        val thread = HandlerThread("CameraBackground")
        thread.start()
        this.mHandler = Handler(thread.looper)

    }

    companion object {
        val REQUEST_IMAGE_CAPTURE = 1
        val CAMERA_FRONT = "1"
        val CAMERA_BACK = "0"
        var size = mutableMapOf<String, Int>()


        /**
         * take a photo and save in the APP path
         */
        fun capture(
            context: Context,
            captureRequestBuilder: CaptureRequest.Builder,
            captureSession: CameraCaptureSession,
            imageReader: ImageReader,
            device: CameraDevice,
            handler: Handler,
            imgSaveComplete: (filePath: String) -> Unit

        ) {
            captureRequestBuilder.set(
                CaptureRequest.CONTROL_AF_TRIGGER,
                CameraMetadata.CONTROL_AF_TRIGGER_START
            )
            captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CameraMetadata.CONTROL_AE_MODE_ON)
            captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CameraMetadata.CONTROL_AF_MODE_AUTO)
            imageReader.setOnImageAvailableListener(ImageReaderListener({
                imgSaveComplete(it)
            }, context), handler)

            captureRequestBuilder.addTarget(imageReader.surface)

            captureSession.capture(
                captureRequestBuilder.build(), object : CameraCaptureSession.CaptureCallback() {

                    override fun onCaptureStarted(
                        session: CameraCaptureSession?,
                        request: CaptureRequest?,
                        timestamp: Long,
                        frameNumber: Long
                    ) {
                        super.onCaptureStarted(session, request, timestamp, frameNumber)
                        Logger.i("onCaptureStarted")
                    }

                    override fun onCaptureCompleted(
                        session: CameraCaptureSession?,
                        request: CaptureRequest?,
                        result: TotalCaptureResult?
                    ) {
                        super.onCaptureCompleted(session, request, result)
                        Logger.i("onCaptureCompleted")
                        device.close()
                        captureSession.close()
                    }

                    override fun onCaptureFailed(
                        session: CameraCaptureSession?,
                        request: CaptureRequest?,
                        failure: CaptureFailure?
                    ) {
                        super.onCaptureFailed(session, request, failure)
                        Logger.i("onCaptureFailed")
                    }
                },
                handler
            )
        }
    }

    @SuppressLint("MissingPermission")
    fun openCamera(
        cameraID: String
    ): Photographer {
        if (getCamera(cameraID).isNotEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                mCameraManager.openCamera(cameraID, object : CameraDevice.StateCallback() {
                    override fun onOpened(camera: CameraDevice?) {
                        Logger.i("------------------------$cameraID is opened")
                        mCameraDevice = camera
                        cameraOpenCloseLock.release()
                        createCameraSeesion(
                            cameraDevice = camera!!
                        )
                    }

                    override fun onDisconnected(camera: CameraDevice?) {
                        Logger.i("------------------------${camera!!.id} is onDisconnected")
                        camera.close()
                    }

                    override fun onError(camera: CameraDevice?, error: Int) {
                        Logger.i("------------------------${camera!!.id} is error code is:$error")
                        camera.close()
                    }

                }, mHandler)
            }

        }
        return this
    }

    /**
     * get all camera
     */
    private fun getCamera(camera: String): String {

        val camaras = mutableMapOf<String, String>()

        val cameraList = mCameraManager.cameraIdList
        cameraList.forEach {
            camaras[it] = it
            com.orhanobut.logger.Logger.i(it)
        }
        return camaras[camera]!!
    }

    private fun createCameraSeesion(
        cameraDevice: CameraDevice
    ) {
        size.forEach {
            Logger.i(it.value.toString())
        }

        mPreviewSize = Size(
            size["width"]!!,
            size["height"]!!

        )

        val texture = SurfaceTexture(0)
        texture.setDefaultBufferSize(mPreviewSize!!.width, mPreviewSize!!.height)
        Logger.i(mPreviewSize!!.width.toString(), mPreviewSize!!.height.toString())
        val surface = Surface(texture)
        captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
        captureRequestBuilder!!.set(
            CaptureRequest.CONTROL_AF_TRIGGER,
            CameraMetadata.CONTROL_AF_TRIGGER_START
        )


        imageReader = ImageReader.newInstance(mPreviewSize!!.width, mPreviewSize!!.height, ImageFormat.JPEG, 1)


        surfaces.addAll(mutableListOf(surface, imageReader!!.surface))
        captureRequestBuilder!!.addTarget(Surface(texture))
        cameraDevice.createCaptureSession(
            surfaces,
            CaptureStateCallback(captureRequestBuilder!!),
            mHandler
        )

    }


    inner class CaptureStateCallback(
        private val captureRequestBuilder: CaptureRequest.Builder
    ) :
        CameraCaptureSession.StateCallback() {

        override fun onConfigureFailed(session: CameraCaptureSession?) {
            Logger.i("camera config failed ")
        }

        override fun onConfigured(session: CameraCaptureSession?) {
            mCaptureSession = session
            try {
                captureRequestBuilder.set(
                    CaptureRequest.CONTROL_AF_MODE,
                    CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE
                )

                // repeating is order to get the steam from camera
                mCaptureSession!!.setRepeatingRequest(
                    captureRequestBuilder.build(),
                    object : CameraCaptureSession.CaptureCallback() {

                    },
                    mHandler
                )

                // if you want capture or record a video
                // you can do here
                captureSessionCreatedCallback(
                    captureRequestBuilder,
                    mCaptureSession!!,
                    mCameraDevice!!,
                    imageReader!!,
                    surfaces,
                    mHandler!!
                )
            } catch (e: CameraAccessException) {
                Logger.e(e.toString())
            }
        }

    }

    /**
     * the surface Listener to save img
     */
    open class ImageReaderListener(
        private val imgSaveComplete: (filePath: String) -> Unit,
        private val context: Context
    ) : ImageReader.OnImageAvailableListener {
        override fun onImageAvailable(reader: ImageReader?) {
            ImgSaver.saveImg(context, reader!!.acquireLatestImage()) {
                imgSaveComplete(it)
            }
        }

    }


    fun release() {
        mCaptureSession!!.close()
        mCameraDevice!!.close()
        imageReader!!.close()
    }
}