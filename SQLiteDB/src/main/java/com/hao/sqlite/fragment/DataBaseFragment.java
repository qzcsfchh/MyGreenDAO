package com.hao.sqlite.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hao.sqlite.R;
import com.hao.sqlite.sqlitehelper.DBOpenHelper;

/**
 * Desc:
 * Created by huanghao123 on 8/6 0006.
 */
public class DataBaseFragment extends Fragment {
    Button btnCreate;
    Button btnAdd;
    Button btnDelete;
    Button btnUpdate;
    Button btnQuery;
    Button btnReplace;

    DBOpenHelper mHelper;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.activity_main, container, false);
        findviews(contentView);
        return contentView;
    }

    public void findviews(View contentView){
        btnCreate=(Button) contentView.findViewById(R.id.btnCreate);
        btnAdd=(Button) contentView.findViewById(R.id.btnAdd);
        btnDelete=(Button) contentView.findViewById(R.id.btnDelete);
        btnUpdate=(Button) contentView.findViewById(R.id.btnUpdate);
        btnQuery=(Button) contentView.findViewById(R.id.btnQuery);
        btnReplace=(Button) contentView.findViewById(R.id.btnReplaceData);
    }

    /**
     *
     * @param view
     */
    public void createDatabase(View view){
        SQLiteDatabase db=mHelper.getReadableDatabase();    //注意只有获得SQLiteDatabase对象的操作才会调用其onCreate和onUpGrade方法
    }
    /**
     *
     * @param view
     */
    public void addData(View view){
        SQLiteDatabase db=mHelper.getReadableDatabase();
        //方法一：使用ContentValues+insert方法
        ContentValues values=new ContentValues();
        values.put("author", "Dan Brown");
        values.put("name", "The Da Vinci Code");
        values.put("pages", 454);
        values.put("price", 16.96);
        db.insert("Book", null, values);
        //方法二：直接使用sql语句
        String sql1="insert into Book (name,author,pages,price)values(?,?,?,?)";
        db.execSQL(sql1, new String[]{"the lost symbol","dan brown","510","19.95"});
        //方法二：
        String sql2="insert into Book (name,author,pages,price)values('haha','jack',200,20.00)";
        db.execSQL(sql2);
    }
    /**
     *
     * @param view
     */
    public void deleteData(View view){
        SQLiteDatabase db=mHelper.getReadableDatabase();
        //方法一：使用ContentValues+update方法
        ContentValues values=new ContentValues();
        values.put("name", "tom");
        db.delete("Book", "pages>?", new String[]{"500"});   //把pages>500的数据删掉
        //方法二：直接使用sql语句
        String sql="delete from Book where name='jack' ";  //把name为tom的数据删掉
        db.execSQL(sql);
    }
    /**
     *
     * @param view
     */
    public void updateData(View view){
        SQLiteDatabase db=mHelper.getReadableDatabase();
        // 方法一：使用ContentValues+update方法
        ContentValues values = new ContentValues();
        values.put("name", "tom");
        db.update("Book", values, "name=? and pages=?", new String[] { "haha","200" }); // 把name为haha的对象的name改为tom
        // 方法二：直接使用sql语句
        String sql = "update Book set name='jack' where name='tom'";
        db.execSQL(sql);
    }
    /**
     *
     * @param view
     */
    public void queryData(View view){
        SQLiteDatabase db=mHelper.getReadableDatabase();
        //方法一：使用sqlitedatabase的query方法
        Cursor cursor=db.query(  //查询book表中所有的数据
                "Book",     //表名
                null,       //查询哪些字段，null表示返回所有的字段值
                null,       //where column=value，表示限制条件
                null,  //value，一般用new String[]{}
                null,  //根据哪一列进行分组，null表示不分组
                null, //对groupby后的结果进一步约束
                null);  //字段名，结果排序的依据
        if(cursor.moveToFirst()){
            do{
                String name=cursor.getString(cursor.getColumnIndex("name"));
                String author=cursor.getString(cursor.getColumnIndex("author"));
                int pages=cursor.getInt(cursor.getColumnIndex("pages"));
                double price=cursor.getDouble(cursor.getColumnIndex("price"));
                Log.i("MainActivity", "book name="+name);
                Log.i("MainActivity", "book author="+author);
                Log.i("MainActivity", "book pages="+pages);
                Log.i("MainActivity", "book price="+price);
            }while(cursor.moveToNext());
        }
        cursor.close();
    }
    /**
     * 使用事务：transaction可以保证事务中的语句要么都执行，要么都不执行；涉及到数据的删改操作时可以保护数据的安全
     * @param view
     */
    public void replaceData(View view){
        SQLiteDatabase db=mHelper.getReadableDatabase();
        //启动事务
        db.beginTransaction();
        try{
            db.delete("Book", null, null);  //删除book表里面的所有数据
            if(true){
                throw new NullPointerException();    //故意抛出异常，这样后面的语句就不执行了
            }
            String sql_insert="insert into Book(name,author,pages,price)values('heihei','green',250,13.15)";
            db.execSQL(sql_insert);
            db.setTransactionSuccessful(); //事务实行成功的标记
        }catch(NullPointerException e){
            e.printStackTrace();
        }finally{
            db.endTransaction();   //关闭事务
        }
    }
}
