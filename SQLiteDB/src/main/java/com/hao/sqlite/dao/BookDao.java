package com.hao.sqlite.dao;

import android.content.Context;

import com.hao.sqlite.bean.Book;
import com.hao.sqlite.sqlitehelper.DBOpenHelper;

import java.util.List;

/**
 * Desc:
 * Created by huanghao123 on 7/29 0029.
 */
public class BookDao implements BaseDao<Book> {
    private DBOpenHelper helper;

    public BookDao(Context context){
        helper=new DBOpenHelper(context);
    }

    @Override
    public boolean insert(Book record) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public boolean update(Book record) {
        return false;
    }

    @Override
    public Book query(String condition) {
        return null;
    }

    @Override
    public List<Book> queryAll() {
        return null;
    }

    @Override
    public List<Book> query(String... conditions) {
        return null;
    }
}
