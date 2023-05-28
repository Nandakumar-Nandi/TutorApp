package com.example.tutorapp.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.tutorapp.R;
import com.example.tutorapp.adapters.listadapter;
import com.example.tutorapp.adapters.question_model_adapter;
import com.example.tutorapp.modelclass.question_model_class;
import com.google.android.gms.tasks.DuplicateTaskCompletionException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class questionmodel extends AppCompatActivity {
    DatabaseReference ref;
    DataSnapshot task_data;
    ArrayList<question_model_class> list;
    question_model_adapter adapter;
    ArrayAdapter<String> adapter2;
    RecyclerView recyclerView;
    AutoCompleteTextView dropdown;
    Button assign;
    String dept="",user;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionmodel);
        sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
        user=sharedPreferences.getString("Name","");
        dropdown=findViewById(R.id.dropdown_menu);
        assign=findViewById(R.id.assign);
        recyclerView=findViewById(R.id.qmodelrecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        adapter=new question_model_adapter(list,this);
        ref= FirebaseDatabase.getInstance().getReferenceFromUrl("https://tutorapp-7c7ab-default-rtdb.firebaseio.com/");
        ref.child("Class").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot ds = task.getResult();
                ArrayList<String> menu = new ArrayList<>();
                if (ds.exists()) {
                    for (DataSnapshot snapshot : ds.getChildren()) {
                        menu.add(String.valueOf(snapshot.getKey()));
                    }
                    adapter2= new ArrayAdapter<String>(questionmodel.this,R.layout.dropdownlayout1,menu);
                    dropdown.setAdapter(adapter2);
                }
            }
        });
        dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dept=adapterView.getItemAtPosition(i).toString();
            }
        });
        if(getIntent().getStringExtra("Category").equals("Custom")){
            ref.child("Tasks").child(getIntent().getStringExtra("Type")).child("Custom").child(user).child(getIntent().getStringExtra("Title")).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    task_data = task.getResult();
                    if (task_data.exists()) {
                        for (DataSnapshot snapshot : task_data.getChildren()) {
                            if (snapshot.hasChildren()) {
                                String Ques="",Answ="",D1="",D2="",D3="";
                                Ques=String.valueOf(snapshot.child("Question").getValue());
                                Answ=String.valueOf(snapshot.child("Answer").getValue());
                                if(snapshot.child("D1").exists()){
                                    D1=String.valueOf(snapshot.child("D1").getValue());
                                    D2=String.valueOf(snapshot.child("D2").getValue());
                                    D3=String.valueOf(snapshot.child("D3").getValue());
                                }
                                question_model_class data = new question_model_class(Ques,
                                        Answ,
                                        D1,D2,D3,getIntent().getStringExtra("Type"),questionmodel.this);
                                list.add(data);
                            }
                        }
                        recyclerView.setAdapter(adapter);
                    }
                }
            });
        }
        else {
            ref.child("Tasks").child(getIntent().getStringExtra("Type")).child("Default").child(getIntent().getStringExtra("Title")).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    task_data = task.getResult();
                    if (task_data.exists()) {
                        for (DataSnapshot snapshot : task_data.getChildren()) {
                            if (snapshot.hasChildren()) {
                                String Ques="",Answ="",D1="",D2="",D3="";
                                Ques=String.valueOf(snapshot.child("Question").getValue());
                                Answ=String.valueOf(snapshot.child("Answer").getValue());
                                if(snapshot.child("D1").exists()){
                                    D1=String.valueOf(snapshot.child("D1").getValue());
                                    D2=String.valueOf(snapshot.child("D2").getValue());
                                    D3=String.valueOf(snapshot.child("D3").getValue());
                                }
                                question_model_class data = new question_model_class(Ques,
                                        Answ,
                                        D1,D2,D3,getIntent().getStringExtra("Type"),questionmodel.this);
                                list.add(data);
                            }
                        }
                        recyclerView.setAdapter(adapter);
                    }
                }
            });
        }
        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dept.equals("")){
                    Toast.makeText(questionmodel.this, "Select a class to assign", Toast.LENGTH_SHORT).show();
                }
                else{
                    ref.child("Class").child(dept).child("tasks").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                                DataSnapshot ds=task.getResult();
                                Integer Child_count=0;
                                Child_count=Integer.parseInt(String.valueOf(ds.getChildrenCount()));
                                ref.child("Class").child(dept).child("tasks").child(String.valueOf(Child_count+1)).setValue(task_data.getValue());
                                ref.child("Class").child(dept).child("tasks").child(String.valueOf(Child_count+1)).child("Assigned By").setValue(user);
                                ref.child("Assigned").child(user).child(dept).child(String.valueOf(Child_count+1)).setValue("");
                                Toast.makeText(questionmodel.this, "Tasks Assigned Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(questionmodel.this,home_teacher.class));
                        }
                    });

                }
            }
        });
    }
}