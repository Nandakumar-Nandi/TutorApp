package com.example.tutorapp.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tutorapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class edit extends AppCompatActivity {
    DatabaseReference ref;
    TextInputEditText name,roll,c_lass,password;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        name=findViewById(R.id.edit_name);
        c_lass=findViewById(R.id.edit_class);
        roll=findViewById(R.id.edit_roll);
        password=findViewById(R.id.edit_password);
        save=findViewById(R.id.edit_save);
        ref= FirebaseDatabase.getInstance().getReferenceFromUrl("https://tutorapp-7c7ab-default-rtdb.firebaseio.com/");
        ref.child("users").child(getIntent().getStringExtra("Type")).child(getIntent().getStringExtra("Id")).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                    DataSnapshot ds= task.getResult();
                    if(ds.exists()){
                        if(getIntent().getStringExtra("Type").equals("teachers")){
                            name.setText(ds.child("Name").getValue().toString());
                            roll.setText(ds.child("Reg_no").getValue().toString());
                            c_lass.setText(ds.child("Department").getValue().toString());
                            password.setText(ds.child("Password").getValue().toString());
                        }
                        else {
                            name.setText(ds.child("Name").getValue().toString());
                            roll.setText(ds.child("Reg_no").getValue().toString());
                            c_lass.setText(ds.child("Class").getValue().toString());
                            password.setText(ds.child("Password").getValue().toString());
                        }
                    }
                    else{
                        Toast.makeText(edit.this, "data not found", Toast.LENGTH_SHORT).show();
                    }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIntent().getStringExtra("Type").equals("teachers")){
                    ref.child("users").child(getIntent().getStringExtra("Type")).child(getIntent().getStringExtra("Id")).child("Name").setValue(name.getText().toString());
                    ref.child("users").child(getIntent().getStringExtra("Type")).child(getIntent().getStringExtra("Id")).child("Reg_no").setValue(roll.getText().toString());
                    ref.child("users").child(getIntent().getStringExtra("Type")).child(getIntent().getStringExtra("Id")).child("Department").setValue(c_lass.getText().toString());
                    ref.child("users").child(getIntent().getStringExtra("Type")).child(getIntent().getStringExtra("Id")).child("Password").setValue(password.getText().toString());
                    Toast.makeText(edit.this, "Changes saved", Toast.LENGTH_SHORT).show();
                }
                else{
                ref.child("users").child(getIntent().getStringExtra("Type")).child(getIntent().getStringExtra("Id")).child("Name").setValue(name.getText().toString());
                ref.child("users").child(getIntent().getStringExtra("Type")).child(getIntent().getStringExtra("Id")).child("Reg_no").setValue(roll.getText().toString());
                ref.child("users").child(getIntent().getStringExtra("Type")).child(getIntent().getStringExtra("Id")).child("Class").setValue(c_lass.getText().toString());
                ref.child("users").child(getIntent().getStringExtra("Type")).child(getIntent().getStringExtra("Id")).child("Password").setValue(password.getText().toString());
                Toast.makeText(edit.this, "Changes saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}