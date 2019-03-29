package com.hc.calling.api.commen

import android.util.Log
import com.hc.calling.api.observerCallBack.DesCallBack
import io.reactivex.Observer
import io.reactivex.disposables.Disposable


/**
 * subscribe的时候使用这个接口
 */
class Destiny<T> : Observer<T> {
    var callBack: DesCallBack<T>? = null

    constructor(callBack: DesCallBack<T>) {
        this.callBack = callBack
    }

    override fun onComplete() {

    }

    override fun onSubscribe(d: Disposable) {
    }

    override fun onNext(t: T) {
         callBack?.success(t)
    }

    override fun onError(e: Throwable) {
        Log.e(
            "medtap_http_error",
            "error_msg:" + e.message
                    + "\n"
                    + "error_cause:"
                    + e.cause
        )
        callBack?.failed(e)
    }

}