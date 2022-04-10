package com.example.fatkick.subsystem.main;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;

import com.example.fatkick.R;

public class ScheduleReset extends BroadcastReceiver {
    //the method will be fired when the alarm is triggered
    @Override
    public void onReceive(Context context, Intent intent) {

        //you can check the log that it is fired
        //Here we are actually not doing anything
        //but you can do any task here that you want to be done at a specific time everyday
        //resetSteps();

        Log.i("tuba", "Alarm just fired");

        //load
        SharedPreferences sharedpreferences = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        float totalSteps = sharedpreferences.getFloat("key2", 0);
        //save data
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putFloat("key1", totalSteps);
        editor.apply();

        StepCountActivity.tvSteps.setText("0");
    }


}
