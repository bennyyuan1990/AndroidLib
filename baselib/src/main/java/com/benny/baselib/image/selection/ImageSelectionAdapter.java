package com.benny.baselib.image.selection;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import com.benny.baselib.R;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanbb on 2017/9/8.
 */

public class ImageSelectionAdapter extends RecyclerView.Adapter<ImageSelectionAdapter.ImageSelectionViewHolder> {

    private List<ImageSelectionBean> mImageData;
    private boolean mTaskPhoto;
    private static final int VIEW_TYPE_TAKE_PHOTO = 1;
    private static final int VIEW_TYPE_SELECT_IMAGE = 2;

    public ImageSelectionAdapter(List<ImageSelectionBean> data, boolean taskPhoto) {
        mImageData = data == null ? new ArrayList<ImageSelectionBean>() : data;
        mTaskPhoto = taskPhoto;
    }


    @Override
    public ImageSelectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageSelectionViewHolder viewHolder = new ImageSelectionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_selection, parent, false));
        viewHolder.checkBox.setVisibility(viewType == VIEW_TYPE_TAKE_PHOTO ? View.INVISIBLE : View.VISIBLE);
        return viewHolder;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onBindViewHolder(ImageSelectionViewHolder holder, int position) {
        if (position == 0 && mTaskPhoto) {
            holder.imageView.setImageDrawable(holder.imageView.getContext().getResources().getDrawable(R.mipmap.ic_add_a_photo_white_48dp));
            return;
        }
        ImageSelectionBean bean = mImageData.get(position - (mTaskPhoto?1:0));
        if(!TextUtils.isEmpty(bean.getFileName())){
            Glide.with(holder.imageView.getContext())
                .load(bean.getFileName())
                .placeholder(R.mipmap.ic_photo_white_48dp)
                .fitCenter()
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


    static class ImageSelectionViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private CheckBox checkBox;

        public ImageSelectionViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_activity_selection_iv);
            checkBox = (CheckBox) itemView.findViewById(R.id.item_activity_selection_ck);
        }
    }

}
