package com.schoolpartime.schoolpartime;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.schoolpartime.dao.DaoMaster;
import com.schoolpartime.dao.DaoSession;

public class SchoolPartimeApplication extends Application {

    public static final String DB_NAME = "app.db";

    private static DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        initGreenDao();
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, DB_NAME);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    public static DaoSession getmDaoSession() {
        return mDaoSession;
    }


}
