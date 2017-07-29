package com.example.sqlitebestdemo.dao;

import java.util.List;

/**
 * Desc:
 * Created by huanghao123 on 7/29 0029.
 */
public interface BaseDao<T> {
    boolean addRecord(T record);
    boolean deleteRecord(T record);
    boolean updateRecord(T record);
    T getRecord(String... conditions);
    List<T> getAllRecords();
    List<T> getRecords(String... conditions);
}
