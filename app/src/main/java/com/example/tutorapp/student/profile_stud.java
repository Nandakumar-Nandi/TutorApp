package com.example.tutorapp.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import com.example.tutorapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class profile_stud extends AppCompatActivity {
    Toolbar bar;
    BottomNavigationView nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_stud);
        bar=findViewById(R.id.toolbar);
        setSupportActionBar(bar);
        nav=findViewById(R.id.nav);
        nav.setSelectedItemId(R.id.profile);
        nav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:startActivity(new Intent(this, home_student.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());  return true;
                case R.id.history: startActivity(new Intent(this, history_stud.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle()); return true;
                case R.id.profile:  return true;
                case R.id.settings:startActivity(new Intent(this, settings_stud.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());  return true;
                default: return true;
            }
        });
    }
}