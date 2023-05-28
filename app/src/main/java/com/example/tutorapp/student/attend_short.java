package com.example.tutorapp.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorapp.R;
import com.example.tutorapp.modelclass.question_model_class;
import com.example.tutorapp.modelclass.question_model_short_class;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class attend_short extends AppCompatActivity {
    TextView title,question,count;
    TextInputEditText answer;
    Button next;
    DatabaseReference ref;
    SharedPreferences sharedPreferences;
    String task_title,userclass,username;
    DataSnapshot ds,s1;
    ArrayList<question_model_short_class> list=new ArrayList<>();
    Integer qcount;
    Double Score=0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend_short);
        title=findViewById(R.id.attend_short_title);
        question=findViewById(R.id.attend_short_question);
        count=findViewById(R.id.attend_short_count);
        answer=findViewById(R.id.attend_short_answer);
        next=findViewById(R.id.attend_short_next);
        ref= FirebaseDatabase.getInstance().getReferenceFromUrl("https://tutorapp-7c7ab-default-rtdb.firebaseio.com/");
        sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
        userclass=sharedPreferences.getString("Class","");
        username=sharedPreferences.getString("Name","");
        task_title=getIntent().getStringExtra("Title");
        title.setText(task_title);
        ref.child("Class").child(userclass).child("tasks").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                ds=task.getResult();
                for(DataSnapshot s:ds.getChildren()) {
                    if(task_title.equals(s.child("Name").getValue().toString())) {
                        s1=s;
                        for (DataSnapshot snapshot : s.getChildren()) {
                            if (snapshot.hasChildren()) {
                                ArrayList<String> keyword_list=new ArrayList<>();
                                for(DataSnapshot ds:snapshot.child("Keywords").getChildren()){
                                    keyword_list.add(ds.getValue().toString());
                                }
                                question_model_short_class data = new question_model_short_class(String.valueOf(snapshot.child("Question").getValue()),
                                        String.valueOf(snapshot.child("Answer").getValue()),
                                        keyword_list);
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
    ArrayList<String> keywords=new ArrayList<>();
    private void loadquestion(int n) {
        question_model_short_class data=list.get(n);
        question.setText(data.getQuestion());
        keywords=data.getKeywords();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!answer.getText().toString().equals("")){
                    String X = answer.getText().toString();
                    String[] words = X.split("[\\s,.(\\[]+|(?=\\))");
                    Double listcount=(double)keywords.size(),answercount=0.0;
                    for (String word : words) {
                        if(keywords.contains(word)){
                            answercount=answercount+1.0;
                        }
                    }
                    Score=Score+(answercount/listcount);
                    Toast.makeText(attend_short.this, ""+Score, Toast.LENGTH_SHORT).show();
                    ref.child("Class").child(userclass).child("students").child(username).child(s1.getKey()).child("Q"+(n+1)).child("Question").setValue(data.getQuestion());
                    ref.child("Class").child(userclass).child("students").child(username).child(s1.getKey()).child("Q"+(n+1)).child("Correct Answer").setValue(data.getAnswer());
                    ref.child("Class").child(userclass).child("students").child(username).child(s1.getKey()).child("Q"+(n+1)).child("Answer").setValue(answer.getText().toString());
                    if(qcount<=n+(1)){
                        Toast.makeText(attend_short.this, "You Completed the task", Toast.LENGTH_SHORT).show();
                        ref.child("Assigned").child(s1.child("Assigned By").getValue().toString()).child(s1.getKey()).child(username).setValue(new DecimalFormat("$#.0").format(Score)+"/"+qcount);
                        Intent i =new Intent(attend_short.this,Score.class);
                        i.putExtra("Title",task_title);
                        startActivity(i);
                        finish();
                    }
                    else {
                        ref.child("Class").child(userclass).child("students").child(username).child(s1.getKey()).child("Score").setValue(new DecimalFormat("#.0").format(Score));
                        loadquestion(n + 1);
                    }

                }
            }
        });
    }
}