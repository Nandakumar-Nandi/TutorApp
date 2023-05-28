package com.example.tutorapp.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.tutorapp.R;
import com.example.tutorapp.adapters.student_adapter;
import com.example.tutorapp.modelclass.student_class;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class students extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    DatabaseReference ref;
    RecyclerView recyclerView;
    String username,class_name,id;
    student_adapter adapter;
    ArrayList<student_class> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
        username=sharedPreferences.getString("Name","");
        id=getIntent().getStringExtra("id");
        recyclerView=findViewById(R.id.students_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        list=new ArrayList<>();
        adapter=new student_adapter(list,this);
        ref= FirebaseDatabase.getInstance().getReferenceFromUrl("https://tutorapp-7c7ab-default-rtdb.firebaseio.com/");
        ref.child("Assigned").child(username).child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot d= task.getResult();
                for(DataSnapshot snapshot:d.getChildren()){
                    ref.child("Class").child("MCA").child("students").child(snapshot.getKey()).child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                                DataSnapshot ds=task.getResult();
                                student_class data=new student_class(snapshot.getKey(),ds.child("Score").getValue().toString()+"/"+ds.child("Count").getValue().toString(),ds.child("Name").getValue().toString(),"MCA");
                                list.add(data);
                                recyclerView.setAdapter(adapter);
                        }
                    });
                }
            }
        });
    }
}