package com.benny.libapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.benny.baselib.orm.BaseDao;
import com.benny.baselib.orm.DaoFactory;
import com.benny.libapp.orm.UserBean;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

  /*  @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.benny.libapp", appContext.getPackageName());
    }*/

  @Test
  public void testOrm()
  {
      DaoFactory daoFactory = new DaoFactory("testORM");
      BaseDao<UserBean> entityDao = daoFactory.getEntityDao(UserBean.class);

      UserBean bean = new UserBean();
      bean.setUserName("userName");
      bean.setPassword("password");
      entityDao.insert(bean);
  }
}
