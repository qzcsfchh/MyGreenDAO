package com.hao.sqlite.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.hao.sqlite.sqlitehelper.MyDatabaseHelper;

public class MyProvider extends ContentProvider {
	public static final int BOOK_DIR=0;
	public static final int BOOK_ITEM=1;
	public static final int CATEGORY_DIR=2;
	public static final int CATEGORY_ITEM=3;
	
	public static final String AUTHORITY="com.example.sqlitebestdemo.provider";
	
	private static UriMatcher uriMatcher;
	
	private MyDatabaseHelper mHelper;
	
	/**
	 * 初始化这个自定义内容提供器
	 * 用于将数字和相应的表/表中的数据进行匹配绑定
	 * #代表一个字符，*代表任意个字符
	 */
	static{
		uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);   
		uriMatcher.addURI(AUTHORITY, "book", BOOK_DIR);   //将表头book与BOOK_DIR相匹配，这样match方法根据传入的内容uri解析后如果表头是book则返回BOOK_DIR
		uriMatcher.addURI(AUTHORITY, "book/#", BOOK_ITEM);
		uriMatcher.addURI(AUTHORITY, "category", CATEGORY_DIR);
		uriMatcher.addURI(AUTHORITY, "category/#", CATEGORY_ITEM);
	}
	/**
	 * 初始化内容的时候调用，只有ContentResolver尝试访问程序中的数据时，内容提供器才会被初始化
	 * 在这里完成对数据库的创建和升级等操作，返回true表示内容提供器初始化成功；否则失败。
	 */
	@Override
	public boolean onCreate() {
		mHelper=new MyDatabaseHelper(getContext(), "bookstore.db", null, 3);
		return true;
	}
	/**
	 * 从内容提供器中查询数据
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db=mHelper.getReadableDatabase();
		Cursor cursor=null;
		
		switch(uriMatcher.match(uri)){   //uriMatcher.match(uri)方法根据传入的内容uri返回返回这个uri对应的自定义代码
		case BOOK_DIR://查询book表中的所有数据
			cursor=db.query("Book", projection, selection, selectionArgs, null, null, sortOrder);
			break;
		case BOOK_ITEM://查询table1表中的单条数据
			String bookId=uri.getPathSegments().get(1);   //抽取uri的第二部分，包含id信息；第一部分是路径
			cursor=db.query("book", projection, "id=?", new String[]{bookId}, null, null, sortOrder);
			break;
		case CATEGORY_DIR://查询table2表中的所有数据
			cursor=db.query("Category", projection, selection, selectionArgs, null, null, sortOrder);
			break;
		case CATEGORY_ITEM://查询table2表中的单条数据
			String categoryId=uri.getPathSegments().get(1);
			cursor=db.query("Category", projection, "id=?", new String[]{categoryId}, null, null, sortOrder);
			break;
		default:
		}
		
		return cursor;
	}
	/**
	 * 根据传入的内容uri来返回相应的MIME类型
	 */
	@Override
	public String getType(Uri uri) {
		String dirPrefix="vnd.android.cursor.dir/";
		String itemPrefix="vnd.android.cursor.item/";
		
		switch(uriMatcher.match(uri)){
		case BOOK_DIR:
			return dirPrefix+AUTHORITY+".book";
		case BOOK_ITEM:
			return itemPrefix+AUTHORITY+".book";
		case CATEGORY_DIR:
			return dirPrefix+AUTHORITY+".category";
		case CATEGORY_ITEM:
			return itemPrefix+AUTHORITY+".category";
		default:
			return null;
		}
	}
	/**
	 * 向内容提供器中插入数据
	 */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db=mHelper.getReadableDatabase();
		Uri uriReturn=null;
		switch(uriMatcher.match(uri)){
		case BOOK_DIR:
		case BOOK_ITEM:
			long newBookId=db.insert("Book", null, values);
			uriReturn=Uri.parse("content://"+AUTHORITY+"/book/"+newBookId);
			break;
		case CATEGORY_DIR:
		case CATEGORY_ITEM:
			long newCategoryId=db.insert("Category", null, values);
			uriReturn=Uri.parse("content://"+AUTHORITY+"/category/"+newCategoryId);
			break;
		default:
		}
		return uriReturn;
	}
	/**
	 * 从内容提供器中删除数据
	 */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db=mHelper.getReadableDatabase();
		int deleteRows=0;
		switch(uriMatcher.match(uri)){
		case BOOK_DIR:
			deleteRows=db.delete("Book", selection, selectionArgs);
			break;
		case BOOK_ITEM:
			String newBookId=uri.getPathSegments().get(1);
			deleteRows=db.delete("Book", "id=?", new String[]{newBookId});
			break;
		case CATEGORY_DIR:
			deleteRows=db.delete("Category", selection, selectionArgs);
			break;
		case CATEGORY_ITEM:
			String newCategoryId=uri.getPathSegments().get(1);
			deleteRows=db.delete("Category", "id=?", new String[]{newCategoryId});
			break;
		default:
		}
		return deleteRows;
	}
	/**
	 * 更新内容提供器中的数据
	 */
	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		SQLiteDatabase db=mHelper.getReadableDatabase();
		int updateRows=0;
		switch(uriMatcher.match(uri)){
		case BOOK_DIR:
			updateRows=db.update("Book", values, selection, selectionArgs);
			break;
		case BOOK_ITEM:
			String bookId=uri.getPathSegments().get(1);
			updateRows=db.update("Book", values, "id=?", new String[]{bookId});
			break;
		case CATEGORY_DIR:
			updateRows=db.update("Category", values, selection, selectionArgs);
			break;
		case CATEGORY_ITEM:
			String newCategoryId=uri.getPathSegments().get(1);
			updateRows=db.update("Category", values, "id=?", new String[]{newCategoryId});
			break;
		default:
		}
		return updateRows;
	}

}
