package com.example.tutorapp.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tutorapp.R;
import com.example.tutorapp.modelclass.Question_set;
import com.example.tutorapp.student.Score;
import com.example.tutorapp.student.attend_short;
import com.example.tutorapp.student.attend_task;
import com.example.tutorapp.student.history_stud;
import com.example.tutorapp.student.home_student;
import com.example.tutorapp.teacher.create;
import com.example.tutorapp.teacher.create_q_n_a;
import com.example.tutorapp.teacher.home_teacher;
import com.example.tutorapp.teacher.questionmodel;
import com.example.tutorapp.teacher.select_category;
import com.example.tutorapp.teacher.students;

import java.util.ArrayList;

public class Question_set_Adapter extends RecyclerView.Adapter<Question_set_Adapter.Myviewholder> {
    ArrayList<Question_set> list;
    Context context;
    public Question_set_Adapter(ArrayList<Question_set> list, Context context) {
        this.list=list;
        this.context=context;
    }
    @Override
    public Question_set_Adapter.Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view=LayoutInflater.from(context).inflate(R.layout.questionl2,parent,false);
        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Question_set_Adapter.Myviewholder holder, int position) {
        Question_set data=list.get(position);
        holder.title.setText(data.getTitle());
        holder.qcount.setText(data.getCount());
        holder.difficulty.setText(data.getDifficulty());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(context.getClass()==home_student.class){
                    if(data.getType().equals("Short")){
                        Intent i = new Intent(context, attend_short.class);
                        i.putExtra("Title", data.getTitle());
                        context.startActivity(i);
                    }
                    else{
                        Intent i = new Intent(context, attend_task.class);
                        i.putExtra("Title", data.getTitle());
                        context.startActivity(i);
                    }
                }
                else if(context.getClass()== history_stud.class){
                    Intent i = new Intent(context, Score.class);
                    i.putExtra("Title", data.getTitle());
                    context.startActivity(i);
                }
                else if(context.getClass() == create_q_n_a.class){
                    Intent i = new Intent(context, questionmodel.class);
                    i.putExtra("Category", "Custom");
                    i.putExtra("Type", "MCQ");
                    i.putExtra("Title", data.getTitle());
                    context.startActivity(i);
                }
                else if(context.getClass() == home_teacher.class)
                {
                    Intent i = new Intent(context, students.class);
                    i.putExtra("id", data.getId());
                    context.startActivity(i);
                }
                else if(context.getClass() == create.class){
                    Intent i = new Intent(context, questionmodel.class);
                    i.putExtra("Category", "Custom");
                    i.putExtra("Type", "MCQ");
                    i.putExtra("Title", data.getTitle());
                    context.startActivity(i);
                }
                else{
                    Intent i = new Intent(context, questionmodel.class);
                    i.putExtra("Category", "Default");
                    if(data.getType().equals("Short")) {
                        i.putExtra("Type", "Short");
                    }
                    else{
                        i.putExtra("Type", "MCQ");
                    }
                    i.putExtra("Title", data.getTitle());
                    context.startActivity(i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        TextView title,qcount,difficulty;
        ConstraintLayout layout;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            layout=itemView.findViewById(R.id.clayout);
            title=itemView.findViewById(R.id.Title);
            qcount=itemView.findViewById(R.id.Count);
            difficulty=itemView.findViewById(R.id.Difficulty);
        }
    }
}
