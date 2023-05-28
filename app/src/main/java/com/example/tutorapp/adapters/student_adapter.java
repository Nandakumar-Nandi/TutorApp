package com.example.tutorapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorapp.R;
import com.example.tutorapp.modelclass.student_class;
import com.example.tutorapp.student.Score;

import java.util.ArrayList;

public class student_adapter extends RecyclerView.Adapter<student_adapter.ViewHolder> {
    ArrayList<student_class> list;
    Context context;

    public student_adapter(ArrayList<student_class> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public student_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.student_list,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull student_adapter.ViewHolder holder, int position) {
        student_class data=list.get(position);
        holder.name.setText(data.getName());
        holder.score.setText(data.getScore());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, Score.class);
                i.putExtra("Type","teacher");
                i.putExtra("Name",data.getName());
                i.putExtra("Class",data.getuserClass());
                i.putExtra("Title",data.getTitle());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,score;
        ConstraintLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.student_list_id);
            score=itemView.findViewById(R.id.student_list_score);
            layout=itemView.findViewById(R.id.student_list_layout);
        }
    }
}
