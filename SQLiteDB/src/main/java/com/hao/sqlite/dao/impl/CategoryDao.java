package com.hao.sqlite.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.hao.sqlite.bean.Category;
import com.hao.sqlite.dao.BaseDao;
import com.hao.sqlite.sqlitehelper.DBOpenHelper;
import com.hao.sqlite.table.CategoryTable;

import java.util.List;

/**
 * Desc:
 * Created by huanghao123 on 7/29 0029.
 */
public class CategoryDao implements BaseDao<Category> {
    private DBOpenHelper helper;

    public CategoryDao(Context context){
        helper=new DBOpenHelper(context);
    }

    @Override
    public boolean insert(Category record) {
        SQLiteDatabase db = helper.getWritableDatabase();
        long insert = insert1(record, db);
//        boolean res = insert2(record, db);
//        boolean res2= insert3(record,db);
        db.close();
        return insert!=-1;
    }

    private boolean insert3(Category record, SQLiteDatabase db) {
        String insert_sql="insert into "+CategoryTable.TABLE_NAME+" (" +
                CategoryTable.Columns.CATEGORY_NAME+","+CategoryTable.Columns.CATEGORY_CODE+") values(" +
                record.getCategoryName()+","+record.getCategoryCode()+")";
        try {
            db.execSQL(insert_sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean insert2(Category record, SQLiteDatabase db) {
        String insert_sql="insert into table "+CategoryTable.TABLE_NAME+" (" +
                CategoryTable.Columns.CATEGORY_NAME+","+CategoryTable.Columns.CATEGORY_CODE+") values(?,?)";
        String[] args={record.getCategoryName(),String.valueOf(record.getCategoryCode())};
        try {
            db.execSQL(insert_sql,args);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private long insert1(Category record, SQLiteDatabase db) {
        return db.insert(CategoryTable.TABLE_NAME, null, getValuesFromCategory(record));
    }

    private ContentValues getValuesFromCategory(Category record) {
        ContentValues values=new ContentValues();
        if (record.getId()!=0) {
            values.put(CategoryTable.Columns.ID,record.getId());
        }
        values.put(CategoryTable.Columns.CATEGORY_NAME,record.getCategoryName());
        values.put(CategoryTable.Columns.CATEGORY_CODE,record.getCategoryCode());
        return values;
    }

    @Override
    public boolean delete(String id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(CategoryTable.TABLE_NAME,CategoryTable.Columns.ID+"=?",new String[]{id});
        db.close();
        return false;
    }

    @Override
    public boolean update(Category record) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int delete = db.delete(CategoryTable.TABLE_NAME, CategoryTable.Columns.ID + "=?", new String[]{String.valueOf(record.getId())});
        db.close();
        return delete!=-1;
    }

    @Override
    public Category query(String id) {
        Category category=null;
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query(CategoryTable.TABLE_NAME, null, CategoryTable.Columns.ID + "=?", new String[]{id}, null, null, null);
        if (cursor.moveToFirst()) {
            category=new Category();
            category.setId(cursor.getInt(cursor.getColumnIndex(CategoryTable.Columns.ID)));
            category.setCategoryName(cursor.getString(cursor.getColumnIndex(CategoryTable.Columns.CATEGORY_NAME)));
            category.setCategoryCode(cursor.getColumnIndex(CategoryTable.Columns.CATEGORY_CODE));
        }
        db.close();
        return category;
    }

    @Override
    public List<Category> queryAll() {
        List<Category> categories=null;
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query(CategoryTable.TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Category category=new Category();
            category.setId(cursor.getInt(cursor.getColumnIndex(CategoryTable.Columns.ID)));
            category.setCategoryName(cursor.getString(cursor.getColumnIndex(CategoryTable.Columns.CATEGORY_NAME)));
            category.setCategoryCode(cursor.getColumnIndex(CategoryTable.Columns.CATEGORY_CODE));
            categories.add(category);
        }
        db.close();
        return categories;
    }

    @Override
    public List<Category> query(String... conditions) {

        return null;
    }

    public void closeHelper(){
        this.helper.close();
    }
}
