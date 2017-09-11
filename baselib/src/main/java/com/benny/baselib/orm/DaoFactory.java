package com.benny.baselib.orm;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import java.io.File;

/**
 * Created by yuanbb on 2017/9/11.
 */

public class DaoFactory {

    private String mDbPath;
    private SQLiteDatabase mSQLiteDatabase;


    public DaoFactory(String dbName) {
        File file = new File(Environment.getExternalStorageDirectory(), "orm" + File.separator);
        if (!file.exists()) {
            file.mkdir();
        }
        mDbPath = file.getAbsolutePath() + dbName + ".db";
        mSQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(mDbPath, null);
    }

    public synchronized <T extends BaseDao<M>, M> T getEntityDao(Class<T> daoClass, Class<M> entityClass) {
        BaseDao<M> baseDao = null;
        try {
            if (daoClass == null) {
                baseDao = BaseDao.class.newInstance();
            } else {
                baseDao = daoClass.newInstance();
            }
            baseDao.init(entityClass, mSQLiteDatabase);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) baseDao;
    }

    public synchronized <T extends BaseDao<M>, M> T getEntityDao(Class<M> entityClass) {
        BaseDao<M> entityDao = getEntityDao(null,entityClass);
        if (entityDao != null) {
            return (T) entityDao;
        } else {
            return null;
        }
    }

}
