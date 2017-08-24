package com.benny.baselib.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import java.lang.ref.WeakReference;

/**
 * Created by yuanbb on 2017/8/24.
 */

public class DialogViewHelper {

    private View mContentView;
    private Context mContext;

    private SparseArray<WeakReference<View>> mViews = new SparseArray<>();
    private SparseArray<WeakReference<OnClickListener>> mClickListeners = new SparseArray<>();

    public DialogViewHelper(Context context, View view) {
        mContext = context;
        mContentView = view;
    }


    public DialogViewHelper(Context context, int viewId) {
        mContext = context;
        mContentView = LayoutInflater.from(context).inflate(viewId, null);
    }


    public void setText(int viewId, CharSequence text) {
        View view = getView(viewId);
        if (view != null && view instanceof TextView) {
            ((TextView) view).setText(text);
        }
    }

    public void setOnClickListener(int viewId, OnClickListener listener) {
        mClickListeners.put(viewId, new WeakReference<>(listener));
        View view = getView(viewId);
        if(view !=null) view.setOnClickListener(listener);
    }

    public <T extends View> T getView(int viewId) {
        WeakReference<View> viewWeakReference = mViews.get(viewId);
        if (viewWeakReference != null && viewWeakReference.get() != null) {
            return (T) viewWeakReference.get();
        } else {
            View view = mContentView.findViewById(viewId);
            if (view != null) {
                mViews.append(viewId, new WeakReference<>(view));
                return (T) view;
            }
            return null;
        }
    }

    public View getContentView() {
        return mContentView;
    }
}
