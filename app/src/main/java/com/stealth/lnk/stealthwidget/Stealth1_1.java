package com.stealth.lnk.stealthwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.stealth.lnk.stealthwidget.DB.DBHelper;
import com.stealth.lnk.stealthwidget.DB.LayoutDAO;
import com.stealth.lnk.stealthwidget.DB.LayoutDTO;
import com.stealth.lnk.stealthwidget.DB.SettingDAO;
import com.stealth.lnk.stealthwidget.DB.SettingDTO;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class Stealth1_1 extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {

            int widgetId = appWidgetIds[i];
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
            Intent intent = new Intent(context, StealthMain.class);

            DBHelper dbHelper = new DBHelper(context);
            //DB연결
            LayoutDAO layoutDAO = new LayoutDAO(dbHelper);
            LayoutDTO layoutDTO = new LayoutDTO();
            SettingDAO settingDAO = new SettingDAO(dbHelper);
            SettingDTO saveDto = new SettingDTO();
            ArrayList<SettingDTO> settingDTOList;

            layoutDTO.setName("1n1");
            layoutDTO = layoutDAO.selectLayout(layoutDTO);

            saveDto.setLayout_seq(layoutDTO.getSeq());
            settingDTOList = settingDAO.selectSettingList(saveDto);

            if(settingDTOList.isEmpty()) {
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

                RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.stealth1_1);
                remoteView.setOnClickPendingIntent(R.id.app01, pendingIntent);
                appWidgetManager.updateAppWidget(widgetId, remoteView);
            } else {
                for(int j = 0 ; j < settingDTOList.size() ; j++ ) {
                    saveDto = settingDTOList.get(0);
            RemoteViews remoteView = new RemoteViews(context.getPackageName(),R.layout.stealth1_1);
            Intent intent1 = context.getPackageManager().getLaunchIntentForPackage(saveDto.getApp_package());
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, 0);
            remoteView.setOnClickPendingIntent(R.id.app01, pendingIntent);

            /**
             * 위젯 업데이트
             */
            appWidgetManager.updateAppWidget(widgetId, remoteView);
                }
            }

        }

    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

    }
}

