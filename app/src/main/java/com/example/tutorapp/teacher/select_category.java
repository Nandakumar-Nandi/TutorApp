package com.example.tutorapp.teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.tutorapp.R;

public class select_category extends AppCompatActivity {
    Button feeded,custom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);
        feeded=findViewById(R.id.feeded);
        custom=findViewById(R.id.custom);
        feeded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(select_category.this,questionlist.class);
                i.putExtra("Type",getIntent().getStringExtra("Type"));
                startActivity(i);
            }
        });
        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(select_category.this,create.class);
                i.putExtra("Type",getIntent().getStringExtra("Type"));
                startActivity(i);
            }
        });
    }

}