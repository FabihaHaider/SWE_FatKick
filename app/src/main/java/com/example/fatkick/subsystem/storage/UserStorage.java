package com.example.fatkick.subsystem.storage;

import androidx.annotation.NonNull;

import com.example.fatkick.subsystem.authenticator.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserStorage {

    private User user;

    private DatabaseReference databaseReference;

    public UserStorage(User user) {
        this.user = user;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("UserAccount");
    }

    public UserStorage()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("UserAccount");
    }

    public void storeUser()
    {

        databaseReference.push().setValue(user);
    }

    public void getUser(String user_email, UserInterface userInterface)
    {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {

                    if (snapshot.exists()) {
                        User user;

                        String email = dataSnapshot.child("email").getValue().toString();

                        if (email.equals(user_email)) {
                            String name = dataSnapshot.child("name").getValue().toString();
                            String DOB = dataSnapshot.child("dob").getValue().toString();
                            Double weight = Double.parseDouble(dataSnapshot.child("weight").getValue().toString());
                            Double height = Double.parseDouble(dataSnapshot.child("height").getValue().toString());
                            String gender = dataSnapshot.child("gender").getValue().toString();

                            user = new User(name, DOB, weight, height, gender, email);

                            userInterface.onCallBack(user);

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
