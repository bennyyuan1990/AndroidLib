package com.benny.baselib.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import java.io.File;

/**
 * Created by yuanbb on 2017/10/9.
 */

public class ApkHelper {


    /**
     * 安装APK应用
     */
    public static void installApk(Context context, String apkFilePath) {
        File apkfile = new File(apkFilePath);
        if (!apkfile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }


    /**
     * 获取应用程序版本号
     * @param context
     * @return
     */
    public static String getVersionName(Context context){
        String versioName = "1.0.0";
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pinfo = pm.getPackageInfo(context.getPackageName(),0);
            versioName = pinfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versioName;
    }

}
