package com.hao.sqlite.sqlitehelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hao.sqlite.table.BookTable;
import com.hao.sqlite.table.CategoryTable;

import static com.hao.sqlite.table.CategoryTable.CREATE_CATEGORY;

/**
 * 数据库级操作
 */
public class DBOpenHelper extends SQLiteOpenHelper {
	private static final String TAG = "DBOpenHelper";

	public static final int DB_VERSION_1 =1;

	public static final int CURR_VERSION= DB_VERSION_1;

	public static final String DB_NAME="datas";

	public DBOpenHelper(Context context) {
		super(context, DB_NAME, null, CURR_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {//只在创建数据库文件的时候调用
		db.execSQL(BookTable.CREATE_BOOK);
		db.execSQL(CategoryTable.CREATE_CATEGORY);
	}
	/**
	 * 升级数据库的最佳写法：根据旧版本数据库的版本号来进行累积操作，注意switch中不能用break，否则不能累积
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {    //版本号改变时运行该方法
		//db.execSQL("drop table if exists Book");
		//onCreate(db);
		switch(oldVersion){
		case 1:
			db.execSQL(CREATE_CATEGORY);   
		case 2:
			db.execSQL("alter table book add column category_id integer");   //更改Book表的表结构，增加一条字段，integer型
		default:
		}
	}

}
