package com.schoolpartime.schoolpartime;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.schoolpartime.dao.DaoMaster;
import com.schoolpartime.dao.DaoSession;

public class SchoolPartimeApplication extends Application {

    public static final String DB_NAME = "app.db";

    private static DaoSession mDaoSession;

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
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

    public static Context getContext() {
        return context;
    }

}
