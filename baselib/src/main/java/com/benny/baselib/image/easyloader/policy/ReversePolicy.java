package com.benny.baselib.image.easyloader.policy;

import com.benny.baselib.image.easyloader.request.BitmapRequest;

/**
 * Created by Benny on 2017/10/4.
 * <p>
 * 反序加载策略
 */

public class ReversePolicy implements LoadPolicy {
    @Override
    public int compareto(BitmapRequest request1, BitmapRequest request2) {
        return request2.getSerialNo() - request1.getSerialNo();
    }
}
