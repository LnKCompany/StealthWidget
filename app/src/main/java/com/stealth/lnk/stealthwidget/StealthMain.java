package com.stealth.lnk.stealthwidget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stealth.lnk.stealthwidget.DB.DBHelper;
import com.stealth.lnk.stealthwidget.DB.SettingDAO;
import com.stealth.lnk.stealthwidget.DB.SettingDTO;

import java.sql.BatchUpdateException;
import java.util.ArrayList;

public class StealthMain extends AppCompatActivity {

    String firstText = "설정된 앱 이름 : ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stealth_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public void onResume() {
        super.onResume();

        DBHelper dbHelper = new DBHelper(this);

        SettingDAO settingDAO = new SettingDAO(dbHelper);
        ArrayList<SettingDTO> settingDTOList = new ArrayList<SettingDTO>();
        ArrayList<TextView> textViews = new ArrayList<TextView>();

        textViews.add((TextView)findViewById(R.id.red_app));
        textViews.add((TextView)findViewById(R.id.green_app));
        textViews.add((TextView)findViewById(R.id.blue_app));
        textViews.add((TextView)findViewById(R.id.pink_app));
        textViews.add((TextView)findViewById(R.id.yellow_app));
        textViews.add((TextView)findViewById(R.id.white_app));

        settingDTOList = settingDAO.selectList();

        for(SettingDTO dto : settingDTOList) {
            for(TextView text : textViews) {
//                text.setText(firstText);
                if(dto.getName().equals(text.getTag().toString())) {
                    if(dto.getApp_name().length() == 0)
                        text.setText("설정이 필요합니다!");
                    else
                        text.setText("설정된 앱 이름 : " + dto.getApp_name());
                }
            }
        }

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
        Intent intent = new Intent(StealthMain.this, StealthSetting.class);
        String color=null;
        if (id == R.id.action_settings) {
            Toast.makeText(this, "기본 메뉴가 터치되었습니다", Toast.LENGTH_SHORT).show();
            return true;
        }else if(id==R.id.menu1){
            //menu1 은 1x1 의 사이즈
            Toast.makeText(this, "Red 메뉴가 터치되었습니다", Toast.LENGTH_SHORT).show();
            color="Red";

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
            Toast.makeText(this, "Yellow 메뉴가 터치되었습니다", Toast.LENGTH_SHORT).show();
            color="Yellow";

        }else if(id==R.id.menu6){
            // 4x4
            Toast.makeText(this, "White 메뉴가 터치되었습니다", Toast.LENGTH_SHORT).show();
            color="White";

        }else if(id==R.id.menu7){
            // 도움말
            Toast.makeText(this, "도움말 메뉴가 터치되었습니다", Toast.LENGTH_SHORT).show();
            color="Help";

        }

        intent.putExtra("menu", color);

        startActivity(intent);

        return super.onOptionsItemSelected(item);
        //메뉴를 선택합니다.
    }

}
