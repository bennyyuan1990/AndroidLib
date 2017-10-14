package com.benny.baselib.image.easyloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import com.benny.baselib.image.easyloader.request.BitmapRequest;
import com.benny.baselib.image.easyloader.utils.BitmapDecoder;
import com.benny.baselib.image.easyloader.utils.ImageViewHelper;
import java.io.File;

/**
 * Created by Benny on 2017/10/4.
 */

public class LocalLoader extends AbsBaseLoader {

    @Override
    protected Bitmap onLoadBitmap(BitmapRequest request) {

        final String path = Uri.parse(request.getUrl()).getPath();
        File file = new File(path);

        if (file.exists()) {

            BitmapDecoder bitmapDecoder = new BitmapDecoder() {
                @Override
                protected Bitmap decodeBitmapWithOption(Options options) {
                    return BitmapFactory.decodeFile(path, options);
                }
            };
            return bitmapDecoder.decodeBitmap(ImageViewHelper.getImageViewWidth(request.getImageView()), ImageViewHelper.getImageViewHeight(request.getImageView()));
        }

        return null;
    }
}
