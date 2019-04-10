package com.hc.calling.commands.shadow.vision

import android.content.Context
import android.media.MediaRecorder
import com.orhanobut.logger.Logger

/**
 * Created by ChanHong on 2019/4/10
 *
 */
abstract class Recorder(var mFilePath: String, val mContext: Context, var mCamera: String) {
    val mMediaRecorder = MediaRecorder()

    fun record() {
        mFilePath = "$mFilePath.mp4"
        Logger.i(mFilePath)
    }

    open fun record(completed: (filePath: String) -> Unit) {
    }

    abstract fun initMediaRecorder()

    open fun takePhoto(completed: (filePath: String) -> Unit) {
        mFilePath = "$mFilePath.jpeg"
    }

}