package com.benny.baselib.hook;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Benny on 2017/9/9.
 */

public class ActivityHook {
    private static final String TAG = "ActivityHook";

    public boolean register() {
        try {
            Class<?> activityManagerClass = Class.forName("android.app.IActivityManager");
            Class<?> activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative");
            Field gDefaultField = activityManagerNativeClass.getDeclaredField("gDefault");
            gDefaultField.setAccessible(true);
            Object gDefault = gDefaultField.get(null);

            Class<?>  singletonClass = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClass.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            final Object activityManagerInstance = mInstanceField.get(gDefault);

            Log.d(TAG, "register: " + activityManagerInstance);

            Object proxyActivityManager = Proxy.newProxyInstance(ActivityHook.class.getClassLoader(), new Class[]{activityManagerClass}, new ActivityInvocationHandler(activityManagerInstance));

            mInstanceField.set(gDefault,proxyActivityManager);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public class ActivityInvocationHandler implements InvocationHandler {
        private Object mActivityManager;

        public ActivityInvocationHandler(Object activityManager) {
            this.mActivityManager = activityManager;
        }


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.d(TAG, "invoke: " + method);
            return method.invoke(mActivityManager,args);
        }
    }
}
