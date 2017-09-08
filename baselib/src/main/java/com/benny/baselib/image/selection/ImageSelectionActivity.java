package com.benny.baselib.image.selection;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.benny.baselib.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanbb on 2017/9/8.
 */

public class ImageSelectionActivity extends AppCompatActivity {


    private static final String EXTRA_SELECT_MAX_COUNT = "SelectMaxCount";
    private static final String EXTRA_SELECTED_IMAGES = "SelectedImages";
    private static final String EXTRA_TASK_PHOTO = "TaskPhoto";


    private int mSelectMaxCount = 9;
    private List<String> mSelectImages;
    private boolean mTaskPhoto = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mSelectMaxCount = intent.getIntExtra(EXTRA_SELECT_MAX_COUNT, 9);
        mSelectImages = intent.getStringArrayListExtra(EXTRA_SELECTED_IMAGES);
        mTaskPhoto = intent.getBooleanExtra(EXTRA_TASK_PHOTO, true);
        if (mSelectImages == null) {
            mSelectImages = new ArrayList<>();
        }
    }


}
