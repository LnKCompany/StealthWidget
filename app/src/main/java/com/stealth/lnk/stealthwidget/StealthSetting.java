package com.stealth.lnk.stealthwidget;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.stealth.lnk.stealthwidget.DB.DBHelper;
import com.stealth.lnk.stealthwidget.DB.LayoutDAO;
import com.stealth.lnk.stealthwidget.DB.LayoutDTO;
import com.stealth.lnk.stealthwidget.DB.SettingDAO;
import com.stealth.lnk.stealthwidget.DB.SettingDTO;

import java.util.ArrayList;

public class StealthSetting extends AppCompatActivity implements View.OnClickListener{

    String layoutName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();//get 부분
        //선택 레이아웃의 id저장
        int activity = intent.getExtras().getInt("selectMenu");
        //선택 레이아웃의 이름 저장
        layoutName = intent.getStringExtra("layoutName");

        DBHelper dbHelper = new DBHelper(this);
        //DB연결
        LayoutDAO layoutDAO = new LayoutDAO(dbHelper);
        LayoutDTO layoutDTO = new LayoutDTO();
        SettingDAO settingDAO = new SettingDAO(dbHelper);
        SettingDTO saveDto = new SettingDTO();
        ArrayList<SettingDTO> settingDTOList;

        layoutDTO.setName(layoutName);
        layoutDTO = layoutDAO.selectLayout(layoutDTO);

        saveDto.setLayout_seq(layoutDTO.getSeq());
        settingDTOList = settingDAO.selectSettingList(saveDto);

        setContentView(activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //버튼의 값을 받아 리스트로 연결한다.
        Button btn1 = (Button)findViewById(R.id.app01);
        btn1.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        // TODO Auto-generated method stub
        Intent intent = new Intent(this, AppInfoActivity.class);
        //선택 레이아웃의 이름 저장
        intent.putExtra("layoutName",layoutName);

        startActivity(intent);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stealth_main, menu);
        return true;
    }
}
