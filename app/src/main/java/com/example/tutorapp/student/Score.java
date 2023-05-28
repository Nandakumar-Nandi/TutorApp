package com.example.tutorapp.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorapp.R;
import com.example.tutorapp.adapters.score_adapter;
import com.example.tutorapp.modelclass.score_class;
import com.example.tutorapp.teacher.home_teacher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Score extends AppCompatActivity {
    TextView Score;
    Button home;
    RecyclerView recyclerView;
    DatabaseReference ref;
    score_adapter adapter;
    ArrayList<score_class> list;
    SharedPreferences sharedPreferences;
    String username,userclass,task_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Score=findViewById(R.id.score_result);
        home=findViewById(R.id.score_home);
        recyclerView=findViewById(R.id.score_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
        if(sharedPreferences.getString("Type","").equals("Student")) {
            username = sharedPreferences.getString("Name", "");
            userclass = sharedPreferences.getString("Class", "");
        }
        else{
            username=getIntent().getStringExtra("Name");
            userclass=getIntent().getStringExtra("Class");
        }
        task_title=getIntent().getStringExtra("Title");
        ref= FirebaseDatabase.getInstance().getReferenceFromUrl("https://tutorapp-7c7ab-default-rtdb.firebaseio.com/");
        list=new ArrayList<>();
        adapter=new score_adapter(list,this);
        ref.child("Class").child(userclass).child("students").child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot ds=task.getResult();
                if(ds.exists()){
                    for(DataSnapshot s:ds.getChildren()){
                        if(s.child("Name").getValue().toString().equals(task_title)){
                            String x=s.child("Score").getValue().toString()+"/"+s.child("Count").getValue().toString();
                            Score.setText(x);
                            for(DataSnapshot snapshot:s.getChildren()) {
                                if (snapshot.hasChildren()) {
                                    score_class data = new score_class(snapshot.child("Question").getValue().toString(),
                                            snapshot.child("Correct Answer").getValue().toString(),
                                            snapshot.child("Answer").getValue().toString());
                                    list.add(data);
                                }
                            }
                            recyclerView.setAdapter(adapter);
                        }
                    }
                }
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIntent().getStringExtra("Type")!=null && getIntent().getStringExtra("Type").equals("teacher") ){
                    startActivity(new Intent(Score.this, home_teacher.class));
                    finish();
                }
                else{
                    startActivity(new Intent(Score.this,home_student.class));
                    finish();
                }

            }
        });
    }
}