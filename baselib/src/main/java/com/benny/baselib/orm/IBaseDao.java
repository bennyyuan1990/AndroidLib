package com.benny.baselib.orm;

/**
 * Created by yuanbb on 2017/9/11.
 */

public interface IBaseDao<T> {

    public boolean insert(T value);

    public boolean delete(T value);

    public boolean update(T value, T where);

}
