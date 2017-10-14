package com.benny.baselib.image.easyloader.loader;

import android.graphics.Bitmap;
import com.benny.baselib.image.easyloader.request.BitmapRequest;

/**
 * Created by yuanbb on 2017/10/10.
 */

public class NullLoader extends AbsBaseLoader {

    @Override
    protected Bitmap onLoadBitmap(BitmapRequest request) {
        return null;
    }
}
