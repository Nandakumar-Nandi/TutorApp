package com.example.tutorapp.teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.example.tutorapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class profile_teacher extends AppCompatActivity {
    Toolbar bar;
    BottomNavigationView nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_teacher);
        bar=findViewById(R.id.toolbar);
        setSupportActionBar(bar);
        nav=findViewById(R.id.nav);
        nav.setSelectedItemId(R.id.profile);
        nav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home: startActivity(new Intent(this,home_teacher.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle()); return true;
                case R.id.assign: startActivity(new Intent(this,assigntasks_teacher.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle()); return true;
                case R.id.profile:  return true;
                case R.id.settings: startActivity(new Intent(this,settings_teacher.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle()); return true;
                default: return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar,menu);
        return true;
    }
}