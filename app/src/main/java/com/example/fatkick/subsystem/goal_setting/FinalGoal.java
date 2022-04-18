package com.example.fatkick.subsystem.goal_setting;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.fatkick.subsystem.authenticator.User;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;


public class FinalGoal {
    private User user;
    private double weight;
    private String deadline;
    private String sDeadline;
    private String userEmail;


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

    public String getDeadline() {
        return deadline;
    }


    public String calculateDeadline()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        long weeks = (long) (user.getWeight() - weight);
        long days = weeks * 7;
        cal.add(Calendar.DAY_OF_MONTH, (int) days);
        deadline = sdf.format(cal.getTime());
        return deadline;
    }

}
