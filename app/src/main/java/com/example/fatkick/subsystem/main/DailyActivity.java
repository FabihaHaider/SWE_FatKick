package com.example.fatkick.subsystem.main;

public class DailyActivity {
    private Double calorieIntake; //in Kcal
    private String activityLevel; //
    private Double waterIntake;   //in L
    private Double sleep;         //in hour
    private Double meditation;    //in minutes


    private Exercise[] exercise; //idx 0-> swimming, idx 1 -> running, idx 2-> walking, idx 3-> cycling
    private int idx;

    public DailyActivity(Double calorieIntake, String activityLevel, Double waterIntake, Double sleep, Double meditation) {
        this.calorieIntake = calorieIntake;
        this.activityLevel = activityLevel;
        this.waterIntake = waterIntake;
        this.sleep = sleep;
        this.meditation = meditation;
        exercise = new Exercise[10];
        idx=0;
    }

    public Double getCalorieIntake() {
        return calorieIntake;
    }

    public void setCalorieIntake(Double calorieIntake) {
        this.calorieIntake = calorieIntake;
    }

    public String getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(String activityLevel) {
        this.activityLevel = activityLevel;
    }

    public Double getWaterIntake() {
        return waterIntake;
    }

    public void setWaterIntake(Double waterIntake) {
        this.waterIntake = waterIntake;
    }

    public Double getSleep() {
        return sleep;
    }

    public void setSleep(Double sleep) {
        this.sleep = sleep;
    }

    public Double getMeditation() {
        return meditation;
    }

    public void setMeditation(Double meditation) {
        this.meditation = meditation;
    }

    public Exercise[] getExercise() {
        return exercise;
    }

    public void setExercise(Exercise[] exercise) {
        this.exercise = exercise;
    }

    public void addExercise(Exercise exc){
        exercise[idx++] =  exc;

    }

}
