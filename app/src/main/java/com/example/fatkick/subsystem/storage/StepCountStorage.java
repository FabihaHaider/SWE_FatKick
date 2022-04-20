package com.example.fatkick.subsystem.storage;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.fatkick.subsystem.main.StepCountActivity;

public class StepCountStorage {
    SharedPreferences sharedPreferences;
    float totalCount;
    float prevCount;

    public StepCountStorage(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        loadSteps();
    }

    public StepCountStorage(SharedPreferences sharedPreferences, float curCount, float prevCount) {
        this.sharedPreferences = sharedPreferences;
        this.totalCount = curCount;
        this.prevCount = prevCount;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public float getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(float totalCount) {
        this.totalCount = totalCount;
    }

    public float getPrevCount() {
        return prevCount;
    }

    public void setPrevCount(float prevCount) {
        this.prevCount = prevCount;
    }

    public void saveTotalSteps(){
        //save data for totalSteps
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("key2", totalCount);
        editor.apply();
    }

    public void savePrevSteps(){
        //save data for totalSteps
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("key1", prevCount);
        editor.apply();
    }

    public void loadSteps(){
        prevCount = sharedPreferences.getFloat("key1", 0);
        totalCount = sharedPreferences.getFloat("key2", 0);
    }

    public void resetSteps(){
        loadSteps();
        Log.i("tuba", prevCount+" "+totalCount);
        prevCount = totalCount;
        //saveData;
        savePrevSteps(); //worked?
        Log.i("tuba", "prev count saved");
        //StepCountActivity.tvSteps.setText("0");

    }
}
