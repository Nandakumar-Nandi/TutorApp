package com.example.tutorapp.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorapp.R;
import com.example.tutorapp.modelclass.question_model_class;
import com.example.tutorapp.teacher.questionmodel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

public class attend_task extends AppCompatActivity {
    TextView Question,Count,Title;
    RadioButton B1,B2,B3,B4;
    Button Next;
    RadioGroup AnswerGroup;
    DatabaseReference ref;
    DataSnapshot ds,s1;
    SharedPreferences sharedPreferences;
    String userclass,username,task_title;
    Integer qcount;
    ArrayList<question_model_class> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend_task);
        Question = findViewById(R.id.Question);
        Count = findViewById(R.id.Count);
        Title=findViewById(R.id.QuizName);
        AnswerGroup = findViewById(R.id.AnswerGroup);
        B1 = findViewById(R.id.radioButton1);
        B2 = findViewById(R.id.radioButton2);
        B3 = findViewById(R.id.radioButton3);
        B4 = findViewById(R.id.radioButton4);
        Next = findViewById(R.id.nextButton);
        list=new ArrayList<>();
        sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
        userclass=sharedPreferences.getString("Class","");
        username=sharedPreferences.getString("Name","");
        task_title=getIntent().getStringExtra("Title");
        Title.setText(task_title);
        ref= FirebaseDatabase.getInstance().getReferenceFromUrl("https://tutorapp-7c7ab-default-rtdb.firebaseio.com/");
        ref.child("Class").child(userclass).child("tasks").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                ds=task.getResult();
                for(DataSnapshot s:ds.getChildren()) {
                    if(task_title.equals(s.child("Name").getValue().toString())) {
                        s1=s;
                        for (DataSnapshot snapshot : s.getChildren()) {
                            if (snapshot.hasChildren()) {
                                question_model_class data = new question_model_class(String.valueOf(snapshot.child("Question").getValue()),
                                        String.valueOf(snapshot.child("Answer").getValue()),
                                        String.valueOf(snapshot.child("D1").getValue()),
                                        String.valueOf(snapshot.child("D2").getValue()),
                                        String.valueOf(snapshot.child("D3").getValue()),
                                        "",attend_task.this);
                                list.add(data);
                            }
                        }
                    }
                }
                qcount= list.size();
                ref.child("Class").child(userclass).child("students").child(username).child(s1.getKey()).child("Count").setValue(qcount);
                ref.child("Class").child(userclass).child("students").child(username).child(s1.getKey()).child("Name").setValue(s1.child("Name").getValue().toString());
                ref.child("Class").child(userclass).child("students").child(username).child(s1.getKey()).child("Difficulty").setValue(s1.child("Difficulty").getValue().toString());
                ref.child("Class").child(userclass).child("students").child(username).child(s1.getKey()).child("Score").setValue("0");
                loadquestion(0);
            }
        });

    }
    String A1,A2,A3,A4,useranswer;
    Integer Score=0;
    private void loadquestion(Integer n) {
        Count.setText((n+1)+"/"+qcount);
        question_model_class data1=list.get(n);
        Question.setText(data1.getQuestion());
        A1=data1.getAnswer();
        A2=data1.getChoice1();
        A3=data1.getChoice2();
        A4=data1.getChoice3();
        int max = 4;
        String[] data=new String[]{"","","",""};
        while(data[0].equals("") || data[1].equals("") || data[2].equals("") || data[3].equals("")){
            Integer rand =(int)Math.floor(Math.random() * max);
            if(data[rand]==""){
                if(!A1.equals("")){
                    data[rand]=A1;
                    A1="";
                }
                else if(!A2.equals("")){
                    data[rand]=A2;
                    A2="";
                }
                else if(!A3.equals("")){
                    data[rand]=A3;
                    A3="";
                }
                else if(!A4.equals("")){
                    data[rand]=A4;
                    A4="";
                }
            }
        }
        B1.setText(data[0]);
        B2.setText(data[1]);
        B3.setText(data[2]);
        B4.setText(data[3]);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(AnswerGroup.getCheckedRadioButtonId()){
                    case R.id.radioButton1:useranswer=B1.getText().toString();
                        break;
                    case R.id.radioButton2:useranswer=B2.getText().toString();
                        break;
                    case R.id.radioButton3:useranswer=B3.getText().toString();
                        break;
                    case R.id.radioButton4:useranswer=B4.getText().toString();
                        break;
                    default:
                        Toast.makeText(attend_task.this, "Not answered", Toast.LENGTH_SHORT).show();
                        break;
                }
                if(useranswer.equals(data1.getAnswer())){
                    Score++;
                    ref.child("Class").child(userclass).child("students").child(username).child(s1.getKey()).child("Score").setValue(Score);
                }
                AnswerGroup.clearCheck();
                ref.child("Class").child(userclass).child("students").child(username).child(s1.getKey()).child("Q"+(n+1)).child("Question").setValue(data1.getQuestion());
                ref.child("Class").child(userclass).child("students").child(username).child(s1.getKey()).child("Q"+(n+1)).child("Correct Answer").setValue(data1.getAnswer());
                ref.child("Class").child(userclass).child("students").child(username).child(s1.getKey()).child("Q"+(n+1)).child("Answer").setValue(useranswer);
                if(qcount<=n+(1)){
                    Toast.makeText(attend_task.this, "You Completed the task", Toast.LENGTH_SHORT).show();
                    ref.child("Assigned").child(s1.child("Assigned By").getValue().toString()).child(s1.getKey()).child(username).setValue(Score+"/"+qcount);
                   Intent i =new Intent(attend_task.this,Score.class);
                    i.putExtra("Title",task_title);
                    startActivity(i);
                    finish();
                }
                else {
                    loadquestion(n + 1);
                }
            }
        });
    }
}
