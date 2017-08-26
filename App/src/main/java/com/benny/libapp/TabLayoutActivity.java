package com.benny.libapp;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class TabLayoutActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_tab_layout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorAccent));
        }

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(Gravity.TOP);

        tabLayout.addTab(tabLayout.newTab().setText("阿里"));
        tabLayout.addTab(tabLayout.newTab().setText("腾通"));
        tabLayout.addTab(tabLayout.newTab().setText("百度"));
        tabLayout.addTab(tabLayout.newTab().setText("京东"));

        viewPager.setAdapter(new CustemFragmentPagerAdapter(getSupportFragmentManager()));
    }


    public static class CustemFragmentPagerAdapter extends FragmentPagerAdapter {

        public CustemFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new CustemFragment();
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }

    }

    public static class CustemFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.dialog_test, container, false);
        }
    }
}
