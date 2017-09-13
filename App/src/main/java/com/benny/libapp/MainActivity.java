package com.benny.libapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.benny.baselib.skin.BaseSkinActivity;

public class MainActivity extends BaseSkinActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//       new DefaultNavigationBar.Builder(this)
//           .setRightClickListener(new OnClickListener() {
//               @Override
//               public void onClick(View view) {
//                   Toast.makeText(MainActivity.this, "右侧按钮", Toast.LENGTH_SHORT).show();
//               }
//           }).setRightTitle("右侧按钮").build();

        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        //初始化同步状态
        drawerToggle.syncState();
        // drawerLayout.addDrawerListener(drawerToggle);

       /* drawerLayout.addDrawerListener(new SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                Log.d("onDrawerSlide", "slideOffset: " + slideOffset);

                float scale = 1 - slideOffset;
                float contentScale = (float) (1 - slideOffset * 0.3);
                View contentView = drawerLayout.getChildAt(0);
                contentView.setScaleX(contentScale);
                contentView.setScaleY(contentScale);
                contentView.setTranslationX(contentView.getMeasuredWidth() * slideOffset);

                drawerView = drawerLayout.getChildAt(1);
                drawerView.setScaleX((float) (1 - 0.3 * scale));
                drawerView.setScaleY((float) (1 - 0.3 * scale));


            }
        });

         findViewById(R.id.hello_tv).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MainActivity.this).setContentView(R.layout.dialog_test)
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
*/

        ((TextView) findViewById(R.id.activity_main_ndk)).setText(new JniTest().getText());
        findViewById(R.id.activity_main_tab_layout_btn).setOnClickListener(this);
        findViewById(R.id.activity_main_fab_btn).setOnClickListener(this);
        findViewById(R.id.activity_main_fmode).setOnClickListener(this);
        findViewById(R.id.activity_main_image_selection).setOnClickListener(this);
        findViewById(R.id.activity_main_hook_activity).setOnClickListener(this);
        findViewById(R.id.activity_main_ffmpeg_activity).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.activity_main_tab_layout_btn) {
            Intent intent = new Intent(this, TabLayoutActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.activity_main_fab_btn) {
            Intent intent = new Intent(this, FabActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.activity_main_fmode) {
            Intent intent = new Intent(this, com.benny.fmod.MainActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.activity_main_image_selection) {
            Intent intent = new Intent(this, com.benny.baselib.image.selection.ImageSelectionActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.activity_main_hook_activity) {
            Intent intent = new Intent(this,TestActivity.class);
            startActivity(intent);
        }else if(view.getId() == R.id.activity_main_ffmpeg_activity){
            Intent intent = new Intent(this,com.benny.ffmpeg.FFmpegMainActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return super.onCreateOptionsMenu(menu);

    }
}
