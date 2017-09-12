package com.benny.baselib.http.request;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import com.benny.baselib.http.core.HttpMethod;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;


/**
 * Created by yuanbb on 2017/9/12.
 */

public class FormDataHttpRequest extends BaseHttpRequest {

    private String boundary = generateMultipartBoundary();

    private static char[] MULTIPART_CHARS =
        ("1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();

    private static final String CHARSET_ENCODING = "UTF-8";
    private static final String LINE_FEED = "\r\n";

    private ArrayMap<String, String> mFormData = new ArrayMap<>();
    private ArrayList<File> mFile = new ArrayList<>();


    private static String generateMultipartBoundary() {
        Random rand = new Random();
        char[] chars = new char[rand.nextInt(9) + 12]; // 随机长度(12 - 20个字符)
        for (int i = 0; i < chars.length; i++) {
            chars[i] = MULTIPART_CHARS[rand.nextInt(MULTIPART_CHARS.length)];
        }
        return "----AndroidHttpURLConnection" + new String(chars);
    }

    @NonNull
    @Override
    public String getUrl() {
        return null;

    }

    @Override
    public int getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getContentType() {
        return "multipart/form-data; boundary=" + boundary;
    }

    @Override
    public void writeOutputStream(OutputStream outputStream) throws IOException {
        DataOutputStream bufferedOutputStream = new DataOutputStream(outputStream);

        writeFormData(bufferedOutputStream);
        writeFile(bufferedOutputStream);

        bufferedOutputStream.writeBytes(LINE_FEED);
        bufferedOutputStream.writeBytes("--" + boundary + "--");
        bufferedOutputStream.writeBytes(LINE_FEED);
        bufferedOutputStream.close();
    }


    public void addFormData(String key, String value) {
        mFormData.put(key, value);
    }

    public void addFormData(File file) {
        mFile.add(file);
    }

    private void writeFormData(DataOutputStream outputStream) throws IOException {
        if (mFormData == null || mFormData.size() == 0) {
            return;
        }

        for (ArrayMap.Entry<String, String> entry : mFormData.entrySet()) {
            outputStream.writeBytes("--" + boundary);
            outputStream.writeBytes(LINE_FEED);

            outputStream.writeBytes("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"");
            outputStream.writeBytes(LINE_FEED);

            outputStream.writeBytes(LINE_FEED);
            outputStream.writeBytes(URLEncoder.encode(entry.getValue(), CHARSET_ENCODING));
            outputStream.writeBytes(LINE_FEED);
            outputStream.flush();
        }

    }


    private void writeFile(DataOutputStream outputStream) throws IOException {
        if (mFile == null || mFile.isEmpty()) {
            return;
        }

        for (int i = 0, count = mFile.size(); i < count; i++) {

            File file = mFile.get(i);

            outputStream.writeBytes("--" + boundary);
            outputStream.writeBytes(LINE_FEED);

            outputStream.writeBytes("Content-Disposition: form-data; name=\"" + "FILE" + i + "\"; filename=\"" + file.getName() + "\"");
            outputStream.writeBytes(LINE_FEED);

            outputStream.writeBytes("Content-Type: " + URLConnection.guessContentTypeFromName(file.getName()));
            outputStream.writeBytes(LINE_FEED);
            outputStream.writeBytes(LINE_FEED);

            InputStream iStream = null;
            try {
                iStream = new FileInputStream(file);
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = iStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                iStream.close();
                outputStream.writeBytes(LINE_FEED);
                outputStream.flush();
            } catch (IOException ignored) {
            } finally {
                try {
                    if (iStream != null) {
                        iStream.close();
                    }
                } catch (Exception ignored) {
                }
            }

        }


    }

}
