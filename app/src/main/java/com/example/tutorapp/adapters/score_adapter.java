package com.example.tutorapp.adapters;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorapp.MainActivity;
import com.example.tutorapp.R;
import com.example.tutorapp.login;
import com.example.tutorapp.modelclass.score_class;
import com.example.tutorapp.webscapping.search;

import java.util.ArrayList;

public class score_adapter extends RecyclerView.Adapter<score_adapter.ViewHolder> {
    ArrayList<score_class> list;
    Context context;

    public score_adapter(ArrayList<score_class> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public score_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.scorelayout,parent,false);
        return new ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull score_adapter.ViewHolder holder, int position) {
        score_class data=list.get(position);
        holder.Question.setText(data.getQuestion());
        holder.CAnswer.setText(data.getCorrect_answer());
        holder.Answer.setText(data.getUser_answer());
        if(holder.CAnswer.getText().toString().equals(holder.Answer.getText().toString())){
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.green));
        }
        else{
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.red));
        }
        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                holder.search.setVisibility(View.VISIBLE);
                Handler h=new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.search.setVisibility(View.GONE);
                    }
                },5000);
                return false;
            }
        });
        holder.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,search.class);
                i.putExtra("Question",data.getQuestion());
                i.putExtra("YourAnswer",data.getUser_answer());
                i.putExtra("GivenAnswer",data.getCorrect_answer());
                context.startActivity(i);
            }
        });


    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Question,Answer,CAnswer,search;
        ConstraintLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout=itemView.findViewById(R.id.score_layout);
            search=itemView.findViewById(R.id.search);
            Question=itemView.findViewById(R.id.score_question);
            Answer=itemView.findViewById(R.id.score_user_answer);
            CAnswer=itemView.findViewById(R.id.score_answer);
        }
    }
}
