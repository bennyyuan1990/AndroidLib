package com.benny.baselib.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.widget.Toast;

/**
 * Created by yuanbb on 2017/10/9.
 */

public class NetworkHelper {

    private boolean NetWorkStatus(final Context context) {
        ConnectivityManager cwjManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        cwjManager.getActiveNetworkInfo();
        boolean netStatus = true;
        if (cwjManager.getActiveNetworkInfo() != null) {

            netStatus = cwjManager.getActiveNetworkInfo().isAvailable();

            Toast.makeText(context, "网络已经打开", Toast.LENGTH_LONG).show();

        } else {
            Builder b = new AlertDialog.Builder(context).setTitle("没有可用的网络")
                .setMessage("是否对网络进行设置？");
            b.setPositiveButton("是", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    Intent intent = new Intent("/");
                    ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
                    intent.setComponent(comp);
                    intent.setAction("android.intent.action.VIEW");
                    if (context instanceof Activity) {
                        ((Activity) context).startActivityForResult(intent, 0);
                    } else {
                        context.startActivity(intent);
                    }
                }
            }).setNeutralButton("否", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.cancel();
                }
            }).show();
        }
        return netStatus;
    }


    /**
     * 网络连接状态
     *
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     *
     * @param context 程序上下文
     * @return true:可连接
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * WiFi 网络连接状态
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
     *
     * @param context 程序上下文
     * @return true:可连接
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * Mobile 网络连接状态
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     *
     * @param context 程序上下文
     * @return true:可连接
     */
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取网络连接类型
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     *
     * @param context 程序上下文
     * @return ConnectivityManager.TYPE_XXXX
     */
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }


}

