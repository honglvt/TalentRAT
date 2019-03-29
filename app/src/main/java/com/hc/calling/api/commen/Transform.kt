package com.hc.calling.api.commen

/**
 * you must impliment this interface when u create a bean
 */
interface Transform<T> {
    fun transform(): T
}