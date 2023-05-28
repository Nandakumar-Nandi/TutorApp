package com.example.tutorapp.adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.example.tutorapp.R;
import com.example.tutorapp.modelclass.question_model_class;

import java.util.ArrayList;

public class question_model_adapter extends RecyclerView.Adapter<question_model_adapter.viewHolder> {
    ArrayList<question_model_class> list;
    Context context;
    public question_model_adapter(ArrayList<question_model_class> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public question_model_adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.questionl3,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull question_model_adapter.viewHolder holder, int position) {
        question_model_class data=list.get(position);
        holder.Question.setText(data.getQuestion());
        holder.Answer.setText(data.getAnswer());
        if(data.getChoice1().equals("")){
            holder.ch1t.setVisibility(View.GONE);
            holder.ch2t.setVisibility(View.GONE);
            holder.ch3t.setVisibility(View.GONE);
            holder.Ch1.setVisibility(View.GONE);
            holder.Ch2.setVisibility(View.GONE);
            holder.Ch3.setVisibility(View.GONE);
        }
        else {
            holder.Ch1.setText(data.getChoice1());
            holder.Ch2.setText(data.getChoice2());
            holder.Ch3.setText(data.getChoice3());
        }
        holder.dropdown.setImageResource(R.drawable.dropdown);
        holder.dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.layout.getVisibility()!=View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(holder.layout,new AutoTransition());
                    holder.dropdown.setImageResource(R.drawable.dropup);
                    holder.layout.setVisibility(View.VISIBLE);
                }
                else{
                    TransitionManager.beginDelayedTransition(holder.layout,new AutoTransition());
                    holder.dropdown.setImageResource(R.drawable.dropdown);
                    holder.layout.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView Question,Answer,Ch1,Ch2,Ch3,ch1t,ch2t,ch3t;
        ImageView dropdown;
        ConstraintLayout layout;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            Question=itemView.findViewById(R.id.question);
            Answer=itemView.findViewById(R.id.answer);
            Ch1=itemView.findViewById(R.id.choice1);
            Ch2=itemView.findViewById(R.id.choice2);
            Ch3=itemView.findViewById(R.id.choice3);
            ch1t=itemView.findViewById(R.id.textView16);
            ch2t=itemView.findViewById(R.id.textView17);
            ch3t=itemView.findViewById(R.id.textView18);
            dropdown=itemView.findViewById(R.id.dropdown);
            layout=itemView.findViewById(R.id.qmodel_clayout);
        }
    }
}
