package com.example.tutorapp.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tutorapp.R;
import com.example.tutorapp.adapters.Question_set_Adapter;
import com.example.tutorapp.modelclass.Question_set;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class create extends AppCompatActivity {
    TextInputEditText quizname,count,difficulty;
    Button create;
    RecyclerView recyclerView;
    DatabaseReference ref;
    SharedPreferences sharedPreferences;
    String Name,type;
    Question_set_Adapter adapter;
    ArrayList<Question_set> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        quizname=findViewById(R.id.create_quizname);
        count=findViewById(R.id.create_quizcount);
        difficulty=findViewById(R.id.create_quizdifficulty);
        create=findViewById(R.id.create_start);
        sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
        Name=sharedPreferences.getString("Name","");
        type=getIntent().getStringExtra("Type");
        list=new ArrayList<>();
        adapter=new Question_set_Adapter(list,this);
        recyclerView=findViewById(R.id.create_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ref= FirebaseDatabase.getInstance().getReferenceFromUrl("https://tutorapp-7c7ab-default-rtdb.firebaseio.com/");
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.child("Tasks").child(type).child("Custom").child(Name).child(quizname.getText().toString()).child("Name").setValue(quizname.getText().toString());
                ref.child("Tasks").child(type).child("Custom").child(Name).child(quizname.getText().toString()).child("Difficulty").setValue(difficulty.getText().toString());
                if(type.equals("MCQ")){
                Intent i=new Intent(create.this,create_q_n_a.class);
                i.putExtra("Quiz_name",quizname.getText().toString());
                i.putExtra("Question_count",count.getText().toString());
                startActivity(i);}
                else{
                    Intent i=new Intent(create.this,create_short_answer.class);
                    i.putExtra("Quiz_name",quizname.getText().toString());
                    i.putExtra("Question_count",count.getText().toString());
                    startActivity(i);
                }
            }
        });
        ref.child("Tasks").child(type).child("Custom").child(Name).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot snapshot= task.getResult();
                if(snapshot.exists()){
                    for(DataSnapshot ds:snapshot.getChildren()){
                        if(ds.hasChildren()) {
                            Question_set data = new Question_set(ds.getKey(),String.valueOf(ds.child("Name").getValue()),
                                    String.valueOf(ds.getChildrenCount()),
                                    String.valueOf(ds.child("Difficulty").getValue()),"MCQ");
                            list.add(data);
                        }
                    }
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }
}