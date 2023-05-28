package com.example.tutorapp.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorapp.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class create_short_answer extends AppCompatActivity implements View.OnClickListener {
    ArrayList<String> stopWords;
    TextInputEditText question, answer;
    TextView title;
    ChipGroup chipgroup;
    Button submit,generate;
    DatabaseReference ref;
    Context context;
    ArrayList<String> result;
    String quiz_name,user;
    Integer quiz_count,current=0;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_short_answer);
        context = this;
        sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
        user=sharedPreferences.getString("Name","");
        quiz_count=Integer.parseInt(getIntent().getStringExtra("Question_count"));
        quiz_name=getIntent().getStringExtra("Quiz_name");
        stopWords = new ArrayList<>(Arrays.asList(
                "a", "an", "and", "are", "as", "at", "be", "by", "did", "do",
                "for", "from", "had", "has", "have", "how", "i", "if", "in", "is",
                "it", "its", "my", "of", "on", "or", "that", "the", "their",
                "them", "then", "there", "these", "they", "this", "to", "was",
                "we", "what", "when", "which", "who", "will", "with", "you", "your"
        ));
        result = new ArrayList<>();
        question = findViewById(R.id.create_short_quiz_question);
        title = findViewById(R.id.create_short_quiz_name);
        title.setText(quiz_name);
        answer = findViewById(R.id.create_short_quiz_answer);
        submit = findViewById(R.id.create_short_Submit);
        generate=findViewById(R.id.create_short_generate);
        chipgroup = findViewById(R.id.chipgroup);
        ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://tutorapp-7c7ab-default-rtdb.firebaseio.com/");
        ref.child("Tasks").child("Short").child("Custom").child(user).child(quiz_name).child("Type").setValue("Short");
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chipgroup.getClipChildren()) {
                    chipset();
                } else {
                    Toast.makeText(context, "Worked bro", Toast.LENGTH_SHORT).show();
                }
            }

        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chipgroup.getClipChildren()){
                    current++;
                    ref.child("Tasks").child("Short").child("Custom").child(user).child(quiz_name).child("Q"+current).child("Question").setValue(question.getText().toString());
                    ref.child("Tasks").child("Short").child("Custom").child(user).child(quiz_name).child("Q"+current).child("Answer").setValue(answer.getText().toString());
                    ref.child("Tasks").child("Short").child("Custom").child(user).child(quiz_name).child("Q"+current).child("Keywords").setValue(result);
                    result.clear();
                    question.setText("");
                    answer.setText("");
                    chipgroup.removeAllViews();
                    if(current==quiz_count){
                        Toast.makeText(context, "You are done here", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(context,questionmodel.class);
                        i.putExtra("Type","Short");
                        i.putExtra("Category","Custom");
                        i.putExtra("Title",quiz_name);
                        startActivity(i);
                    }

                }else{
                    Toast.makeText(context, "Generate Keywords first", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void chipset() {
        if (!answer.getText().equals("")) {
            String X = answer.getText().toString();
            String[] words = X.split("[\\s,.(\\[]+|(?=\\))");
            for (String word : words) {
                if (!stopWords.contains(word)) {
                    result.add(word.toLowerCase(Locale.ROOT));
                    Chip c = new Chip(context);
                    c.setText(word);
                    c.setTextColor(getResources().getColor(R.color.black));
                    c.setCloseIconVisible(true);
                    c.setOnCloseIconClickListener(this);
                    chipgroup.addView(c);
                }
            }


        }
    }

    @Override
    public void onClick(View view) {
        Chip c=(Chip) view;
        chipgroup.removeView(c);
        result.remove(c.getText());
    }
}