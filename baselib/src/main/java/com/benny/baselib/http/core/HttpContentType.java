package com.benny.baselib.http.core;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import java.io.File;

/**
 * Created by yuanbb on 2017/9/12.
 */

public class HttpContentType {

    private static ArrayMap<String, String> mContentType = new ArrayMap<>();

    public static final String UNKOWN_CONTENT_TYPE = "application/octet-stream";

    static {
        mContentType.put("bmp", "application/x-bmp");
        mContentType.put("html", "text/html");
        mContentType.put("jpe", "image/jpeg");
        mContentType.put("jpeg", "image/jpeg");
        mContentType.put("jpg", "image/jpeg");
        mContentType.put("mp4", "video/mpeg4");
        mContentType.put("pdf", "application/pdf");
        mContentType.put("png", "application/x-png");
        mContentType.put("ppt", "application/x-ppt");
        mContentType.put("tif", "image/tiff");
        mContentType.put("tiff", "image/tiff");
        mContentType.put("xhtml", "text/html");
        mContentType.put("xls", "application/x-xls");
        mContentType.put("xml", "text/xml");
        mContentType.put("htm", "text/html");
        mContentType.put("svg", "text/xml");
    }

    public static String getContentType(File file) {
        String name = file.getName();
        if (TextUtils.isEmpty(name)) {
            return "";
        }

        if (name.lastIndexOf(".") > 0) {
            String extension = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
            if (mContentType.containsKey(extension)) {
                return mContentType.get(extension);
            } else {
                return UNKOWN_CONTENT_TYPE;
            }
        }
        return "";
    }


}
