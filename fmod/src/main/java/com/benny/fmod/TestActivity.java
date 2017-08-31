package com.benny.fmod;

import android.inputmethodservice.Keyboard.Key;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

public class TestActivity extends AppCompatActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        FmodVoice.init(this);

        findViewById(R.id.activity_test_normal_btn).setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        FmodVoice.close();
    }

    @Override
    public void onClick(View view) {
        String fileName = "file:///android_asset/pianpianxihuanni.mp3";
        if (view.getId() == R.id.activity_test_normal_btn) {
            FmodVoice.PlayVoice(fileName, FmodVoice.FMOD_VOICE_TYPE_LUOLI);
        }
    }


    @Override
    public void onBackPressed() {

        FmodVoice.Stop();
        FmodVoice.close();
        super.onBackPressed();
    }
}
