package com.hc.calling.api.api

import com.hc.calling.MainService
import com.hc.calling.callingtransaction.BuildConfig
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient() {
    private var BASE_URL_MAIN = BuildConfig.SERVER_ADDRESS
    private var retrofit: Retrofit? = null
    private var interceptor: Interceptor? = null
    private var netInterceptor: Interceptor? = null

    private var apiService: NetService? = null

    companion object {
        val instance: ApiClient by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ApiClient()
        }
    }

    fun getApiService(): NetService {
        return if (apiService == null) retrofit!!.create(NetService::class.java) else apiService!!
    }

    /**
     * return the retrofit finally
     */
    fun build() {
        val okHttpClient = InternalOkHttpClient.getOkhttpClient()
        if (interceptor != null) {
            okHttpClient.newBuilder().addInterceptor(interceptor!!)
        }
        if (netInterceptor != null) {
            okHttpClient.newBuilder().addInterceptor(netInterceptor!!)
        }

        this.retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL_MAIN)
            .client(okHttpClient)
            .build()
    }

    /**
     *  add the personal BASE_URL_MAIN for the project
     */
    fun addBaseUrl(baseURL: String): ApiClient {
        this.BASE_URL_MAIN = baseURL
        return this
    }

    /**
     * add a special interceptpr for okhttpCLient to log sth before a net work
     */
    fun addIntercepet(interceptor: Interceptor): ApiClient {
        this.interceptor = interceptor
        return this
    }

    /**
     * add a special interceptor when a request finish
     */
    fun addNewWorkIntercept(netInterceptor: Interceptor): ApiClient {
        this.netInterceptor = netInterceptor
        return this
    }

}