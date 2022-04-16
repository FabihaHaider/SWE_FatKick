package com.example.fatkick.subsystem.storage;

import android.content.SharedPreferences;

import com.example.fatkick.subsystem.main.DailyActivity;
import com.example.fatkick.subsystem.main.Exercise;

public class DailyActivityStorage {
    //this is for accessing local storage
    SharedPreferences sharedPref;
    DailyActivity dailyActivity;

    public DailyActivityStorage(SharedPreferences sharedPreferences, DailyActivity dailyActivity) {
        this.sharedPref = sharedPreferences;
        this.dailyActivity = dailyActivity;
    }

    public DailyActivity getDailyActivity() {
        return dailyActivity;
    }

    public void setDailyActivity(DailyActivity dailyActivity) {
        this.dailyActivity = dailyActivity;
    }

    public void loadData() {
        dailyActivity.setCalorieIntake((double) sharedPref.getFloat("calIntake", 0.0F));
        dailyActivity.setActivityLevel( sharedPref.getString("actv_level", "0%"));
        dailyActivity.setWaterIntake((double) sharedPref.getFloat("wtrIntake", 0.0F));
        dailyActivity.setMeditation((double) sharedPref.getFloat("meditation", 0.0F));
        dailyActivity.setSleep((double) sharedPref.getFloat("sleep", 0.0F));

        Exercise[] exercises = new Exercise[10];
        exercises[0] = new Exercise("swimming", (double) sharedPref.getFloat("swimming", 0.0F));
        exercises[1] = new Exercise("running", (double) sharedPref.getFloat("running", 0.0F));
        exercises[2] = new Exercise("walking", (double) sharedPref.getFloat("walking", 0.0F));
        exercises[3] = new Exercise("cycling", (double) sharedPref.getFloat("cycling", 0.0F));
        dailyActivity.setExercise(exercises);
    }

    public void storeData(){
        //save to local storage, store progress in firebase
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat("calIntake", this.dailyActivity.getCalorieIntake().floatValue());
        editor.putString("actv_level", this.dailyActivity.getActivityLevel().trim());
        editor.putFloat("wtrIntake", this.dailyActivity.getWaterIntake().floatValue());
        editor.putFloat("meditation", this.dailyActivity.getMeditation().floatValue());
        editor.putFloat("sleep", this.dailyActivity.getSleep().floatValue());

        editor.putFloat("swimming", this.dailyActivity.getExercise()[0].getDuration().floatValue());
        editor.putFloat("running", this.dailyActivity.getExercise()[1].getDuration().floatValue());
        editor.putFloat("walking", this.dailyActivity.getExercise()[2].getDuration().floatValue());
        editor.putFloat("cycling", this.dailyActivity.getExercise()[3].getDuration().floatValue());
        editor.apply();
    }

    public void storeGoalData(){
        //save to local storage, store progress in firebase
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat("calIntake", this.dailyActivity.getCalorieIntake().floatValue());
        editor.putString("actv_level", this.dailyActivity.getActivityLevel().trim());
        editor.putFloat("wtrIntake", this.dailyActivity.getWaterIntake().floatValue());
        editor.putFloat("meditation", this.dailyActivity.getMeditation().floatValue());
        editor.putFloat("sleep", this.dailyActivity.getSleep().floatValue());

        editor.apply();
    }

    public void loadGoalData() {
        dailyActivity.setCalorieIntake((double) sharedPref.getFloat("calIntake", 0.0F));
        dailyActivity.setActivityLevel( sharedPref.getString("actv_level", "0%"));
        dailyActivity.setWaterIntake((double) sharedPref.getFloat("wtrIntake", 0.0F));
        dailyActivity.setMeditation((double) sharedPref.getFloat("meditation", 0.0F));
        dailyActivity.setSleep((double) sharedPref.getFloat("sleep", 0.0F));

    }
}
