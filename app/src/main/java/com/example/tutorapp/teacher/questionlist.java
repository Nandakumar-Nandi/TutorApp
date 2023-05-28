package com.example.tutorapp.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.example.tutorapp.R;
import com.example.tutorapp.adapters.Question_set_Adapter;
import com.example.tutorapp.modelclass.Question_set;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class questionlist extends AppCompatActivity {
    Question_set_Adapter adapter;
    ArrayList<Question_set> list;
    DatabaseReference ref;
    String type;
    RecyclerView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionlist);
        list=new ArrayList<>();
        adapter=new Question_set_Adapter(list,this);
        view =findViewById(R.id.question_recycler);
        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(this));
        type=getIntent().getStringExtra("Type");
        ref=FirebaseDatabase.getInstance().getReferenceFromUrl("https://tutorapp-7c7ab-default-rtdb.firebaseio.com/");
        ref.child("Tasks").child(type).child("Default").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot dataSnapshot=task.getResult();
                if(dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String title=String.valueOf(ds.child("Name").getValue());
                            String difficulty=String.valueOf(ds.child("Difficulty").getValue());
                            String count= String.valueOf(ds.getChildrenCount());
                            Question_set data=new Question_set(ds.getKey(),title,count,difficulty,type);
                            list.add(data);
                            view.setAdapter(adapter);
                    }
                }
            }
        });

    }
}