package com.benny.baselib.orm.utils;

import com.benny.baselib.orm.annotation.DbColumn;
import java.lang.reflect.Field;

/**
 * Created by yuanbb on 2017/9/11.
 */

public class DbTableHelper {


    public static String getDbType(Field field) {
        if (field == null) {
            return null;
        }

        DbColumn annotation = field.getAnnotation(DbColumn.class);
        int length = 100;
        if (annotation != null && annotation.length() > 0) {
            length = annotation.length();
        }
        Class<?> type = field.getType();
        String dataType = type.getSimpleName();

        if("String".equals(dataType)){
            return String.format("varchar(%s)",length);
        }else if("int".equals(dataType)) {
            return "interger";
        }else if("float".equals(dataType)){
            return "float";
        }else if("double".equals(dataType)){
            return "double";
        }else if("long".equals(dataType)){
            return "interger";
        }else if("boolean".equals(dataType)){
            return "char(5)";
        }else if("char".equals(dataType)){
            return "char(1)";
        }else if("Date".equals(dataType)){
            return "timestamp";
        }

        return null;
    }


}
