package com.system.everydayvideo.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

public class PermissionHelper {
    Activity activity;

    public PermissionHelper(Activity activity) {
        this.activity = activity;
    }

    /**
     * 第 1 步: 检查是否拥有指定的所有权限
     */

    public boolean checkPermissionAllGranted(String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                    // 只要有一个权限没有被授予, 则直接返回 false
                    return false;
                }
            }
            return true;
        }
        return true;
    }

    /**
     * 第 2 步: 请求权限
     */
    // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
    public void requestPermissionAllGranted(String[] permissions, int i) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(activity, permissions, i);
        }
    }
}
