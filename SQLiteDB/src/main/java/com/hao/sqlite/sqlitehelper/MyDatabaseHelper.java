package com.hao.sqlite.sqlitehelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 数据库级操作
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {
	private static final String TAG = "MyDatabaseHelper";
	/** 创建名为Book的表格 */
	public static String CREATE_BOOK="create table if not exists book("
			+ "id integer primary key autoincrement,"
			+ "name text,"
			+ "author text,"
			+ "price real,"
			+ "pages integer)";

	/** 创建名为Category的表格 */
	public static String CREATE_CATEGORY="create table category("
			+ "id integer primary key autoincrement,"
			+ "category_name text,"
			+ "category_code integer)";

	public Context mContext;

	/**
	 *
	 * @param context
	 * @param name 数据库名
	 * @param factory
	 * @param version 数据库版本号
	 */
	public MyDatabaseHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		mContext=context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {//只在创建数据库文件的时候调用
		db.execSQL(CREATE_BOOK);
		db.execSQL(CREATE_CATEGORY);
		Log.d(TAG, "onCreate: ");
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
