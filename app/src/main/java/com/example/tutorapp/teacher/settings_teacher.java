package com.example.tutorapp.teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.example.tutorapp.Notification;
import com.example.tutorapp.R;
import com.example.tutorapp.login;
import com.example.tutorapp.student.settings_stud;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class settings_teacher extends AppCompatActivity {
    Toolbar bar;
    BottomNavigationView nav;
    Button logout;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_teacher);
        bar=findViewById(R.id.toolbar);
        setSupportActionBar(bar);
        nav=findViewById(R.id.nav);
        nav.setSelectedItemId(R.id.settings);
        nav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home: startActivity(new Intent(this,home_teacher.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle()); return true;
                case R.id.assign: startActivity(new Intent(this,assigntasks_teacher.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle()); return true;
                case R.id.profile: startActivity(new Intent(this,profile_teacher.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle()); return true;
                case R.id.settings:  return true;
                default: return true;
            }
        });
        sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        logout=findViewById(R.id.teacher_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(settings_teacher.this, Notification.class));
                editor.clear();
                editor.commit();
                startActivity(new Intent(settings_teacher.this, login.class));
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar,menu);
        return true;
    }
}