package com.benny.baselib.hook;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Benny on 2017/9/9.
 */

public class ActivityHook {
    private static final String TAG = "ActivityHook";
    static ActivityHook mInstance;

    private static final String PROXY_EXTRA_ORIGIN_INTENT = "ORIGIN_INTENT";

    public static ActivityHook getInstance() {
        return mInstance;
    }

    static {
        mInstance = new ActivityHook();
    }

    private ActivityHook() {

    }

    private WeakReference<Context> mContext;
    private Class<?> mProxyActivityClass;

    public boolean hookStartActivity(Context context, Class<?> proxyActivityClass) {
        mContext = new WeakReference<>(context);
        mProxyActivityClass = proxyActivityClass;
        try {
            Class<?> activityManagerClass = Class.forName("android.app.IActivityManager");
            Class<?> activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative");
            Field gDefaultField = activityManagerNativeClass.getDeclaredField("gDefault");
            gDefaultField.setAccessible(true);
            Object gDefault = gDefaultField.get(null);

            Class<?> singletonClass = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClass.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            final Object activityManagerInstance = mInstanceField.get(gDefault);

            Log.d(TAG, "register: " + activityManagerInstance);

            Object proxyActivityManager = Proxy.newProxyInstance(ActivityHook.class.getClassLoader(), new Class[]{activityManagerClass}, new ActivityInvocationHandler(activityManagerInstance));

            mInstanceField.set(gDefault, proxyActivityManager);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        hookLaunchActivity();
        return true;
    }

    private void hookLaunchActivity() {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
            Object sCurrentActivityThreadValue = currentActivityThreadMethod.invoke(null);

            Field mHField = activityThreadClass.getDeclaredField("mH");
            mHField.setAccessible(true);
            Object mHValue = mHField.get(sCurrentActivityThreadValue);
            Field mCallbackField = android.os.Handler.class.getDeclaredField("mCallback");
            mCallbackField.setAccessible(true);
            mCallbackField.set(mHValue, new HCallback());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private class HCallback implements android.os.Handler.Callback {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 100) {
                handleLaunchActivity(msg);
            }
            return false;
        }
    }

    private void handleLaunchActivity(Message msg) {
        Object r = msg.obj;
        try {
            Field intentField = r.getClass().getDeclaredField("intent");
            intentField.setAccessible(true);
            Intent intent = (Intent) intentField.get(r);
            Intent originIntent = intent.getParcelableExtra(PROXY_EXTRA_ORIGIN_INTENT);
            if (originIntent != null) {
                intentField.set(r, originIntent);
            }


            // 兼容AppCompatActivity报错问题
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
            Object sCurrentActivityThreadValue = currentActivityThreadMethod.invoke(null);
            Method getPackageManager = sCurrentActivityThreadValue.getClass().getDeclaredMethod("getPackageManager");
            Object iPackageManager = getPackageManager.invoke(sCurrentActivityThreadValue);

            Class<?> iPackageManagerClass = Class.forName("android.content.pm.IPackageManager");
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class<?>[]{iPackageManagerClass}, new PackageManagerHandler(iPackageManager));

            // 获取 sPackageManager 属性
            Field iPackageManagerField = sCurrentActivityThreadValue.getClass().getDeclaredField("sPackageManager");
            iPackageManagerField.setAccessible(true);
            iPackageManagerField.set(sCurrentActivityThreadValue, proxy);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class ActivityInvocationHandler implements InvocationHandler {
        private Object mActivityManager;

        public ActivityInvocationHandler(Object activityManager) {
            this.mActivityManager = activityManager;
        }


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if (method.getName().equals("startActivity")) {

                int index = 0;
                for (int i = 0, count = args.length; i < count; i++) {
                    if (args[i] instanceof Intent) {
                        index = i;
                        break;
                    }
                }
                //获取原Intent
                Intent originIntent = (Intent) args[index];
                Intent intent = new Intent(mContext.get(), mProxyActivityClass);
                args[index] = intent;

                intent.putExtra(PROXY_EXTRA_ORIGIN_INTENT, originIntent);


            }
            return method.invoke(mActivityManager, args);
        }
    }

    public class PackageManagerHandler implements InvocationHandler {
        private Object mPackageManager;

        public PackageManagerHandler(Object packageManager) {
            this.mPackageManager = packageManager;
        }


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.d(TAG, "invoke: " + method);

            if (method.getName().equals("getActivityInfo")) {
                ComponentName componentName = new ComponentName(mContext.get(), mProxyActivityClass);
                args[0] = componentName;
            }
            return method.invoke(mPackageManager, args);
        }
    }
}
