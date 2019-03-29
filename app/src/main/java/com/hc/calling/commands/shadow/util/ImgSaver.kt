package com.hc.calling.commands.shadow.util

import android.content.Context
import android.media.Image
import com.hc.calling.util.DateUtil
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * Created by ChanHong on 2019/3/28
 *
 */
class ImgSaver {
    companion object {
        fun saveImg(context: Context, img: Image, imgSaveComplete: (filePath: String) -> Unit) {
            GlobalScope.launch(Dispatchers.IO) {
                val file =
                    File(context.getExternalFilesDir(null), DateUtil.GetNowDate("yyyyMMddHHmmss") + ".jpg")
                val buffer = img.planes[0].buffer
                val bytes = ByteArray(buffer.remaining())
                buffer.get(bytes)
                var outPut: FileOutputStream? = null

                try {

                    outPut = FileOutputStream(file).apply {
                        write(bytes)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    outPut?.let {
                        try {
                            Logger.i("the pic is saved at ${file.path}")

                            img.close()
                            it.close()
                            imgSaveComplete(file.path)
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
    }
}