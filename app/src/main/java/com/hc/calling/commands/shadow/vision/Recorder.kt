package com.hc.calling.commands.shadow.vision

import android.content.Context
import android.media.MediaRecorder

/**
 * Created by ChanHong on 2019/4/10
 *
 */
abstract class Recorder(val mContext: Context) {
    val mMediaRecorder = MediaRecorder()
    var mCamera: String? = null
    var mFilePath: String? = null
    fun record() {
        mFilePath = "$mFilePath.mp4"
    }

    open fun record(completed: (filePath: String) -> Unit) {
    }

    abstract fun initMediaRecorder(completed: () -> Unit)

    open fun takePhoto(completed: (filePath: String) -> Unit) {
        mFilePath = "$mFilePath.jpeg"
    }

}