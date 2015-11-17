package com.stealth.lnk.stealthwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.RemoteViews;

import com.stealth.lnk.stealthwidget.DB.DBHelper;
import com.stealth.lnk.stealthwidget.DB.SettingDAO;
import com.stealth.lnk.stealthwidget.DB.SettingDTO;

/**
 * Implementation of App Widget functionality.
 */
public class StealthWidgetBlue extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {

            int widgetId = appWidgetIds[i];
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);

            DBHelper dbHelper = new DBHelper(context);
            //DB연결
            SettingDAO settingDAO = new SettingDAO(dbHelper);
            SettingDTO settingDTO = new SettingDTO();

            settingDTO.setName("Blue");
            settingDTO = settingDAO.selectOne(settingDTO);
            Intent intent = null;

            RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.stealth_widget_blue);
            remoteView.setInt(R.id.app01, "setBackgroundColor", Color.parseColor(settingDTO.getOpacity()));

            if(settingDTO.getApp_name().equals("")) {
                //앱 설정이 되지 않았을 경우 메인으로 연결
                intent = new Intent(context, StealthMain.class);
            } else {
                intent = context.getPackageManager().getLaunchIntentForPackage(settingDTO.getApp_package());
            }

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            remoteView.setOnClickPendingIntent(R.id.app01, pendingIntent);
            /**
             * 위젯 업데이트
             */
            appWidgetManager.updateAppWidget(widgetId, remoteView);

            dbHelper.close();
        }

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, StealthWidgetBlue.class)));

        super.onReceive(context, intent);
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

