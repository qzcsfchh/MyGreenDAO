package com.hao.sqlite.table;

/**
 * Desc:
 * Created by huanghao123 on 8/6 0006.
 */
public class CategoryTable {

    public static class Columns{
        public static final String ID="_id";
        public static final String CATEGORY_NAME="category_name";
        public static final String CATEGORY_CODE="category_code";
    }

    public static final String TABLE_NAME="category";

    /** 创建名为Category的表格 */
    public static String CREATE_CATEGORY="create table "+TABLE_NAME+"("
            + Columns.ID+" integer primary key autoincrement,"
            + Columns.CATEGORY_NAME+" text,"
            + Columns.CATEGORY_CODE+" integer)";

    public static final String drop_table="drop table if exists table"+TABLE_NAME;

}
