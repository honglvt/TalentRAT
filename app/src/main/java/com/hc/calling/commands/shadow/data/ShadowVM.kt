package com.hc.calling.commands.shadow.data

import android.annotation.SuppressLint
import com.hc.calling.api.api.ApiClient
import com.hc.calling.api.transformHelper.RxStreamHelper
import com.orhanobut.logger.Logger
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * Created by ChanHong on 2019/3/27
 *
 */
class ShadowVM {
    @SuppressLint("CheckResult")
    fun upLoadFile(file: File, action: (data: String) -> Unit) {

        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
        val description = "its a pic"
        val requestDescription = RequestBody.create(MediaType.parse("multipart/form-data"), description)

        Logger.i(file.path)

        ApiClient
            .instance
            .getApiService()
            .upload(requestDescription, body)
            .compose(RxStreamHelper().io_Main())
            .subscribe {
                action(it!!)
            }
    }
}