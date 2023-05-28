package com.example.tutorapp.adapters;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorapp.R;
import com.example.tutorapp.modelclass.score_class;

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

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Question,Answer,CAnswer;
        ConstraintLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout=itemView.findViewById(R.id.score_layout);
            Question=itemView.findViewById(R.id.score_question);
            Answer=itemView.findViewById(R.id.score_user_answer);
            CAnswer=itemView.findViewById(R.id.score_answer);
        }
    }
}
