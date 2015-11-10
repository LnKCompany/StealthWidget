package com.stealth.lnk.stealthwidget;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class StealthSetting extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();//get 부분
        int activity = intent.getExtras().getInt("selectMenu");

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

        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stealth_main, menu);
        return true;
    }
}
