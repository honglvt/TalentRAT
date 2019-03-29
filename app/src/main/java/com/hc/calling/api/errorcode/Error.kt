package com.hc.calling.api.errorcode

class Error() {
    companion object {
        val UNAUTHORIZED = 401 //  未授权的请求
        val FORBIDDEN = 403//禁止访问
        val NOT_FOUND = 404//服务器地址未找到
        val REQUEST_TIMEOUT = 408//请求超时
        val INTERNAL_SERVER_ERROR = 500//服务器出错
        val BAD_GATEWAY = 502//无效的请求
        val SERVICE_UNAVAILABLE = 503//服务器不可用
        val GATEWAY_TIMEOUT = 504//网关响应超时
        val ACCESS_DENIED = 302//网络错误
        val HANDEL_ERROR = 417//接口处理失败

        /**
         * 未知错误
         */
        val UNKNOWN = 1000
        /**
         * 解析错误
         */
        val PARSE_ERROR = 1001
        /**
         * 网络错误
         */
        val NETWORK_ERROR = 1002
        /**
         * 协议出错
         */
        val HTTP_ERROR = 1003

        /**
         * 证书出错
         */
        val SSL_ERROR = 1005

        /**
         * 连接超时
         */
        val TIMEOUT_ERROR = 1006

        /**
         * 证书未找到
         */
        val SSL_NOT_FOUND = 1007

        /**
         * 出现空值
         */
        val NULL = -100

        /**
         * 格式错误
         */
        val FORMAT_ERROR = 1008
    }

}