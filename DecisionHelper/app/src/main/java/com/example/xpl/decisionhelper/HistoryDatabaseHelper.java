/*
function:自定义SQLiteOpenHelper
author:xpl
create date:2018.4.30
 */

package com.example.xpl.decisionhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class HistoryDatabaseHelper extends SQLiteOpenHelper {

    private Context mcontext;
    public HistoryDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mcontext = context;
    }

    //历史页面数据包括事件名，结果，时间
    public static String CREATE_DATA = "create table History("
            +"id integer primary key autoincrement,"
            +"name text,"
            +"result text,"
            +"time text)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
