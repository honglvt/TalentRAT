package com.hc.calling.api.observerCallBack

interface DesCallBack<T> {
    fun success(any: T)
    fun failed(e: Throwable)
}