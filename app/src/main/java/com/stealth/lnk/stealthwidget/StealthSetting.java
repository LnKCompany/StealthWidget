package com.stealth.lnk.stealthwidget;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.stealth.lnk.stealthwidget.DB.DBHelper;
import com.stealth.lnk.stealthwidget.DB.SettingDAO;
import com.stealth.lnk.stealthwidget.DB.SettingDTO;

import java.util.ArrayList;

public class StealthSetting extends AppCompatActivity implements View.OnClickListener{

    Intent intent;
    String colorName, cur_trans, less_trans, more_trans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = getIntent();//get 부분
        colorName = intent.getStringExtra("menu");

        DBHelper dbHelper = new DBHelper(this);
        //DB연결
        SettingDAO settingDAO = new SettingDAO(dbHelper);
        SettingDTO settingDTO = new SettingDTO();

        settingDTO.setName(colorName);
        settingDTO = settingDAO.selectOne(settingDTO);

        setContentView(R.layout.activity_stealth_setting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //버튼의 값을 받아 리스트로 연결한다.
        Button btn1 = (Button)findViewById(R.id.app01);
        btn1.setOnClickListener(this);

        //설정되었는지 확인하고 처리하는 부분
        if(settingDTO.getApp_name().length() == 0) {
        //설정된 내용이 없다면 텍스트 변경
        TextView textView = (TextView)findViewById(R.id.setting_text);
        textView.setText("설정하려면 아래 버튼을 터치하세요.");
        }


        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayout1);
        if(colorName.equals("White")) {
            linearLayout.setBackgroundColor(Color.parseColor("#44000000"));
        } else {
            linearLayout.setBackgroundColor(Color.parseColor("#00000000"));
        }

        cur_trans = intent.getStringExtra("cur_trans");
        less_trans = intent.getStringExtra("less_trans");
        more_trans = intent.getStringExtra("more_trans");


        //버튼을 저장한다
        Button btn_cur = (Button)findViewById(R.id.cur_trans);
        Button btn_less = (Button)findViewById(R.id.less_trans);
        Button btn_more = (Button)findViewById(R.id.more_trans);

        Button btn_cur_ex = (Button)findViewById(R.id.cur_trans_ex);
        Button btn_less_ex = (Button)findViewById(R.id.less_trans_ex);
        Button btn_more_ex = (Button)findViewById(R.id.more_trans_ex);

        //예시 색상 설정
        btn_cur_ex.setBackgroundColor(Color.parseColor(cur_trans));
        btn_less_ex.setBackgroundColor(Color.parseColor(less_trans));
        btn_more_ex.setBackgroundColor(Color.parseColor(more_trans));

        //버튼 리스너에 등록
        btn_cur.setOnClickListener(this);
        btn_less.setOnClickListener(this);
        btn_more.setOnClickListener(this);

        btn_cur_ex.setOnClickListener(this);
        btn_less_ex.setOnClickListener(this);
        btn_more_ex.setOnClickListener(this);

        dbHelper.close();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent intent = new Intent(StealthSetting.this, StealthSetting.class);
        String color=null, more="#00000000", less=null, cur=null;
        if (id == R.id.action_settings) {
            Toast.makeText(this, "기본 메뉴가 터치되었습니다", Toast.LENGTH_SHORT).show();
            return true;
        }else if(id==R.id.menu1){
            //menu1 은 1x1 의 사이즈
            Toast.makeText(this, "Red 메뉴가 터치되었습니다", Toast.LENGTH_SHORT).show();
            color="Red";
            cur = "#9EFF000D";
            less = "#4AFF000D";
        }else if(id==R.id.menu2){
            // 1x2
            Toast.makeText(this, "Green 메뉴가 터치되었습니다", Toast.LENGTH_SHORT).show();
            color="Green";
            cur = "#7704ff00";
            less = "#3B04FF00";
        }else if(id==R.id.menu3){
            // 2x1
            Toast.makeText(this, "Blue 메뉴가 터치되었습니다", Toast.LENGTH_SHORT).show();
            color="Blue";
            cur = "#5a0037ff";
            less = "#2D0037FF";
        }else if(id==R.id.menu4){
            // 1x4
            Toast.makeText(this, "Pink 메뉴가 터치되었습니다", Toast.LENGTH_SHORT).show();
            color="Pink";
            cur = "#5aff0073";
            less = "#2DFF0073";
        }else if(id==R.id.menu5){
            // 4x1
            Toast.makeText(this, "Yellow 메뉴가 터치되었습니다", Toast.LENGTH_SHORT).show();
            color="Yellow";
            cur = "#66FFF200";
            less = "#35FFF200";
        }else if(id==R.id.menu6){
            // 4x4
            Toast.makeText(this, "White 메뉴가 터치되었습니다", Toast.LENGTH_SHORT).show();
            color="White";
            cur = "#b9ffffff";
            less = "#52FFFFFF";
        }else if(id==R.id.menu7){
            // 도움말
            Toast.makeText(this, "도움말 메뉴가 터치되었습니다", Toast.LENGTH_SHORT).show();
            color="Help";

        }

        intent.putExtra("menu", color);     //메뉴 선택
        intent.putExtra("cur_trans",cur);   //100%  색
        intent.putExtra("less_trans", less);//50%   색
        intent.putExtra("more_trans", more);//0%    색

        startActivity(intent);

        finish();

        return super.onOptionsItemSelected(item);
        //메뉴를 선택합니다.
    }

    @Override
    public void onClick(View v) {

        Button button = (Button)v;
        String tag = button.getTag().toString();

        DBHelper dbHelper = new DBHelper(this);

        SettingDAO settingDAO = new SettingDAO(dbHelper);
        SettingDTO settingDTO = new SettingDTO();

        settingDTO.setName(colorName);

        if (tag.equals("setting")) {
            // TODO Auto-generated method stub
            Intent intent = new Intent(this, AppInfoActivity.class);
            //선택 레이아웃의 이름 저장
            intent.putExtra("menu",colorName);
            startActivity(intent);
            finish();
        } else if (tag.equals("more_trans")) {
            //0%로 변경한다
            settingDTO.setOpacity(more_trans);
            settingDAO.updateOpacity(settingDTO);
            Toast.makeText(this, "불투명도가 0%로 설정되었습니다.", Toast.LENGTH_SHORT).show();
        } else if (tag.equals("less_trans")) {
            //50%로 변경한다
            settingDTO.setOpacity(less_trans);
            settingDAO.updateOpacity(settingDTO);
            Toast.makeText(this, "불투명도가 50%로 설정되었습니다.", Toast.LENGTH_SHORT).show();
        } else if (tag.equals("cur_trans")) {
            //100%로 변경한다
            settingDTO.setOpacity(cur_trans);
            settingDAO.updateOpacity(settingDTO);
            Toast.makeText(this, "불투명도가 100%로 설정되었습니다.", Toast.LENGTH_SHORT).show();
        }

        String action = "com.stealth.lnk.updatewidget." +colorName ;
        Intent intent = new Intent(action);
        sendBroadcast(intent);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stealth_main, menu);
        return true;
    }
}
