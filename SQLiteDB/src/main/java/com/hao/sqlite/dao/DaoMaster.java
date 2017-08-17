package com.hao.sqlite.dao;

import android.content.Context;

import com.hao.sqlite.dao.impl.BookDao;
import com.hao.sqlite.dao.impl.CategoryDao;
import com.hao.sqlite.sqlitehelper.DBOpenHelper;

/**
 * Desc:用于同一管理所有的dao类
 * Created by huanghao123 on 8/6 0006.
 */
public class DaoMaster {
    private DBOpenHelper helper;
    private BookDao bookDao;
    private CategoryDao categoryDao;

    private static DaoMaster instance;
    private DaoMaster(Context context){
        helper=new DBOpenHelper(context);
        bookDao=new BookDao(context);
        categoryDao=new CategoryDao(context);
    }

    public static DaoMaster getInstance(Context context){
        if (instance==null) {
            synchronized (DaoMaster.class){
                if (instance==null) {
                    instance=new DaoMaster(context);
                }
            }
        }
        return instance;
    }

    public BookDao getBookDao(){
        return bookDao;
    }

    public CategoryDao getCategoryDao(){
        return categoryDao;
    }

}
