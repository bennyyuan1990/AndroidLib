package com.benny.libapp;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.benny.baselib.dialog.AlertDialog;
import com.benny.baselib.view.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.hello_tv).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MainActivity.this).setContentView(R.layout.dialog_text)
                    .setOnClickListener(R.id.dialog_ok_btn, new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .fullWidth()
                    .fromBottom(true)
                    .setText(R.id.dialog_tv, "内容").show();
            }
        });
    }
}
