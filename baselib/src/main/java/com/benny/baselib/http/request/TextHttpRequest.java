package com.benny.baselib.http.request;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.benny.baselib.http.core.HttpMethod;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by yuanbb on 2017/9/12.
 */

public class TextHttpRequest extends BaseHttpRequest {

    protected String mBodyText;

    @Override
    public int getHttpMethod() {
        return HttpMethod.POST;
    }

    @NonNull
    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public String getContentType() {
        return "text/plain";
    }

    @Override
    public void writeOutputStream(OutputStream outputStream) throws IOException {
        if (TextUtils.isEmpty(mBodyText)) {
            return;
        }

        DataOutputStream bufferedOutputStream = new DataOutputStream(outputStream);
        bufferedOutputStream.writeBytes(mBodyText);
        bufferedOutputStream.close();
    }

    public void setText(String body) {
        mBodyText = body;
    }
}
