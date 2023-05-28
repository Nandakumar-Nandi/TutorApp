package com.example.tutorapp.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.tutorapp.R;
import com.example.tutorapp.adapters.Question_set_Adapter;
import com.example.tutorapp.modelclass.Question_set;
import com.example.tutorapp.teacher.questionlist;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class home_student extends AppCompatActivity {
    Toolbar bar;
    BottomNavigationView nav;
    Question_set_Adapter adapter;
    ArrayList<Question_set> list;
    DatabaseReference ref;
    RecyclerView view;
    SharedPreferences sharedPreferences;
    String username,userclass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_stud);
        bar=findViewById(R.id.toolbar);
        setSupportActionBar(bar);
        nav=findViewById(R.id.nav);
        nav.setSelectedItemId(R.id.home);
        nav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:  return true;
                case R.id.history: startActivity(new Intent(this, history_stud.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle()); return true;
                case R.id.profile: startActivity(new Intent(this, profile_stud.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle()); return true;
                case R.id.settings:startActivity(new Intent(this, settings_stud.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());  return true;
                default: return true;
            }
        });
        sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
        username=sharedPreferences.getString("Name","");
        userclass=sharedPreferences.getString("Class","");
        list=new ArrayList<>();
        adapter=new Question_set_Adapter(list,this);
        view =findViewById(R.id.home_stud);
        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(this));
        ref= FirebaseDatabase.getInstance().getReferenceFromUrl("https://tutorapp-7c7ab-default-rtdb.firebaseio.com/");
        ref.child("Class").child(userclass).child("students").child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot student=task.getResult();
                ref.child("Class").child(userclass).child("tasks").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        DataSnapshot dataSnapshot=task.getResult();
                        if(dataSnapshot.exists()) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                if(!student.child(ds.getKey()).exists()) {
                                    String title = String.valueOf(ds.child("Name").getValue());
                                    String difficulty = String.valueOf(ds.child("Difficulty").getValue());
                                    String count = String.valueOf(ds.getChildrenCount());
                                    String type = "";
                                    if (ds.child("Type").exists()) {
                                        type = String.valueOf(ds.child("Type").getValue());
                                    }
                                    Question_set data = new Question_set(ds.getKey(), title, count, difficulty, type);
                                    list.add(data);
                                    view.setAdapter(adapter);
                                }
                            }
                        }
                    }
                });
            }
        });

    }
}