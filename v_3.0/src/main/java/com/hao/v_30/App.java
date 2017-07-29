package com.hao.v_30;

import android.app.Application;

import com.hao.v_30.bean.DaoMaster;
import com.hao.v_30.bean.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Desc:
 * Created by huanghao123 on 7/18 0018.
 */
public class App extends Application {
    /** A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher. */
    public static final boolean ENCRYPTED = true;
    private static App instance;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "user-db");
        Database database=ENCRYPTED?devOpenHelper.getEncryptedWritableDb("password"):devOpenHelper.getWritableDb();
        daoSession=new DaoMaster(database).newSession();
    }

    public DaoSession getDaoSession(){
        return daoSession;
    }

    public static App getInstance(){
        return instance;
    }
}
