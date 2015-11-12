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

    public SettingDTO selectSetting(SettingDTO dto) {
        sqlDB = dbHelper.getReadableDatabase();
        SettingDTO resultDTO = new SettingDTO();
        Cursor cursor;
        cursor = sqlDB.rawQuery("select * from setting where layout_seq = " + dto.getLayout_seq() + ";", null);

        while(cursor.moveToNext()) {
            resultDTO.setSeq(cursor.getInt(0));
            resultDTO.setApp_name(cursor.getString(1));
            resultDTO.setApp_package(cursor.getString(2));
            resultDTO.setApp_icon(cursor.getString(3));
            resultDTO.setLayout_seq(cursor.getInt(4));
        }

        cursor.close();
        sqlDB.close();

        return resultDTO;
    }

    public ArrayList<SettingDTO> selectSettingList(SettingDTO dto) {
        sqlDB = dbHelper.getReadableDatabase();
        ArrayList<SettingDTO> resultDTO = new ArrayList<SettingDTO>();
        SettingDTO saveDto = new SettingDTO();
        Cursor cursor;
        cursor = sqlDB.rawQuery("select * from setting where layout_seq = "+ dto.getLayout_seq() + ";", null);

        while(cursor.moveToNext()) {
            saveDto.setSeq(cursor.getInt(0));
            saveDto.setApp_name(cursor.getString(1));
            saveDto.setApp_package(cursor.getString(2));
            saveDto.setApp_icon(cursor.getString(3));
            saveDto.setLayout_seq(cursor.getInt(4));

            resultDTO.add(saveDto);
        }

        cursor.close();
        sqlDB.close();

        return resultDTO;
    }

    public void insertSetting(SettingDTO dto) {
        sqlDB = dbHelper.getWritableDatabase();
        sqlDB.execSQL("insert into setting(app_name, app_package, layout_seq) values( '" +
                dto.getApp_name() + "', '" +
//                1. 아이콘도 저장할 때
//                dto.getApp_package() + "', '" +
//                dto.getApp_icon() + "', " +

//                2. 아이콘은 빼고 저장할 때
                dto.getApp_package() + "', " +
                dto.getLayout_seq() + ");");
        sqlDB.close();
    }

    public void updateSetting(SettingDTO dto) {
        sqlDB = dbHelper.getWritableDatabase();
        sqlDB.execSQL("");
        sqlDB.close();
    }

    public void deleteSetting(SettingDTO dto) {
        sqlDB = dbHelper.getWritableDatabase();
        sqlDB.execSQL("");
        sqlDB.close();
    }
}
