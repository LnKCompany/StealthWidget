package com.stealth.lnk.stealthwidget.DB;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by injung on 2015. 11. 12..
 */
public class SettingDAO {

    DBHelper dbHelper;
    SQLiteDatabase sqlDB;

    public SettingDAO(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public SettingDTO selectOne(SettingDTO dto) {
        sqlDB = dbHelper.getReadableDatabase();
        SettingDTO resultDTO = new SettingDTO();
        Cursor cursor;
        cursor = sqlDB.rawQuery("select * from setting where name = '" + dto.getName() + "';", null);

        while(cursor.moveToNext()) {
            resultDTO.setSeq(cursor.getInt(0));
            resultDTO.setName(cursor.getString(1));
            resultDTO.setApp_name(cursor.getString(2));
            resultDTO.setApp_package(cursor.getString(3));
            resultDTO.setApp_icon(cursor.getString(4));
        }

        cursor.close();
        sqlDB.close();

        return resultDTO;
    }

    public ArrayList<SettingDTO> selectList(SettingDTO dto) {
        sqlDB = dbHelper.getReadableDatabase();
        ArrayList<SettingDTO> resultDTO = new ArrayList<SettingDTO>();
        SettingDTO saveDto = new SettingDTO();
        Cursor cursor;
        cursor = sqlDB.rawQuery("select * from setting where name = '"+ dto.getName() + "';", null);

        while(cursor.moveToNext()) {
            saveDto.setSeq(cursor.getInt(0));
            saveDto.setName(cursor.getString(1));
            saveDto.setApp_name(cursor.getString(2));
            saveDto.setApp_package(cursor.getString(3));
            saveDto.setApp_icon(cursor.getString(4));

            resultDTO.add(saveDto);
        }

        cursor.close();
        sqlDB.close();

        return resultDTO;
    }

    public void insert(SettingDTO dto) {
        sqlDB = dbHelper.getWritableDatabase();
        //구현 필요없음
        sqlDB.close();
    }

    public void update(SettingDTO dto) {
        sqlDB = dbHelper.getWritableDatabase();
        sqlDB.execSQL("update setting set app_name = '" +
                dto.getApp_name() +
                "', app_package = '" +
                dto.getApp_package() +
                "', app_icon = '" +
                dto.getApp_icon() +
                "' where name = '" +
                dto.getName() +
                "';");
        sqlDB.close();
    }

    public void delete(SettingDTO dto) {
        sqlDB = dbHelper.getWritableDatabase();
        //구현 필요없음
        sqlDB.close();
    }
}
