package com.hc.calling.api.basemodel

open class BaseModel<T> {

    var code: Int? = null
    var msg: String? = null
    var data: T? = null

}