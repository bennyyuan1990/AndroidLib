package com.benny.libapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v4.widget.DrawerLayout.SimpleDrawerListener;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.benny.baselib.dialog.AlertDialog;
import com.benny.baselib.view.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

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
*/
        findViewById(R.id.activity_main_tab_layout_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.activity_main_tab_layout_btn) {
            Intent intent = new Intent(this, TabLayoutActivity.class);
            startActivity(intent);
        }
    }
}
