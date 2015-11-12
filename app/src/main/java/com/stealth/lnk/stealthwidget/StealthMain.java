package com.stealth.lnk.stealthwidget;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.stealth.lnk.stealthwidget.DB.DBHelper;

public class StealthMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stealth_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DBHelper dbHelper = new DBHelper(this);
        dbHelper.close();

    }
    //이부분이 메뉴 추가해주는 부분
    //추가해주려면 res/ menu / main.xml , string.xml 만져줘야지
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stealth_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(this, "기본 메뉴가 터치되었습니다", Toast.LENGTH_SHORT).show();
            return true;
        }else if(id==R.id.menu1){
            //menu1 은 1x1 의 사이즈
            Toast.makeText(this, "1x1가 터치되었습니다", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(StealthMain.this, StealthSetting.class);

            //Layout 정보 저장
            intent.putExtra("selectMenu",R.layout.activity_stealth1_1);
            //Layout DB연결 정보 저장
            intent.putExtra("layoutName", "1n1");

            startActivity(intent);
            //액티비티 하나 추가해서 거기에 putExtrea 로 r.id.menu1 같이 넘겨줘
            return true;
        }else if(id==R.id.menu2){
            // 1x2
            Toast.makeText(this, "1x2가 터치되었습니다", Toast.LENGTH_SHORT).show();
        }else if(id==R.id.menu3){
            // 2x1
            Toast.makeText(this, "2x1 메뉴가 터치되었습니다", Toast.LENGTH_SHORT).show();
        }else if(id==R.id.menu4){
            // 1x4
            Toast.makeText(this, "1x4 메뉴가 터치되었습니다", Toast.LENGTH_SHORT).show();
        }else if(id==R.id.menu5){
            // 4x1
            Toast.makeText(this, "4x1 메뉴가 터치되었습니다", Toast.LENGTH_SHORT).show();
        }else if(id==R.id.menu6){
            // 4x4
            Toast.makeText(this, "4x4 메뉴가 터치되었습니다", Toast.LENGTH_SHORT).show();
        }else if(id==R.id.menu7){
            // 도움말
            Toast.makeText(this, "도움말 메뉴가 터치되었습니다", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
        //메뉴를 선택합니다.
    }

}
