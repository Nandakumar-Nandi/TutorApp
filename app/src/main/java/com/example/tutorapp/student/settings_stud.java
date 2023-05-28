package com.example.tutorapp.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tutorapp.Notification;
import com.example.tutorapp.R;
import com.example.tutorapp.login;
import com.example.tutorapp.teacher.assigntasks_teacher;
import com.example.tutorapp.teacher.home_teacher;
import com.example.tutorapp.teacher.profile_teacher;
import com.example.tutorapp.teacher.settings_teacher;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class settings_stud extends AppCompatActivity {
    Button logout;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Toolbar bar;
    BottomNavigationView nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_stud);
        bar=findViewById(R.id.toolbar);
        setSupportActionBar(bar);
        nav=findViewById(R.id.nav);
        nav.setSelectedItemId(R.id.settings);
        nav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home: startActivity(new Intent(this, home_student.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle()); return true;
                case R.id.history: startActivity(new Intent(this, history_stud.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle()); return true;
                case R.id.profile: startActivity(new Intent(this, profile_stud.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle()); return true;
                case R.id.settings:  return true;
                default: return true;
            }
        });
        sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        logout=findViewById(R.id.stud_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(settings_stud.this, Notification.class));
                editor.clear();
                editor.commit();
                startActivity(new Intent(settings_stud.this, login.class));
                finish();
            }
        });

    }
}