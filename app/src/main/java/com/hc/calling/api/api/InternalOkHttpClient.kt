package com.hc.calling.api.api

import com.hc.calling.callingtransaction.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class InternalOkHttpClient {
    companion object {

        fun getOkhttpClient(): OkHttpClient {
            var okHttpClient: OkHttpClient? = null

            if (okHttpClient == null) {
                okHttpClient = OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build()

                if (BuildConfig.DEBUG) {//printf logs while  debug
                    okHttpClient = okHttpClient?.newBuilder()
                        ?.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))?.build()
                }
            }
            return okHttpClient!!
        }
    }
}