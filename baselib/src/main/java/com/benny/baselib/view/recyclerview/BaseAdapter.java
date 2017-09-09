package com.benny.baselib.view.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benny on 2017/9/1.
 */

public abstract class BaseAdapter<T extends BaseAdapter.BaseViewHolder, I> extends RecyclerView.Adapter<T> {

    private List<I> mData;
    private SparseArray<WeakReference<View.OnClickListener>> mClickListeners = new SparseArray<>();
    private View mItemView;

    public BaseAdapter(View itemView, List<I> data) {
        mData = data == null ? new ArrayList<I>() : data;
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType){
        T viewHolder = onCreateCustomViewHolder(parent, viewType);
        if(viewHolder == null) viewHolder = (T) new BaseViewHolder(mItemView);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(T holder, int position) {
        onBindViewData(holder, mData.get(position));
    }

    public abstract void onBindViewData(T holder,I data);
    
    public T onCreateCustomViewHolder(ViewGroup parent, int viewType){
        return  null;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {

        private SparseArray<View> mViews = new SparseArray<>();


        public BaseViewHolder(View itemView) {
            super(itemView);
        }


        public <T extends View> T getView(int id) {
            View view = mViews.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                if (view != null) mViews.append(id, view);
            }
            return (T) view;
        }

        public BaseViewHolder setViewText(int id, Character text) {
            View view = getView(id);
            if (view != null || view instanceof TextView) ((TextView) view).setText(text);
            return this;
        }

        public BaseViewHolder setChildOnClickListener(int id, View.OnClickListener listener) {
            View view = getView(id);
            if (view != null) view.setOnClickListener(listener);
            return  this;
        }


        public void setRootOnClickListener(View.OnClickListener listener) {
            itemView.setOnClickListener(listener);
        }

    }
}