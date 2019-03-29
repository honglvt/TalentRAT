package com.hc.calling.api.commen

/**
 * 和服务端约定的错误
 */
class ApiException(message: String?, cause: Throwable?) : Exception(message, cause) {
    var code: Int? = 0
    var msg: String? = null
    var errorCause: Throwable? = null

    constructor(code: Int, message: String?, cause: Throwable?) : this(message, cause) {
        this.code = code
        this.msg = message
        this.errorCause = cause
    }

    constructor(code: Int, msg: String) : this(code, msg, Throwable()) {
        this.code = code
        this.msg = msg
    }

    fun msg(msg: String) {
        this.msg = msg
    }

    fun code(code: Int) {
        this.code = code
    }

}