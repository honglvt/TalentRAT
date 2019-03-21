package com.hc.calling.socket

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter

/**
 * Created by ChanHong on 2019/3/20
 *
 */
class SocketConntector {

    fun clientEmitter(src: String): Emitter {
        var socket = IO.socket(src)

        socket.connect()
        return socket.on(Socket.EVENT_CONNECT, object : Emitter.Listener {
            override fun call(vararg args: Any?) {
                Log.i("socket_message", "-----------connect_success")
                socket.emit("new msg", "hahaha")
            }
        })
            .on(Socket.EVENT_DISCONNECT) {
                Log.i("socket_message", "-----------connect_disconnected")
            }

    }
}