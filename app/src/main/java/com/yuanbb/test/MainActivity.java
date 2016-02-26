package com.yuanbb.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yuanbb.common.adapter.MyBaseAdapter;
import com.yuanbb.common.widget.ProgressButton;
import com.yuanbb.test.activity.CustomViewActivity;
import com.yuanbb.test.activity.GridViewImageActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mLvItems;

    private MyBaseAdapter mBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        List list = new ArrayList();
        list.add(new MenuItemEntity("GridView图片缩放", GridViewImageActivity.class));
        list.add(new MenuItemEntity("自定义界面", CustomViewActivity.class));

        mBaseAdapter = new MyBaseAdapter<MenuItemEntity>(this, list, android.R.layout.simple_expandable_list_item_1) {
            @Override
            public void setViewValue(MenuItemEntity obj, ViewHodler viewHodler) {
                ((TextView) viewHodler.getView(android.R.id.text1)).setText(obj.mContent);
            }
        };

        mLvItems = (ListView) findViewById(R.id.lv_Items);

        mLvItems.setAdapter(mBaseAdapter);

        mLvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Class clazz = ((MenuItemEntity) mBaseAdapter.getItem(position)).mClass;
                Intent intent = new Intent(MainActivity.this, clazz);
                startActivity(intent);
            }
        });

        /*
        final ProgressButton btn = (ProgressButton) findViewById(R.id.btn_Progress);
        btn.setOnProgressButtonClickListener(new ProgressButton.OnProgressButtonClickListener() {
            @Override
            public void onClickListener() {
                new Thread() {
                    @Override
                    public void run() {

                        while (btn.getProgress() < btn.getMax()) {
                            try {
                                btn.setProgress(btn.getProgress() + 5);

                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }.start();
            }
        });
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class MenuItemEntity {
        public MenuItemEntity() {
        }

        public MenuItemEntity(String content, Class clazz) {
            mContent = content;
            mClass = clazz;
        }

        private String mContent;
        private Class mClass;
    }


}
