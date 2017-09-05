package com.benny.baselib.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Rect;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by yuanbb on 2017/9/5.
 */

public class BitmapCompressHelper {

    /**
     * 质量压缩图片
     *
     * 降低图片的质量，像素不会减少
     *
     * @param quality 压缩质量 0-100
     */
    public static void compressImageQuality(Bitmap bmp, File outFile, int quality) {
        //0-100,压缩质量
        // int quality =50;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.JPEG, quality, baos);

        try {
            FileOutputStream fos = new FileOutputStream(outFile);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 压缩图片尺寸
     *
     * 通过降低图片的尺寸大小降低图片文件大小
     *
     * @param ratio 尺寸压缩倍数
     */
    public static void compressImageSize(Bitmap bmp, File outFile, float ratio) {
        int width = (int) (bmp.getWidth() / ratio);
        int height = (int) (bmp.getHeight() / ratio);
        Bitmap result = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(bmp, null, new Rect(0, 0, width, height), null);

        compressImageQuality(result, outFile, 100);
    }

    /**
     * 根据采样率压缩图片
     *
     * 读取图片文件时，按照一定采样率加载Bitmap
     *
     * @param simpleSize 采样率 2的倍数
     */
    public static void compressImageSimpleSize(String inFile, File outFile, int simpleSize) {
        BitmapFactory.Options options = new Options();
        options.inJustDecodeBounds = false;  //true,只加载图片的宽高等元数据信息
        options.inSampleSize = simpleSize; //采样率，必须是2的倍数
        Bitmap bitmap = BitmapFactory.decodeFile(inFile, options);
        compressImageQuality(bitmap, outFile, 100);
    }


}
