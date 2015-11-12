package com.stealth.lnk.stealthwidget.DB;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by injung on 2015. 11. 12..
 */
public class LayoutDAO {

    DBHelper dbHelper;
    SQLiteDatabase sqlDB;

    public LayoutDAO(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public LayoutDTO selectLayout(LayoutDTO dto) {
        LayoutDTO resultDTO = new LayoutDTO();

        sqlDB = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("select * from layout where name = '" + dto.getName() + "';", null);

        while(cursor.moveToNext()) {
            resultDTO.setSeq(cursor.getInt(0));
            resultDTO.setName(cursor.getString(1));
        }

        cursor.close();
        sqlDB.close();

        return resultDTO;

    }
}
