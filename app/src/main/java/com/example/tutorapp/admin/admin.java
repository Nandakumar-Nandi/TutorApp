package com.example.tutorapp.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tutorapp.R;
import com.example.tutorapp.login;
import com.example.tutorapp.student.settings_stud;

public class admin extends AppCompatActivity {
    ConstraintLayout teacher,student;
    Button logout;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        logout=findViewById(R.id.admin_logout);
        sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.commit();
                startActivity(new Intent(admin.this, login.class));
                finish();
            }
        });
        teacher=findViewById(R.id.teacher);
        student=findViewById(R.id.student);
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(admin.this, list.class);
                i.putExtra("Value","teachers");
                startActivity(i);
            }
        });
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(admin.this,list.class);
                i.putExtra("Value","students");
                startActivity(i);
            }
        });
    }
}