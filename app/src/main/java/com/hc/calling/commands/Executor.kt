package com.hc.calling.commands

/**
 * Created by ChanHong on 2019/3/22
 *your command should implement this interface to do ur own work by fun execute
 */
interface Executor {

    /**
     * execute the command from server
     */
    fun execute(data: Array<Any>)
}