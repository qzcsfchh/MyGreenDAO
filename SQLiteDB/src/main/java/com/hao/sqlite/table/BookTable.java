package com.hao.sqlite.table;

/**
 * Desc:
 * Created by huanghao123 on 8/6 0006.
 */
public class BookTable {

    public static class Columns{
        public static final String ID="_id";
        public static final String NAME="name";
        public static final String AUTHOR="author";
        public static final String PRICE="price";
        public static final String PAGES="pages";
    }

    public static final String TABLE_NAME ="book";

    /** 创建名为Book的表格 */
    public static final String CREATE_BOOK="create table if not exists "+ TABLE_NAME +"("
            + Columns.ID+" integer primary key autoincrement,"
            + Columns.NAME+" text,"
            + Columns.AUTHOR+" text,"
            + Columns.PRICE+" real,"
            + Columns.PAGES+" integer)";

    public static final String DROP_TABLE="drop table if exists "+TABLE_NAME;

}
