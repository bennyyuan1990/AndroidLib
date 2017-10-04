package com.benny.baselib.image.easyloader.policy;

import com.benny.baselib.image.easyloader.request.BitmapRequest;

/**
 * Created by Benny on 2017/10/4.
 */

public interface LoadPolicy {
    int compareto(BitmapRequest request1, BitmapRequest request2);
}
