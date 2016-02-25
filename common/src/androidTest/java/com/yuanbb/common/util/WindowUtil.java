package com.yuanbb.common.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by yuanbb on 2016/2/25.
 */
public class WindowUtil {

    @TargetApi(19)
    private void setTranslucentStatus(Activity context, boolean on) {
        Window win = context.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

}
