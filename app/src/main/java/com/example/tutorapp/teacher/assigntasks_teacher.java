package com.example.tutorapp.teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.example.tutorapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class assigntasks_teacher extends AppCompatActivity {
    Toolbar bar;
    BottomNavigationView nav;
    ImageView Mcq,Short_answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigntasks_teacher);
        bar=findViewById(R.id.toolbar);
        setSupportActionBar(bar);
        Mcq=findViewById(R.id.Mcq);
        Short_answer=findViewById(R.id.Short_answer);
        Mcq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(assigntasks_teacher.this,select_category.class);
                i.putExtra("Type","MCQ");
                startActivity(i);
            }
        });
        Short_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(assigntasks_teacher.this,select_category.class);
                i.putExtra("Type","Short");
                startActivity(i);
            }
        });
        nav=findViewById(R.id.nav);
        nav.setSelectedItemId(R.id.assign);
        nav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home: startActivity(new Intent(this,home_teacher.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle()); return true;
                case R.id.assign:  return true;
                case R.id.profile: startActivity(new Intent(this,profile_teacher.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle()); return true;
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