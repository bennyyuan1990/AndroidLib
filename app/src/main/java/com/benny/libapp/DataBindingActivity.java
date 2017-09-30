package com.benny.libapp;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.benny.libapp.databinding.ActivityDataBindingBinding;
import com.benny.libapp.orm.UserBean;

public class DataBindingActivity extends AppCompatActivity {

    private ActivityDataBindingBinding mActivityDataBindingBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_data_binding);

        mActivityDataBindingBinding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        mActivityDataBindingBinding.setUser(new UserBean());
        mActivityDataBindingBinding.setHandler(new DataBindingHandler());

        findViewById(R.id.activity_data_binding_update_btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                UserBean userBean = new UserBean();
                userBean.setUserName("username");
                userBean.setPassword("password");
                mActivityDataBindingBinding.setUser(userBean);
            }
        });
    }
}
