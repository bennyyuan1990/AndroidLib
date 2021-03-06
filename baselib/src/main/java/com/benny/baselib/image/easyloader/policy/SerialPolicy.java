package com.benny.baselib.image.easyloader.policy;

import com.benny.baselib.image.easyloader.request.BitmapRequest;

/**
 * Created by Benny on 2017/10/4.
 * <p>
 * 正序加载策略
 */

public class SerialPolicy implements LoadPolicy {
    @Override
    public int compareTo(BitmapRequest request1, BitmapRequest request2) {
        return request1.getSerialNo() - request2.getSerialNo();
    }
}
