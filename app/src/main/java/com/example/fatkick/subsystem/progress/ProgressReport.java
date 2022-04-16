package com.example.fatkick.subsystem.progress;

import android.util.Log;

import java.util.Date;

public class ProgressReport {
    String date;
    int days;
    Double calorieIntakeProgress;
    Double activityProgress;
    Double waterIntakeProgress;
    Double sleepProgress;
    Double meditationProgress;



    public ProgressReport(){
        Date curDate = new Date();
        this.date = curDate.toString();
        this.days = 1;

    }

    public ProgressReport(Double calorieIntakeProgress, Double activityProgress, Double waterIntakeProgress, Double sleepProgress, Double meditationProgress) {
        Date curDate = new Date();
        this.date = curDate.toString();
        this.days = 1;
        this.calorieIntakeProgress = calorieIntakeProgress;
        this.activityProgress = activityProgress;
        this.waterIntakeProgress = waterIntakeProgress;
        this.sleepProgress = sleepProgress;
        this.meditationProgress = meditationProgress;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public Double getCalorieIntakeProgress() {
        return calorieIntakeProgress;
    }

    public void setCalorieIntakeProgress(Double calorieIntakeProgress) {
        this.calorieIntakeProgress = calorieIntakeProgress;
    }

    public Double getActivityProgress() {
        return activityProgress;
    }

    public void setActivityProgress(Double activityProgress) {
        this.activityProgress = activityProgress;
    }

    public Double getWaterIntakeProgress() {
        return waterIntakeProgress;
    }

    public void setWaterIntakeProgress(Double waterIntakeProgress) {
        this.waterIntakeProgress = waterIntakeProgress;
    }

    public Double getSleepProgress() {
        return sleepProgress;
    }

    public void setSleepProgress(Double sleepProgress) {
        this.sleepProgress = sleepProgress;
    }

    public Double getMeditationProgress() {
        return meditationProgress;
    }

    public void setMeditationProgress(Double meditationProgress) {
        this.meditationProgress = meditationProgress;
    }

    public void avg(int cnt) {
        if(cnt ==0) {
            Log.i("tuba", "cnt 0" );
            return;
        }
        calorieIntakeProgress/=cnt;
        waterIntakeProgress/=cnt;
        activityProgress/=cnt;
        sleepProgress/=cnt;
        meditationProgress/=cnt;
    }
}
