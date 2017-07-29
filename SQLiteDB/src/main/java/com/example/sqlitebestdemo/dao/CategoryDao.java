package com.example.sqlitebestdemo.dao;

import com.example.sqlitebestdemo.bean.Category;

import java.util.List;

/**
 * Desc:
 * Created by huanghao123 on 7/29 0029.
 */
public class CategoryDao implements BaseDao<Category> {
    @Override
    public boolean addRecord(Category record) {
        return false;
    }

    @Override
    public boolean deleteRecord(Category record) {
        return false;
    }

    @Override
    public boolean updateRecord(Category record) {
        return false;
    }

    @Override
    public Category getRecord(String... conditions) {
        return null;
    }

    @Override
    public List<Category> getAllRecords() {
        return null;
    }

    @Override
    public List<Category> getRecords(String... conditions) {
        return null;
    }
}
