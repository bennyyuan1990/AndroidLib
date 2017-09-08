package com.benny.baselib.image.selection;

import android.os.Parcel;

/**
 * Created by yuanbb on 2017/9/8.
 */

public class ImageSelectionBean implements android.os.Parcelable {
    private String fileName;
    private boolean isSelected ;
    private long dateTime;

    public ImageSelectionBean() {
    }

    public ImageSelectionBean(String fileName, long dateTime) {
        this.fileName = fileName;
        this.isSelected = false;
        this.dateTime = dateTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fileName);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
        dest.writeLong(this.dateTime);
    }

    protected ImageSelectionBean(Parcel in) {
        this.fileName = in.readString();
        this.isSelected = in.readByte() != 0;
        this.dateTime = in.readLong();
    }

    public static final Creator<ImageSelectionBean> CREATOR = new Creator<ImageSelectionBean>() {
        @Override
        public ImageSelectionBean createFromParcel(Parcel source) {
            return new ImageSelectionBean(source);
        }

        @Override
        public ImageSelectionBean[] newArray(int size) {
            return new ImageSelectionBean[size];
        }
    };
}
