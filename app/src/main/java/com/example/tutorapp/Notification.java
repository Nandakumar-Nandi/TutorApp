package com.example.tutorapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.tutorapp.student.home_student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Notification extends Service {
    DatabaseReference ref= FirebaseDatabase.getInstance().getReferenceFromUrl("https://tutorapp-7c7ab-default-rtdb.firebaseio.com/");
    SharedPreferences sharedPreferences;
    String name,class_name;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("MyNotification", "Notification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager nmanager = getSystemService(NotificationManager.class);
            nmanager.createNotificationChannel(channel);
        }
        if(sharedPreferences.getString("Type","").equals("Professor")){

        }
        else if(sharedPreferences.getString("Type","").equals("Student")){
            name=sharedPreferences.getString("Name","");
            ref.child("users").child("students").child(name).child("Class").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    DataSnapshot ds=task.getResult();
                    if(ds.exists()){
                        class_name=ds.getValue().toString();
                        ref.child("Class").child(class_name).child("tasks").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                Toast.makeText(Notification.this, "added", Toast.LENGTH_SHORT).show();
                                setnotification(snapshot);
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    else{
                        Toast.makeText(Notification.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void setnotification(DataSnapshot data) {
        Intent intent = new Intent(this, home_student.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MyNotification");
        builder.setContentTitle("New Task");
        builder.setContentText(data.child("Name").getValue().toString()+"\n"+"Assigned by"+data.child("Assigned By").getValue().toString());
        builder.setSmallIcon(R.mipmap.logo);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(1, builder.build());

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}