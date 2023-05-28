
package com.example.tutorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tutorapp.admin.admin;
import com.example.tutorapp.student.home_student;
import com.example.tutorapp.teacher.create_short_answer;
import com.example.tutorapp.teacher.home_teacher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login extends AppCompatActivity {
    FirebaseUser muser;
    DatabaseReference ref;
    TextInputEditText user,pass;
    Button login;
    String username,password;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://tutorapp-7c7ab-default-rtdb.firebaseio.com/");
        user = findViewById(R.id.log_username);
        pass = findViewById(R.id.log_password);
        login = findViewById(R.id.log_login);
        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        editor = sharedPreferences.edit();
            if (!sharedPreferences.getString("Name", "").isEmpty()) {
                startService(new Intent(this,Notification.class));
                if (sharedPreferences.getString("Type", "").equals("Admin")) {
                    startActivity(new Intent(login.this, admin.class));
                    finish();
                } else if (sharedPreferences.getString("Type", "").equals("Professor")) {
                    startActivity(new Intent(login.this, home_teacher.class));
                    finish();
                } else if (sharedPreferences.getString("Type", "").equals("Student")) {
                    startActivity(new Intent(login.this, home_student.class));
                    finish();
                }
            }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }

    private void validate() {
        username=user.getText().toString();
        password=pass.getText().toString();
        ref.child("users").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot ds=task.getResult();
                if(ds.child("admin").hasChild(username)){
                    if(password.equals(String.valueOf(ds.child("admin").child(username).child("Password").getValue()))){
                        editor.putString("Name",username);
                        editor.putString("Type","Admin");
                        editor.commit();
                        startService(new Intent(login.this,Notification.class));
                        startActivity(new Intent(login.this,admin.class));
                        finish();
                    }
                    else{
                        Toast.makeText(login.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    }
                }else if(ds.child("teachers").hasChild(username)) {
                    if (password.equals(String.valueOf(ds.child("teachers").child(username).child("Password").getValue()))) {
                        editor.putString("Name",username);
                        editor.putString("Type","Professor");
                        editor.commit();
                        startService(new Intent(login.this,Notification.class));
                        startActivity(new Intent(login.this, home_teacher.class));
                        finish();
                    } else {
                        Toast.makeText(login.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    }
                }else if(ds.child("students").hasChild(username)) {
                    if (password.equals(String.valueOf(ds.child("students").child(username).child("Password").getValue()))) {
                        editor.putString("Name",username);
                        editor.putString("Type","Student");
                        editor.putString("Class",ds.child("students").child(username).child("Class").getValue().toString());
                        editor.commit();
                        startService(new Intent(login.this,Notification.class));
                        startActivity(new Intent(login.this, home_student.class));
                        finish();
                    } else {
                        Toast.makeText(login.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(login.this, "Account doesn't Exists", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}








