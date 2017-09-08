package com.benny.baselib.image.selection;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import com.benny.baselib.R;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanbb on 2017/9/8.
 */

public class ImageSelectionAdapter extends RecyclerView.Adapter<ImageSelectionAdapter.ImageSelectionViewHolder> {


    private static final int VIEW_TYPE_TAKE_PHOTO = 1;
    private static final int VIEW_TYPE_SELECT_IMAGE = 2;

    private List<ImageSelectionBean> mImageData;
    private boolean mTaskPhoto;
    private int mSelectMaxCount = 9;
    private int mSelectedCount = 0;

    public void setImageSelectChangeListener(ImageSelectChangeListener imageSelectChangeListener) {
        this.mImageSelectChangeListener = imageSelectChangeListener;
    }

    private ImageSelectChangeListener mImageSelectChangeListener;

    public ImageSelectionAdapter(List<ImageSelectionBean> data, boolean taskPhoto) {
        mImageData = data == null ? new ArrayList<ImageSelectionBean>() : data;
        mTaskPhoto = taskPhoto;
    }

    public void setTaskPhoto(boolean taskPhoto) {
        this.mTaskPhoto = taskPhoto;
    }

    public void setSelectMaxCount(int selectMaxCount) {
        this.mSelectMaxCount = selectMaxCount;
    }

    @Override
    public ImageSelectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageSelectionViewHolder viewHolder = new ImageSelectionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_selection, parent, false));
        viewHolder.checkBox.setVisibility(viewType == VIEW_TYPE_TAKE_PHOTO ? View.INVISIBLE : View.VISIBLE);
        return viewHolder;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onBindViewHolder(final ImageSelectionViewHolder holder, int position) {
        if (position == 0 && mTaskPhoto) {
            holder.imageView.setImageDrawable(holder.imageView.getContext().getResources().getDrawable(R.mipmap.ic_add_a_photo_white_48dp));
            return;
        }
        final ImageSelectionBean bean = mImageData.get(position - (mTaskPhoto ? 1 : 0));

        holder.checkBox.setChecked(bean.isSelected());
        holder.checkBox.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return !holder.checkBox.isChecked()&& mSelectedCount >= mSelectMaxCount;
            }
        });
        holder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bean.setSelected(isChecked);
                if (mImageSelectChangeListener != null) {
                    mImageSelectChangeListener.onSelectChanged(getSelectedImages());
                }
            }
        });

        if (!TextUtils.isEmpty(bean.getFileName())) {
            Glide.with(holder.imageView.getContext())
                .load(bean.getFileName())
                .placeholder(R.mipmap.ic_photo_white_48dp)
                .centerCrop()
                .crossFade()
                .into(holder.imageView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && mTaskPhoto) {
            return VIEW_TYPE_TAKE_PHOTO;
        }

        return VIEW_TYPE_SELECT_IMAGE;
    }

    @Override
    public int getItemCount() {
        return (mImageData == null ? 0 : mImageData.size()) + (mTaskPhoto ? 1 : 0);
    }


    public void updateImage(List<ImageSelectionBean> data) {
        mImageData = data == null ? new ArrayList<ImageSelectionBean>() : data;
        notifyDataSetChanged();
        getSelectedImages();
    }


    private List<ImageSelectionBean> getSelectedImages() {
        mSelectedCount = 0;
        if (mImageData == null || mImageData.size() == 0) {
            return null;
        }
        ArrayList<ImageSelectionBean> result = new ArrayList();
        for (ImageSelectionBean bean : mImageData) {
            if (bean.isSelected()) {
                result.add(bean);
            }
        }
        mSelectedCount = result.size();
        return result;
    }

    static class ImageSelectionViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private CheckBox checkBox;

        public ImageSelectionViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_activity_selection_iv);
            checkBox = (CheckBox) itemView.findViewById(R.id.item_activity_selection_ck);
        }
    }


    public interface ImageSelectChangeListener {

        void onSelectChanged(List<ImageSelectionBean> data);
    }
}
