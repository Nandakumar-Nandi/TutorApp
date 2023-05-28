package com.example.tutorapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorapp.R;
import com.example.tutorapp.admin.edit;
import com.example.tutorapp.modelclass.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class listadapter extends RecyclerView.Adapter<listadapter.ViewHolder> {

    ArrayList<user> list;
    Context context;
    public listadapter(Context context,ArrayList<user> list){
        this.context=context;
        this.list=list;
    }
    @NonNull
    @Override
    public listadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull listadapter.ViewHolder holder, int position) {
        user data=list.get(position);
        holder.name.setText(data.getName());
        holder.reg.setText(data.getReg_no());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref= FirebaseDatabase.getInstance().getReferenceFromUrl("https://tutorapp-7c7ab-default-rtdb.firebaseio.com/");
                ref.child("users").child(data.gettype()).child(data.getReg_no()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        DataSnapshot ds=task.getResult();
                        if(ds.exists()){
                            ref.child("users").child(data.gettype()).child(data.getReg_no()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                    context.startActivity(new Intent(context, com.example.tutorapp.admin.list.class));
                                }
                            });
                        }
                        else{
                            Toast.makeText(context, "Not Deleted!Something Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }            
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, edit.class);
                i.putExtra("Type",data.gettype());
                i.putExtra("Id",data.getReg_no());
                context.startActivity(i);
            }
        });
    }

    @Override

    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,reg;
        ImageView edit,delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            edit=itemView.findViewById(R.id.list_edit);
            delete=itemView.findViewById(R.id.list_delete);
            name=itemView.findViewById(R.id.list_name);
            reg=itemView.findViewById(R.id.list_reg);
        }

    }
}
