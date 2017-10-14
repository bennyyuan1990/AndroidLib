package com.benny.baselib.image.easyloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Environment;
import com.benny.baselib.image.easyloader.request.BitmapRequest;
import com.benny.baselib.image.easyloader.utils.BitmapDecoder;
import com.benny.baselib.image.easyloader.utils.ImageViewHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Benny on 2017/10/4.
 */

public class NetLoader extends AbsBaseLoader {

    @Override
    protected Bitmap onLoadBitmap(BitmapRequest request) {

        final File cacheFile = getCache(request.getUrlMD5());
        downloadImage(request.getUrl(), cacheFile);

        BitmapDecoder bitmapDecoder = new BitmapDecoder() {
            @Override
            protected Bitmap decodeBitmapWithOption(Options options) {
                return BitmapFactory.decodeFile(cacheFile.getPath(), options);
            }
        };
        return bitmapDecoder.decodeBitmap(ImageViewHelper.getImageViewWidth(request.getImageView()), ImageViewHelper.getImageViewHeight(request.getImageView()));

    }


    private static boolean downloadImage(String urlStr, File file) {

        try {

            HttpURLConnection connection = (HttpURLConnection) new URL(urlStr).openConnection();
            InputStream is = connection.getInputStream();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            is.close();
            fos.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private File getCache(String name) {
        File file = new File(Environment.getExternalStorageDirectory(), "ImageLoader");
        if (!file.exists()) {
            file.mkdir();
        }

        return new File(file, name);
    }
}
