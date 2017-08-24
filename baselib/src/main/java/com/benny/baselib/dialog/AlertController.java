package com.benny.baselib.dialog;

import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by yuanbb on 2017/8/24.
 */

public class AlertController {

    private DialogViewHelper mViewHelper;
    private AlertDialog mDialog;
    private Window mWindow;

    public AlertController(AlertDialog dialog, Window window) {
        mDialog = dialog;
        mWindow = window;
    }

    /**
     * 获取Dialog
     */
    public AlertDialog getDialog() {
        return mDialog;
    }

    /**
     * 获取window
     */
    public Window getWindow() {
        return mWindow;
    }

    public DialogViewHelper getViewHelper() {
        return mViewHelper;
    }

  /*  *//**
     * 设置View的辅助
     *//*
    public void setDialogViewHelper(DialogViewHelper viewHelper) {
        this.mViewHelper = viewHelper;
    }

    *//**
     * 设置文本
     *//*
    public void setText(int viewId, CharSequence text) {
        mViewHelper.setText(viewId, text);
    }

    *//**
     * 设置点击事件
     *//*
    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        mViewHelper.setOnClickListener(viewId, listener);
    }*/

    /**
     * 通过id获取View
     */
    public <T extends View> T getView(int viewId) {
        return mViewHelper.getView(viewId);
    }


    public static class AlertParams {

        public Context context;
        public final LayoutInflater inflater;
        public boolean cancelable = true;
        public View view;
        public int viewLayoutResId;
        public int themeResId;
        public SparseArray<CharSequence> textArray = new SparseArray<>();
        public SparseArray<View.OnClickListener> clickArray = new SparseArray<>();
        public OnCancelListener onCancelListener;
        public OnDismissListener onDismissListener;
        public OnKeyListener onKeyListener;
        public int width = LayoutParams.WRAP_CONTENT;
        public int gravity = Gravity.CENTER;
        public int animation = 0;
        public int height = LayoutParams.WRAP_CONTENT;


        public AlertParams(Context context) {
            this.context = context;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void apply(AlertController dialog) {
            DialogViewHelper dialogViewHelper = null;

            if (view != null) {
                dialogViewHelper = new DialogViewHelper(context, view);

            } else if (viewLayoutResId != 0) {
                dialogViewHelper = new DialogViewHelper(context, viewLayoutResId);
            } else {
                throw new IllegalArgumentException("请设置Dialog对话框视图布局");
            }

            dialog.mViewHelper = dialogViewHelper;/**/

            //设置对话框内容布局
            dialog.getDialog().setContentView(dialogViewHelper.getContentView());

            //设置文本内容
            for (int i = 0, count = textArray.size(); i < count; i++) {
                dialogViewHelper.setText(textArray.keyAt(i), textArray.get(textArray.keyAt(i)));
            }

            //设置点击事件
            for (int i = 0, count = clickArray.size(); i < count; i++) {
                dialogViewHelper.setOnClickListener(clickArray.keyAt(i), clickArray.get(clickArray.keyAt(i)));
            }

            Window window = dialog.getWindow();
            window.setGravity(gravity);

            if (animation != 0) {
                window.setWindowAnimations(animation);
            }

            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.width = width;
            layoutParams.height = height;
            window.setAttributes(layoutParams);

        }

    }


}
