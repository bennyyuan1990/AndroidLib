package com.benny.libapp;

import android.util.Log;
import com.benny.libapp.orm.UserBean;

/**
 * Created by yuanbb on 2017/9/30.
 */

public class DataBindingHandler {

    private static final String TAG = "DataBindingHandler";

    public void getUser(UserBean userBean) {
        Log.d(TAG, "getUser: " + userBean);
    }

}
