package com.hao.sqlite.dao.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hao.sqlite.bean.Book;
import com.hao.sqlite.dao.BaseDao;
import com.hao.sqlite.sqlitehelper.DBOpenHelper;
import com.hao.sqlite.table.BookTable;

import java.util.ArrayList;
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
    public List<Book> queryBatch(int offset, int limit) {
        String sql="select * from "+BookTable.TABLE_NAME+" order by "+BookTable.Columns.ID+" limit ? offset ?";
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(limit), String.valueOf(offset)});
        List<Book> res=new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
            Book book=new Book();
            book.setId(cursor.getInt(cursor.getColumnIndex(BookTable.Columns.ID)));
            book.setAuthor(cursor.getString(cursor.getColumnIndex(BookTable.Columns.AUTHOR)));
            book.setName(cursor.getString(cursor.getColumnIndex(BookTable.Columns.NAME)));
            book.setPrice(cursor.getFloat(cursor.getColumnIndex(BookTable.Columns.PRICE)));
            book.setPages(cursor.getInt(cursor.getColumnIndex(BookTable.Columns.PAGES)));
            res.add(book);
        }
        cursor.close();
        db.close();
        return res;
    }

    @Override
    public int getTotalCount() {
        String sql="select count(*) from "+ BookTable.TABLE_NAME;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToNext();
        int res = cursor.getInt(0);
        cursor.close();
        db.close();
        return res;
    }

    @Override
    public List<Book> query(String... conditions) {
        return null;
    }
}
