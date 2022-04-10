package com.example.fatkick.subsystem.goal_setting;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.fatkick.subsystem.authenticator.User;

import java.time.LocalDate;


public class FinalGoal {
    private User user;
    private double weight;
    private LocalDate deadline;
    private String sDeadline;
    private String userEmail;


    @SuppressLint("NewApi")
    public FinalGoal(User user, double weight) {
        this.user = user;
        this.weight = weight;
        this.deadline = calculateDeadline();
    }

    public FinalGoal(String userEmail, double weight, String sDeadline) {
        this.weight = weight;
        this.sDeadline = sDeadline;
        this.userEmail = userEmail;
    }

    public FinalGoal(){}


    public String getsDeadline() {
        return sDeadline;
    }

    public void setsDeadline(String sDeadline) {
        this.sDeadline = sDeadline;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public LocalDate getDeadline() {
        return deadline;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDate calculateDeadline()
    {

        double weeks = user.getWeight() - weight;
        LocalDate today = LocalDate.now();
        deadline = today.plusDays(Math.round(weeks * 7));
        return deadline;
    }

}
