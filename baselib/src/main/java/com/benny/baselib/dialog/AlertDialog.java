package com.benny.baselib.dialog;


import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.benny.baselib.R;

/**
 * Created by yuanbb on 2017/8/24.
 */

public class AlertDialog extends AppCompatDialog {


    private AlertController mAlert;


    public AlertDialog(Context context, int theme) {
        super(context, theme);
        mAlert = new AlertController(this, getWindow());
    }


    public void setText(int viewId, CharSequence text) {
        mAlert.getViewHelper().setText(viewId, text);
    }


    public <T extends View> T getView(int viewId){
        return mAlert.getViewHelper().getView(viewId);
    }

    public static class Builder {


        private AlertController.AlertParams P;

        public Builder(Context context) {
            this(context, R.style.BaseStyle_BaseDialog);
        }

        public Builder(Context context, int themeResId) {
            P = new AlertController.AlertParams(context);
            P.themeResId = themeResId;
        }

        public Builder setText(int viewId, CharSequence text) {
            P.textArray.put(viewId, text);
            return this;
        }

        public Builder setOnClickListener(int viewId, View.OnClickListener listener) {
            P.clickArray.put(viewId, listener);
            return this;
        }


        public Builder setContentView(int layoutId) {
            P.view = null;
            P.viewLayoutResId = layoutId;
            return this;
        }

        public Builder setContentView(View view) {
            P.viewLayoutResId = 0;
            P.view = view;
            return this;
        }


        public Builder setCancelable(boolean cancelable) {
            P.cancelable = cancelable;
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            P.onCancelListener = onCancelListener;
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            P.onDismissListener = onDismissListener;
            return this;
        }


        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            P.onKeyListener = onKeyListener;
            return this;
        }


        public Builder fullWidth() {
            P.width = LayoutParams.MATCH_PARENT;
            return this;
        }

        public Builder fromBottom(boolean isAnimation) {
            if (isAnimation) {
                P.animation = R.style.BaseStyle_BaseDialog_BottomAnimation;
            }
            P.gravity = Gravity.BOTTOM;
            return this;
        }

        public Builder setWidthHeight(int width, int height) {
            P.width = width;
            P.height = height;
            return this;
        }


        public AlertDialog create() {
            // Context has already been wrapped with the appropriate theme.
            final AlertDialog dialog = new AlertDialog(P.context, P.themeResId);
            P.apply(dialog.mAlert);
            dialog.setCancelable(P.cancelable);
            if (P.cancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.onCancelListener);
            dialog.setOnDismissListener(P.onDismissListener);
            if (P.onKeyListener != null) {
                dialog.setOnKeyListener(P.onKeyListener);
            }
            return dialog;
        }


        public AlertDialog show() {
            final AlertDialog dialog = create();
            dialog.show();
            return dialog;
        }

    }


}
