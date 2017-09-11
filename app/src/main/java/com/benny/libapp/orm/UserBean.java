package com.benny.libapp.orm;

import com.benny.baselib.orm.annotation.DbColumn;
import com.benny.baselib.orm.annotation.DbTable;
import java.util.Date;

/**
 * Created by yuanbb on 2017/9/11.
 */

@DbTable("T_USER")
public class UserBean {

    @DbColumn("USERNAME")
    private String userName;

    @DbColumn("PASSWORD")
    private String password;


    private int age;
    private float money;
    private boolean isGirl;
    private Date date;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
