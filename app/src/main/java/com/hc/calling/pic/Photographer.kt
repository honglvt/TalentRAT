package com.hc.calling.pic

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore

/**
 * Created by ChanHong on 2019/3/20
 *
 */

class Photographer {
        companion object {
            val REQUEST_IMAGE_CAPTURE = 1

        }
    private fun dispatchTakePictureIntent(context: Activity) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(context.packageManager)?.also {
                context.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }
}