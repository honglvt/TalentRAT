package com.hc.calling.api.api

import com.hc.calling.api.basemodel.BaseModel
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface NetService {

    @Multipart
    @POST("/upload")
    fun upload(
        @Part("description") description: RequestBody,
        @Part file: MultipartBody.Part
    ): Observable<BaseModel<String>>


}
