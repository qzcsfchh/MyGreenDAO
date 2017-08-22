package com.hao.v_30;

import android.content.Context;
import android.util.Log;

import com.hao.v_30.bean.DaoMaster;
import com.hao.v_30.bean.DaoSession;
import com.hao.v_30.bean.UserDao;
import com.hao.v_30.bean.UserWrapperDao;

import org.greenrobot.greendao.database.Database;

/**
 * Desc:
 * Created by huanghao123 on 8/22 0022.
 */
public class GreenDaoHelper {
    private static final String TAG = "GreenDaoHelper";
    private DaoSession daoSession;

    private static GreenDaoHelper instance=new GreenDaoHelper();
    private GreenDaoHelper(){};

    public static void init(Context context,boolean encrypt){
        if (instance.daoSession==null) {
            DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, "user-db");
            Database database=encrypt?devOpenHelper.getEncryptedWritableDb("password"):devOpenHelper.getWritableDb();
            instance.daoSession=new DaoMaster(database).newSession();
        }else{
            Log.d(TAG, "init: already done initialization!");
        }
    }

    public static DaoSession getDaoSession(){
        return instance.daoSession;
    }

    public static void clearSessionCache(){
        instance.daoSession.clear();
    }

    public static UserDao getUserDao(){
        return instance.daoSession.getUserDao();
    }

    public static void clearUserCache(){
        instance.daoSession.getUserDao().detachAll();
    }

    public static UserWrapperDao getUserWrapperDao(){
        return instance.daoSession.getUserWrapperDao();
    }

}
