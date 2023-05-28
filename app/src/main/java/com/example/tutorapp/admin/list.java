package com.example.tutorapp.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tutorapp.R;
import com.example.tutorapp.adapters.listadapter;
import com.example.tutorapp.modelclass.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class list extends AppCompatActivity {
    DatabaseReference ref;
    ArrayList<user> list;
    listadapter adapter;
    String value;
    RecyclerView recyclerView;
    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        add=findViewById(R.id.add);
        recyclerView=findViewById(R.id.recyc);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        adapter=new listadapter(this,list);
        value=getIntent().getStringExtra("Value");
        ref=FirebaseDatabase.getInstance().getReferenceFromUrl("https://tutorapp-7c7ab-default-rtdb.firebaseio.com/");
        ref.child("users").child(value).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot ds=task.getResult();
                if(ds.exists()){
                for (DataSnapshot d: ds.getChildren()){
                    user data=new user(d.child("Name").getValue().toString(),value,d.child("Reg_no").getValue().toString());
                    list.add(data);
                    recyclerView.setAdapter(adapter);
                }
            }
                else{
                    Toast.makeText(list.this, "No user Found", Toast.LENGTH_SHORT).show();
                }}
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(list.this, adduser.class);
                i.putExtra("Type",value);
                startActivity(i);
            }
        });

    }
}