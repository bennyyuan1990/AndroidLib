package com.benny.baselib.orm;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import com.benny.baselib.orm.annotation.DbColumn;
import com.benny.baselib.orm.annotation.DbTable;
import com.benny.baselib.orm.utils.DbTableHelper;
import java.lang.reflect.Field;
import java.util.Iterator;

/**
 * Created by yuanbb on 2017/9/11.
 */

public class BaseDao<T> implements IBaseDao<T> {

    private static final String TAG = "BaseDao";

    private SQLiteDatabase mSQLiteDatabase;
    private Class<T> mEntityClass;
    private String mDbName;
    private ArrayMap<String, Field> mColumnMap;


    protected void init(@NonNull Class<T> entityClass, @NonNull SQLiteDatabase database) {
        mSQLiteDatabase = database;
        mEntityClass = entityClass;
        mColumnMap = new ArrayMap<>();
        initTableName();
        initColumnMap();
        createTable();
    }

    /**
     * 初始化获取表名
     */
    private void initTableName() {
        DbTable annotation = mEntityClass.getAnnotation(DbTable.class);
        if (annotation != null) {
            mDbName = TextUtils.isEmpty(annotation.value()) ? mEntityClass.getSimpleName() : annotation.value();
        } else {
            mDbName = mEntityClass.getSimpleName();
        }
    }

    /**
     * 初始化获取列名与对象字段的映射
     */
    private void initColumnMap() {
        if (mColumnMap == null) {
            mColumnMap = new ArrayMap<>();
        }
        Field[] fields = mEntityClass.getDeclaredFields();
        DbColumn annotation;
        String columnName;
        Field field;
        for (int i = 0, count = fields.length; i < count; i++) {
            field = fields[i];
            field.setAccessible(true);
            annotation = field.getAnnotation(DbColumn.class);

            if (annotation != null) {
                columnName = TextUtils.isEmpty(annotation.value()) ? field.getName() : annotation.value();
            } else {
                columnName = field.getName();
            }
            mColumnMap.put(columnName, field);
        }
    }

    /**
     * 初始化创建数据库表
     */
    private void createTable() {
      /*  create table if not exists MessageLogTable_2 (ID integer
            primary key autoincrement not null,
            tojid varchar(200),fromjid varchar(200),
            timestamp timestamp,remark,data text,type int,body text)*/

        StringBuffer sql = new StringBuffer();
        String fieldName;
        String fieldType;

        Iterator<String> iterator = mColumnMap.keySet().iterator();
        while (iterator.hasNext()) {
            fieldName = iterator.next();
            fieldType = DbTableHelper.getDbType(mColumnMap.get(fieldName));
            if (TextUtils.isEmpty(fieldType)) {
                continue;
            }

            sql.append(String.format(",%s %s ", fieldName, fieldType));
        }
        String createTableSql = String.format("CREATE TABLE IF NOT EXISTS %s (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL %s)", mDbName, sql.toString());

        if (mSQLiteDatabase.isOpen()) {
            mSQLiteDatabase.execSQL(createTableSql);
        }

    }


    @Override
    public boolean insert(T value) {
        if (value == null) {
            return false;
        }
        try {
            StringBuilder columns = new StringBuilder();
            StringBuilder values = new StringBuilder();
            Iterator<String> iterator = mColumnMap.keySet().iterator();
            String fieldName;
            String fieldValue;
            Field field;
            while (iterator.hasNext()) {
                fieldName = iterator.next();
                field = mColumnMap.get(fieldName);

                if (field.get(value) == null) {
                    continue;
                }

                columns.append("," + fieldName);
                fieldValue = field.get(value).toString();

                values.append(String.format(",'%s'", fieldValue));
            }
            if(columns.length()==0) return false;


            String insertSql = String.format("INSERT INTO %s (%s) VALUES(%s)", mDbName, columns.toString().substring(1),values.toString().substring(1));

            if (mSQLiteDatabase.isOpen()) {
                mSQLiteDatabase.execSQL(insertSql);
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(T value) {
        return false;
    }

    @Override
    public boolean update(T value, T where) {
        return false;
    }
}
