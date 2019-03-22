package com.hc.calling.commands

import com.hc.calling.socket.SocketConductor
import io.socket.emitter.Emitter

/**
 * Created by ChanHong on 2019/3/22
 *
 */
open class Command {
    fun emitData(cmd: String, data: Any): Emitter {
        return SocketConductor.instance.emmiter!!.emit(cmd, data)
    }
}