package com.hc.calling.keeper

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

/**
 * Created by ChanHong on 2019/3/19
 *
 */
class KeeperActivity : AppCompatActivity() {

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, KeeperActivity::class.java)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("keeper_actiivty", "oncreate")
        val windowParams = window.attributes
        windowParams.x = 0
        windowParams.y = 0
        windowParams.height = 1
        windowParams.width = 1
        window.attributes = windowParams
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("keeper_actiivty", "onDestroy")

    }

}