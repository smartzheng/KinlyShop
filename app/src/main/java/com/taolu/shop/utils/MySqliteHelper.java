package com.taolu.shop.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yy on 2016/11/27.
 */
public class MySqliteHelper extends SQLiteOpenHelper {
    public MySqliteHelper(Context context ){
        super(context, "RedBaby", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //id  ，名字 url  价格
        db.execSQL("create table merchandise (_id integer primary key autoincrement,numid  varchar(20) ,name varchar(20), url varchar(100),price varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
