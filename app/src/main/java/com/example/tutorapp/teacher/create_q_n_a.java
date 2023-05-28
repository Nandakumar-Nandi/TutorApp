package com.example.tutorapp.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tutorapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class create_q_n_a extends AppCompatActivity {
    TextInputEditText Question,Answer,D1,D2,D3;
    Button submit;
    DatabaseReference ref;
    String quizname,Username;
    SharedPreferences sharedPreferences;
    Integer TotalCount,CurrentCount=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_q_n_a);
        Question=findViewById(R.id.yourquestion);
        Answer=findViewById(R.id.answer);
        D1=findViewById(R.id.Dummy1);
        D2=findViewById(R.id.Dummy2);
        D3=findViewById(R.id.Dummy3);
        submit=findViewById(R.id.Submit);
        sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
        Username=sharedPreferences.getString("Name","");
        quizname=getIntent().getStringExtra("Quiz_name");
        TotalCount=Integer.parseInt(getIntent().getStringExtra("Question_count"));
        ref=FirebaseDatabase.getInstance().getReferenceFromUrl("https://tutorapp-7c7ab-default-rtdb.firebaseio.com/");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Q=Question.getText().toString();
                String A=Answer.getText().toString();
                String d1=D1.getText().toString();
                String d2=D2.getText().toString();
                String d3=D3.getText().toString();
                if(!Q.equals("") || !A.equals("") || !d1.equals("") || !d2.equals("") || !d3.equals("")){
                    String c=String.valueOf("Q"+CurrentCount);
                    ref.child("Tasks").child("MCQ").child("Custom").child(Username).child(quizname).child(c).child("Question").setValue(Q);
                    ref.child("Tasks").child("MCQ").child("Custom").child(Username).child(quizname).child(c).child("Answer").setValue(A);
                    ref.child("Tasks").child("MCQ").child("Custom").child(Username).child(quizname).child(c).child("D1").setValue(d1);
                    ref.child("Tasks").child("MCQ").child("Custom").child(Username).child(quizname).child(c).child("D2").setValue(d2);
                    ref.child("Tasks").child("MCQ").child("Custom").child(Username).child(quizname).child(c).child("D3").setValue(d3);
                    CurrentCount=CurrentCount+1;
                    if(CurrentCount>TotalCount){
                        Intent i=new Intent(create_q_n_a.this,questionmodel.class);
                        i.putExtra("Title",quizname);
                        i.putExtra("Category","Custom");
                        startActivity(i);
                    }
                    else{
                        Question.setText("");
                        Answer.setText("");
                        D1.setText("");
                        D2.setText("");
                        D3.setText("");
                    }
                }
                else{
                    Toast.makeText(create_q_n_a.this, "Please Fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}