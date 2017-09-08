package com.benny.baselib.image.selection;

import android.Manifest;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;
import com.benny.baselib.R;
import com.benny.baselib.image.selection.ImageSelectionAdapter.ImageSelectChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanbb on 2017/9/8.
 */

public class ImageSelectionActivity extends AppCompatActivity {


    private static final String EXTRA_SELECT_MAX_COUNT = "SelectMaxCount";
    private static final String EXTRA_SELECTED_IMAGES = "SelectedImages";
    private static final String EXTRA_TASK_PHOTO = "TaskPhoto";
    private static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    private static String[] IMAGE_PROJECTION = {
        Media.DATA,
        Media.DISPLAY_NAME,
        Media.DATE_ADDED,
        Media.MIME_TYPE,
        Media.SIZE,
        Media._ID
    };


    private int mSelectMaxCount = 9;
    private List<String> mSelectImages;
    private boolean mTaskPhoto = true;
    private RecyclerView mImagesRcv;
    private TextView mIndicatorTv;
    private ImageSelectionAdapter mImageSelectionAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mIndicatorTv = (TextView) findViewById(R.id.activity_image_selection_indicator_tv);

        Intent intent = getIntent();
        mSelectMaxCount = intent.getIntExtra(EXTRA_SELECT_MAX_COUNT, 9);
        mSelectImages = intent.getStringArrayListExtra(EXTRA_SELECTED_IMAGES);
        mTaskPhoto = intent.getBooleanExtra(EXTRA_TASK_PHOTO, true);
        if (mSelectImages == null) {
            mSelectImages = new ArrayList<>();
        }

        mImagesRcv = (RecyclerView) findViewById(R.id.activity_image_selection_images_rcv);
        mImagesRcv.setLayoutManager(new GridLayoutManager(this, 4));
        mImagesRcv.setAdapter(mImageSelectionAdapter = new ImageSelectionAdapter(null, mTaskPhoto));
        mImageSelectionAdapter.setSelectMaxCount(mSelectMaxCount);
        mImageSelectionAdapter.setTaskPhoto(mTaskPhoto);
        mImageSelectionAdapter.setImageSelectChangeListener(new ImageSelectChangeListener() {
            @Override
            public void onSelectChanged(List<ImageSelectionBean> data) {
                int count = data == null ? 0 : data.size();
                mIndicatorTv.setText(count + " / " + mSelectMaxCount);
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 用户已决绝授权，可以弹出对话框解释为何需要此权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(this, "读取图片列表，需要您授权读取拓展存储", Toast.LENGTH_LONG).show();
            } else {
                //申请权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

            }
        } else {
            requestImageLoader();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //申请通过
                //加载图片
                requestImageLoader();
            } else {
                //申请拒绝

                Toast.makeText(this, "授权拒绝，无法继续", Toast.LENGTH_LONG).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void requestImageLoader() {
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(0, null, mLoaderCallBack);
    }

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallBack = new LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {

            CursorLoader cursorLoader = new CursorLoader(ImageSelectionActivity.this,
                Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION, null
                /*IMAGE_PROJECTION[4] + ">0 AND "+IMAGE_PROJECTION[3] + "=? OR " + IMAGE_PROJECTION[3] + "=? ",
                new String[]{"image/jpeg,image/png"}*/, null, IMAGE_PROJECTION[2] + " DESC");
            return cursorLoader;

        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if (data != null && data.getCount() > 0) {
                ArrayList<ImageSelectionBean> images = new ArrayList<>();
                String path = null, name = null;
                long dateTime;
                while (data.moveToNext()) {
                    path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                    name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                    dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));

                    if (!(new File(path).exists())) {
                        continue;
                    }

                    images.add(new ImageSelectionBean(path, dateTime));
                }

                mImageSelectionAdapter.updateImage(images);
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };


}
