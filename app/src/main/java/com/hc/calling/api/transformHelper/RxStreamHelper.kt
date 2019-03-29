package com.hc.calling.api.transformHelper

import com.hc.calling.api.basemodel.BaseModel
import com.hc.calling.api.commen.ApiException
import com.hc.calling.api.commen.CustomException
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers


/**
 * 统一处理，线程调度
 */
class RxStreamHelper {
    fun <T> io_Main(): ObservableTransformer<BaseModel<T>, T> {
        var t: T
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                //出错统一处理
                .onErrorResumeNext(Function { throwable -> Observable.error(CustomException.handleException(throwable)) })
                //解析data层，剔除 code /msg
                .flatMap { tBaseModel ->
                    if (tBaseModel.code == 200) {
                        Observable.just(tBaseModel.data)
                    } else Observable.error(ApiException(tBaseModel.code!!, tBaseModel.msg!!))
                }
                .observeOn(AndroidSchedulers.mainThread())
        }

    }
}
