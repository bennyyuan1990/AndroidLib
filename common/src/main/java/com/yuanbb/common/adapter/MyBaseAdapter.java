package com.yuanbb.common.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.HashMap;
import java.util.List;

/**
 * Created by yuanbb on 2016/2/26.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {

    private List<T> mListData;
    private int mViewResouce;
    private Context mContext;

    public MyBaseAdapter(Context context, List<T> list, int viewResouce) {
        mListData = list;
        mViewResouce = viewResouce;
        mContext = context;
    }



    @Override
    public int getCount() {
        return mListData == null ? 0 : mListData.size();
    }

    @Override
    public Object getItem(int position) {
        if (mListData != null) return mListData.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, mViewResouce,null);
            convertView.setTag(new ViewHodler(convertView));
        }
        setViewValue(mListData ==null?null:mListData.get(position), (ViewHodler) convertView.getTag());
        return convertView;
    }

    /**
     * 设置视图控件内容值
     * @param obj list项
     * @param viewHodler 视图内容
     */
    public abstract void setViewValue(T obj,ViewHodler viewHodler);


    public class ViewHodler {
        private View mView;
        private SparseArray<View> mChildView;

        public ViewHodler(View view) {
            mView = view;
            mChildView = new SparseArray<View>();
        }

        public <I extends View> I getView(int resource) {
            if (mChildView.indexOfKey(resource) < 0) {
                mChildView.put(resource, mView.findViewById(resource));
            }
            return (I) mChildView.get(resource);
        }

    }

}
