package com.yangcao.simpleresume.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Rainie on 4/10/17.
 */

public class PermissionUtils {
    public static final int REQ_CODE_WRITE_EXTERNAL_STORAGE = 200;

    public static boolean checkPermission(@NonNull Context context,
                                          @NonNull String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M  //android 6.0
                || ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermissons(@NonNull Activity activity,
                                         @NonNull String[] permissions,
                                         int reqCode) {
        ActivityCompat.requestPermissions(activity, permissions, reqCode);
    }

    public static void requestReadExternalStoragePermission(@NonNull Activity activity) {
        requestPermissons(activity,
                          new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                          REQ_CODE_WRITE_EXTERNAL_STORAGE);
    }
}
