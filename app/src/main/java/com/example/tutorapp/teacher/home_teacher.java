package com.example.tutorapp.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;

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

public class home_teacher extends AppCompatActivity {
    Toolbar bar;
    BottomNavigationView nav;
    SharedPreferences sharedPreferences;
    String username;
    DatabaseReference ref;
    RecyclerView recyclerView1,recyclerView2;
    Question_set_Adapter adapter1,adapter2;
    ArrayList<Question_set> list1,list2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_teacher);
        sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
        username=sharedPreferences.getString("Name","");
        bar=findViewById(R.id.toolbar);
        setSupportActionBar(bar);
        nav=findViewById(R.id.nav);
        nav.setSelectedItemId(R.id.home);
        nav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home: return true;
                case R.id.assign: startActivity(new Intent(this,assigntasks_teacher.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle()); return true;
                case R.id.profile: startActivity(new Intent(this,profile_teacher.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle()); return true;
                case R.id.settings: startActivity(new Intent(this,settings_teacher.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle()); return true;
                default: return true;
            }
        });
        recyclerView1=findViewById(R.id.teacher_home_mca_recycler);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2=findViewById(R.id.teacher_home_mtech_recycler);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        list1=new ArrayList<>();
        adapter1=new Question_set_Adapter(list1,this);
        list2=new ArrayList<>();
        adapter2=new Question_set_Adapter(list1,this);
        ref= FirebaseDatabase.getInstance().getReferenceFromUrl("https://tutorapp-7c7ab-default-rtdb.firebaseio.com/");
        ref.child("Assigned").child(username).child("MCA").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task1) {
                    DataSnapshot ds=task1.getResult();
                    for(DataSnapshot s:ds.getChildren()){
                        ref.child("Class").child("MCA").child("tasks").child(s.getKey()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task2) {
                                DataSnapshot snapshot=task2.getResult();
                                    String title=String.valueOf(snapshot.child("Name").getValue());
                                    String difficulty=String.valueOf(snapshot.child("Difficulty").getValue());
                                    String count= String.valueOf(snapshot.getChildrenCount());
                                    Question_set data=new Question_set(snapshot.getKey(),title,count,difficulty,"");
                                    list1.add(data);
                                    recyclerView1.setAdapter(adapter1);
                                }

                        });
                    }
            }
        });
        ref.child("Assigned").child(username).child("MTECH").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task1) {
                DataSnapshot ds=task1.getResult();
                for(DataSnapshot s:ds.getChildren()){
                    ref.child("Class").child("MCA").child("tasks").child(s.getKey()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task2) {
                            DataSnapshot snapshot=task2.getResult();
                            String title=String.valueOf(snapshot.child("Name").getValue());
                            String difficulty=String.valueOf(snapshot.child("Difficulty").getValue());
                            String count= String.valueOf(snapshot.getChildrenCount());
                            Question_set data=new Question_set(snapshot.getKey(),title,count,difficulty,"");
                            list2.add(data);
                            recyclerView2.setAdapter(adapter2);
                        }

                    });
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar,menu);
        return true;
    }
}