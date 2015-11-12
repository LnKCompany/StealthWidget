package com.stealth.lnk.stealthwidget.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by injung on 2015. 11. 12..
 */
public class DBHelper  extends SQLiteOpenHelper {

    //생성자 정의
    public DBHelper(Context context) {
        super(context, "StealthWidgetDB", null, 1);
    }

    //앱을 처음 실행했거나, 데이터를 입력할 때 테이블이 없으면 1회 호출한다.
    public void onCreate(SQLiteDatabase db) {
        //layout Table (int seq, String name)
        db.execSQL("CREATE TABLE " +
                "layout(seq INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name text );");

        //layout - 생성 시 작성
        db.execSQL("INSERT INTO layout(name) values('1n1');");
        db.execSQL("INSERT INTO layout(name) values('1n2');");
        db.execSQL("INSERT INTO layout(name) values('1n4');");
        db.execSQL("INSERT INTO layout(name) values('2n1');");
        db.execSQL("INSERT INTO layout(name) values('4n1');");
        db.execSQL("INSERT INTO layout(name) values('4n4');");


        //setting Table (int seq, String app_name, String app_package, String app_icon, int layout_seq)
        db.execSQL("CREATE TABLE " +
                "setting(seq INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "app_name text," +
                "app_package text," +
                "app_icon text," +
                "layout_seq INTEGER );");
        //실행할 SQL문을 작성
    }

    //앱이 업그레이드 되었을 경우 실행된다.
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS layout");
        db.execSQL("DROP TABLE IF EXISTS setting");
        onCreate(db);
    }
}
