package com.example.tutorapp.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tutorapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class adduser extends AppCompatActivity {
    Button add;
    TextInputLayout dept;
    TextInputEditText name,reg,c_lass,password,department;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adduser);
        add=findViewById(R.id.adduser_add);
        name=findViewById(R.id.add_name);
        reg=findViewById(R.id.add_Regno);
        c_lass=findViewById(R.id.add_class);
        password=findViewById(R.id.add_password);
        if(getIntent().getStringExtra("Type").equals("teachers")){
            dept=findViewById(R.id.dept);
            dept.setHint("Enter Department");
            department=findViewById(R.id.add_class);
        }
        ref= FirebaseDatabase.getInstance().getReferenceFromUrl("https://tutorapp-7c7ab-default-rtdb.firebaseio.com/");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIntent().getStringExtra("Type").equals("teachers")){
                    ref.child("users").child(getIntent().getStringExtra("Type")).child(reg.getText().toString()).child("Name").setValue(name.getText().toString());
                    ref.child("users").child(getIntent().getStringExtra("Type")).child(reg.getText().toString()).child("Reg_no").setValue(reg.getText().toString());
                    ref.child("users").child(getIntent().getStringExtra("Type")).child(reg.getText().toString()).child("Department").setValue(department.getText().toString());
                    ref.child("users").child(getIntent().getStringExtra("Type")).child(reg.getText().toString()).child("Password").setValue(password.getText().toString());
                    Toast.makeText(adduser.this, "Added Successfuly", Toast.LENGTH_SHORT).show();
                }
                else{
                    ref.child("users").child(getIntent().getStringExtra("Type")).child(reg.getText().toString()).child("Name").setValue(name.getText().toString());
                    ref.child("users").child(getIntent().getStringExtra("Type")).child(reg.getText().toString()).child("Reg_no").setValue(reg.getText().toString());
                    ref.child("users").child(getIntent().getStringExtra("Type")).child(reg.getText().toString()).child("Class").setValue(c_lass.getText().toString());
                    ref.child("users").child(getIntent().getStringExtra("Type")).child(reg.getText().toString()).child("Password").setValue(password.getText().toString());
                    ref.child("Class").child(c_lass.getText().toString()).child("students").child(reg.getText().toString()).setValue("");
                    Toast.makeText(adduser.this, "Added Successfuly", Toast.LENGTH_SHORT).show();
                }}
        });
    }
}