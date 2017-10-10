package com.benny.baselib.image.easyloader.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

/**
 * Created by yuanbb on 2017/10/10.
 */

public abstract class BitmapDecoder {


    public Bitmap decodeBitmap(int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //读取图片元数据参数信息
        options.inJustDecodeBounds = true;
        decodeBitmapWithOption(options);

        //计算缩放比列
        calculateSampleSizeWithOption(options, reqWidth, reqHeight);

        return decodeBitmapWithOption(options);
    }

    /**
     * 计算图片缩放参数
     */
    private void calculateSampleSizeWithOption(Options options, int reqWidth, int reqHeight) {

        int width = options.outWidth;
        int height = options.outHeight;

        int inSampleSize = 1;

        if (width > reqWidth || height > reqHeight) {
            int heihtRatio = (int) Math.round(height * 1.0 / reqHeight);
            int widthRatio = (int) Math.round(width * 1.0 / reqWidth);

            inSampleSize = Math.max(heihtRatio, widthRatio);
        }

        options.inSampleSize = inSampleSize;

        //使用16位，每个像素占用2字节
        options.inPreferredConfig = Config.RGB_565;
        options.inJustDecodeBounds = false;
        //内存不足时，回收图片内存
        options.inPurgeable = true;
        options.inInputShareable = true;
    }

    /**
     * 读取图片 参数元数据 信息
     */
    protected abstract Bitmap decodeBitmapWithOption(Options options);
}
