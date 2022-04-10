package com.example.fatkick.subsystem.storage;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.fatkick.subsystem.authenticator.User;
import com.example.fatkick.subsystem.goal_setting.FinalGoal;
import com.example.fatkick.subsystem.main.MyCallBack;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class GoalStorage {
    private User user;
    private FinalGoal finalGoal;
    private DatabaseReference databaseReference;

    public GoalStorage(User user, FinalGoal finalGoal) {
        this.user = user;
        this.finalGoal = finalGoal;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("FinalGoal");
    }

    public GoalStorage(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("FinalGoal");
    }


    public void storeGoal(MyCallBack myCallBack)
    {
        FinalGoal finalGoal = new FinalGoal(user.getEmail(),  this.finalGoal.getWeight(), this.finalGoal.getDeadline().toString());

        databaseReference.push().setValue(finalGoal).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    myCallBack.onCallback("Goal saved successfully");
                }
                else
                {
                    myCallBack.onCallback("Goal not saved successfully. Try again.");
                }
            }
        });

    }

    public void readGoal(String userEmail, GoalInterface goalInterface)
    {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {

                    if (snapshot.exists()) {
                        FinalGoal finalGoal = new FinalGoal();

                        String email = dataSnapshot.child("userEmail").getValue().toString();

                        if (email.equals(userEmail)) {
                            String sDeadline = dataSnapshot.child("sDeadline").getValue().toString();
                            Double weight = Double.parseDouble(dataSnapshot.child("weight").getValue().toString());

                            finalGoal = new FinalGoal(email, weight, sDeadline);

                            goalInterface.onCallBack(finalGoal);

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void isGoalExists(String userEmail, MyCallBack myCallBack)
    {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {

                    if (snapshot.exists()) {
                        FinalGoal finalGoal = new FinalGoal();
                        myCallBack.onCallback("false");
                        String email = dataSnapshot.child("userEmail").getValue().toString();

                        if (email.equals(userEmail)) {
                            myCallBack.onCallback("true");
                            return;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
