package com.example.fatkick.subsystem.authenticator;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.fatkick.subsystem.main.MyCallBack;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Authenticator {
    private FirebaseAuth firebaseAuth;
    private User user;

    public Authenticator(User user) {
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.user = user;
    }

    public Authenticator(){

        this.firebaseAuth = FirebaseAuth.getInstance();
    }


    public void createUserAccount(MyCallBack myCallBack) {

        firebaseAuth.createUserWithEmailAndPassword(user.getEmail() , user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    sendEmailVerification(myCallBack);
                }
                else
                {
                    myCallBack.onCallback("Account registration failed");
                }
            }
        });

    }

    private void sendEmailVerification(MyCallBack myCallBack) {

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        myCallBack.onCallback("Verification has been sent");
                    }
                    else
                    {
                        myCallBack.onCallback("Error occurred while sending mail");
                    }
                }
            });
        }
    }

    public boolean isActiveUser()
    {
        FirebaseUser USER = firebaseAuth.getCurrentUser();
        if(USER == null)
        {
            return false;
        }
        return true;
    }


    public void LoginUser(String email, String password, MyCallBack myCallBack) {

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                checkEmailVerification(myCallBack);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                myCallBack.onCallback("Enter valid email and password");

            }
        });
    }

    private void checkEmailVerification(MyCallBack myCallBack) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser.isEmailVerified())
        {
            myCallBack.onCallback("Logged in successfully.");
        }
        else{
            myCallBack.onCallback("Please verify your email.");
        }
    }

    public void ResetPass(String email, MyCallBack myCallBack) {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    myCallBack.onCallback("Password reset email sent");
                }
                else
                {
                    myCallBack.onCallback("Could not send email");
                }
            }
        });
    }

    public String getCurrentUser()
    {
        String email = firebaseAuth.getCurrentUser().getEmail();
        return email;
    }

    public void logout() {
        firebaseAuth.signOut();
    }
}
