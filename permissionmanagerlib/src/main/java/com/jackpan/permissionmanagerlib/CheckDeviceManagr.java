package com.jackpan.permissionmanagerlib;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.provider.Settings;

public class CheckDeviceManagr {
    //检测MIUI
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    //系统授权设置的弹框
    AlertDialog openAppDetDialog = null;
    AlertDialog openMiuiAppDetDialog = null;


    /**
     * 打开 APP 的详情设置
     */
    public void openAppDetails(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getString(R.string.app_name) + "需要访问 \"设备信息\"、\"相册\"、\"定位\" 和 \"外部存储器\",请到 \"应用信息 -> 权限\" 中授予！");
        builder.setPositiveButton("手动授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                context.startActivity(intent);
            }
        });
        builder.setCancelable(false);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((Activity)context).finish();
            }
        });
        if (null == openAppDetDialog)
            openAppDetDialog = builder.create();
        if (null != openAppDetDialog && !openAppDetDialog.isShowing())
            openAppDetDialog.show();
    }
    /**
     * 打开 APP 的详情设置
     */
    private void openMiuiAppDetails(final  Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getString(R.string.app_name) + "需要访问 \"设备信息\"、\"相册\"、\"定位\" 和 \"外部存储器\",请到 \"应用信息 -> 权限\" 中授予！");
        builder.setPositiveButton("手动授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                JumpPermissionManagement.GoToSetting((Activity) context);
            }
        });
        builder.setCancelable(false);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((Activity)context).finish();
            }
        });
        if (null == openMiuiAppDetDialog)
            openMiuiAppDetDialog = builder.create();
        if (null != openMiuiAppDetDialog && !openMiuiAppDetDialog.isShowing())
            openMiuiAppDetDialog.show();
    }
    /**
     * 判断小米MIUI系统中授权管理中对应的权限授取
     *
     * @return false 存在核心的未收取的权限   true 核心权限已经全部授权
     */
    public boolean initMiuiPermission(Context context) {
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        int locationOp = appOpsManager.checkOp(AppOpsManager.OPSTR_FINE_LOCATION, Binder.getCallingUid(), context.getPackageName());
        if (locationOp == AppOpsManager.MODE_IGNORED) {
            return false;
        }

        int cameraOp = appOpsManager.checkOp(AppOpsManager.OPSTR_CAMERA, Binder.getCallingUid(), context.getPackageName());
        if (cameraOp == AppOpsManager.MODE_IGNORED) {
            return false;
        }

        int phoneStateOp = appOpsManager.checkOp(AppOpsManager.OPSTR_READ_PHONE_STATE, Binder.getCallingUid(), context.getPackageName());
        if (phoneStateOp == AppOpsManager.MODE_IGNORED) {
            return false;
        }

        int readSDOp = appOpsManager.checkOp(AppOpsManager.OPSTR_READ_EXTERNAL_STORAGE, Binder.getCallingUid(), context.getPackageName());
        if (readSDOp == AppOpsManager.MODE_IGNORED) {
            return false;
        }

        int writeSDOp = appOpsManager.checkOp(AppOpsManager.OPSTR_WRITE_EXTERNAL_STORAGE, Binder.getCallingUid(), context.getPackageName());
        if (writeSDOp == AppOpsManager.MODE_IGNORED) {
            return false;
        }
        return true;
    }
}
}
