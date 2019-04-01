package com.hc.calling.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by hongchen on 2018/5/17.
 */

public class PermissonUtil {

    public final static int PERMISSION_CEMERA = 1;
    public final static int PERMISSION_LOCATION = 2;

    public static boolean requestPermisson(Context context, String[] permissions, int requestCode) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, permissions, requestCode);
                return false;
            }
        }
        return true;
    }

    public interface PermissionCallBack {
        void permitted(String permission);

        void refused(String permission);
    }
}
