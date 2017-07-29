package com.example.sqlitebestdemo.dao;

import com.example.sqlitebestdemo.bean.Book;

import java.util.List;

/**
 * Desc:
 * Created by huanghao123 on 7/29 0029.
 */
public class BookDao implements BaseDao<Book> {

    @Override
    public boolean addRecord(Book record) {
        return false;
    }

    @Override
    public boolean deleteRecord(Book record) {
        return false;
    }

    @Override
    public boolean updateRecord(Book record) {
        return false;
    }

    @Override
    public Book getRecord(String... conditions) {
        return null;
    }

    @Override
    public List<Book> getAllRecords() {
        return null;
    }

    @Override
    public List<Book> getRecords(String... conditions) {
        return null;
    }
}
