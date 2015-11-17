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
        super(context, "StealthWidget.db", null, 1);
    }

    //앱을 처음 실행했거나, 데이터를 입력할 때 테이블이 없으면 1회 호출한다.
    public void onCreate(SQLiteDatabase db) {

        //setting Table (int seq, String app_name, String app_package, String app_icon)
        db.execSQL("CREATE TABLE " +
                "setting(seq INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name text, " +
                "app_name text, " +
                "app_package text, " +
                "opacity text );");

        db.execSQL("INSERT INTO setting(name, app_name, app_package, opacity) values('Red', '', '', '#9EFF000D')");
        db.execSQL("INSERT INTO setting(name, app_name, app_package, opacity) values('Green', '', '', '#7704ff00')");
        db.execSQL("INSERT INTO setting(name, app_name, app_package, opacity) values('Blue', '', '', '#5a0037ff')");
        db.execSQL("INSERT INTO setting(name, app_name, app_package, opacity) values('Pink', '', '', '#5aff0073')");
        db.execSQL("INSERT INTO setting(name, app_name, app_package, opacity) values('Yellow', '', '', '#66FFF200')");
        db.execSQL("INSERT INTO setting(name, app_name, app_package, opacity) values('White', '', '', '#b9ffffff')");

    }

    //앱이 업그레이드 되었을 경우 실행된다.
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS setting");
        onCreate(db);
    }
}
