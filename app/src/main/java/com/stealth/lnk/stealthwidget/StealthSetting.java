package com.stealth.lnk.stealthwidget;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.stealth.lnk.stealthwidget.DB.DBHelper;
import com.stealth.lnk.stealthwidget.DB.SettingDAO;
import com.stealth.lnk.stealthwidget.DB.SettingDTO;

import java.util.ArrayList;

public class StealthSetting extends AppCompatActivity implements View.OnClickListener{

    String colorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();//get 부분
        colorName = intent.getStringExtra("menu");

        DBHelper dbHelper = new DBHelper(this);
        //DB연결
        SettingDAO settingDAO = new SettingDAO(dbHelper);
        SettingDTO settingDTO = new SettingDTO();

        settingDTO.setName(colorName);
        settingDTO = settingDAO.selectOne(settingDTO);

        if(settingDTO.getApp_name().equals("")) {
            //설정된 내용이 없다면 텍스트 변경
            //나중에 설정하세요! 아이콘으로 변경해야함.
        } else {
//            설정되어있다면 버튼에 아이콘 모양을 넣어야함
        }

        setContentView(R.layout.activity_stealth_setting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //버튼의 값을 받아 리스트로 연결한다.
        Button btn1 = (Button)findViewById(R.id.app01);
        btn1.setOnClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent intent = new Intent(StealthSetting.this, StealthSetting.class);
        String color=null;
        if (id == R.id.action_settings) {
            Toast.makeText(this, "기본 메뉴가 터치되었습니다", Toast.LENGTH_SHORT).show();
            return true;
        }else if(id==R.id.menu1){
            //menu1 은 1x1 의 사이즈
            Toast.makeText(this, "Red 메뉴가 터치되었습니다", Toast.LENGTH_SHORT).show();
            color="Red";
            //액티비티 하나 추가해서 거기에 putExtrea 로 r.id.menu1 같이 넘겨줘

        }else if(id==R.id.menu2){
            // 1x2
            Toast.makeText(this, "Green 메뉴가 터치되었습니다", Toast.LENGTH_SHORT).show();
            color="Green";

        }else if(id==R.id.menu3){
            // 2x1
            Toast.makeText(this, "Blue 메뉴가 터치되었습니다", Toast.LENGTH_SHORT).show();
            color="Blue";

        }else if(id==R.id.menu4){
            // 1x4
            Toast.makeText(this, "Pink 메뉴가 터치되었습니다", Toast.LENGTH_SHORT).show();
            color="Pink";

        }else if(id==R.id.menu5){
            // 4x1
            Toast.makeText(this, "White 메뉴가 터치되었습니다", Toast.LENGTH_SHORT).show();
            color="White";

        }else if(id==R.id.menu6){
            // 4x4
            Toast.makeText(this, "Yellow 메뉴가 터치되었습니다", Toast.LENGTH_SHORT).show();
            color="Yellow";

        }else if(id==R.id.menu7){
            // 도움말
            Toast.makeText(this, "도움말 메뉴가 터치되었습니다", Toast.LENGTH_SHORT).show();
            color="Help";
        }

        intent.putExtra("menu", color);

        startActivity(intent);

        finish();

        return super.onOptionsItemSelected(item);
        //메뉴를 선택합니다.
    }

    @Override
    public void onClick(View v) {

        // TODO Auto-generated method stub
        Intent intent = new Intent(this, AppInfoActivity.class);
        //선택 레이아웃의 이름 저장
        intent.putExtra("menu",colorName);

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
