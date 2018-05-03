/*
function:自定义SQLiteOpenHelper，用于存储已编辑的事件和选项,初始建立，插入初始数据
author:xiepeiliang
create date:2018.4.28
 */

package com.example.xpl.decisionhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper{
    public static String CREATE_DATA = "create table Data("
            +"id integer primary key autoincrement,"
            +"name text,"
            +"data text)";

    public static String InsertData = "insert into Data values" +
                                      "(1,'出行','步行,自行车,公交,开车,地铁')," +
                                      "(2,'晚上吃什么','面条,米饭,饺子,出去吃')," +
                                      "(3,'学习还是玩耍','学习,玩耍')," +
                                      "(4,'谁去刷碗','老公,老婆')";

    private Context mcontext;
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATA);
        db.execSQL(InsertData);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
