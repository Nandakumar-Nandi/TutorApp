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

import com.example.tutorapp.R;
import com.example.tutorapp.adapters.Question_set_Adapter;
import com.example.tutorapp.modelclass.Question_set;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class history_stud extends AppCompatActivity {
    Toolbar bar;
    BottomNavigationView nav;
    SharedPreferences sharedPreferences;
    DatabaseReference ref;
    RecyclerView recyclerView;
    String username,userclass;
    Question_set_Adapter adapter;
    ArrayList<Question_set>list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_stud);
        bar=findViewById(R.id.toolbar);
        setSupportActionBar(bar);
        nav=findViewById(R.id.nav);
        nav.setSelectedItemId(R.id.history);
        nav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:startActivity(new Intent(this, home_student.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());  return true;
                case R.id.history:  return true;
                case R.id.profile: startActivity(new Intent(this, profile_stud.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle()); return true;
                case R.id.settings:startActivity(new Intent(this, settings_stud.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());  return true;
                default: return true;
            }
        });
        sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
        username=sharedPreferences.getString("Name","");
        userclass=sharedPreferences.getString("Class","");
        recyclerView=findViewById(R.id.history_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        list=new ArrayList<>();
        adapter=new Question_set_Adapter(list,this);
        ref= FirebaseDatabase.getInstance().getReferenceFromUrl("https://tutorapp-7c7ab-default-rtdb.firebaseio.com/");
        ref.child("Class").child(userclass).child("students").child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot dataSnapshot=task.getResult();
                if(dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String title=String.valueOf(ds.child("Name").getValue());
                        String difficulty=String.valueOf(ds.child("Difficulty").getValue());
                        String count= String.valueOf(ds.child("Count").getValue());
                        Question_set data=new Question_set(ds.getKey().toString(),title,count,difficulty,"");
                        list.add(data);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }
        });


    }
}