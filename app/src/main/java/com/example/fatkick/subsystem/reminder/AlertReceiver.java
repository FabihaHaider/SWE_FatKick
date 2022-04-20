package com.example.fatkick.subsystem.reminder;

import static android.content.Context.MODE_PRIVATE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.fatkick.subsystem.main.DailyActivity;
import com.example.fatkick.subsystem.progress.ProgressController;
import com.example.fatkick.subsystem.progress.ProgressReport;
import com.example.fatkick.subsystem.storage.DailyActivityStorage;
import com.example.fatkick.subsystem.storage.ProgressStorage;
import com.example.fatkick.subsystem.storage.StepCountStorage;
import com.google.firebase.auth.FirebaseAuth;

public class AlertReceiver extends BroadcastReceiver {
    private DatabaseUpdateReminder databaseUpdateReminder;
    private DailyActivityStorage dailyActivityStorage;
    private DailyActivity dailyGoal;
    private DailyActivity dailyCompletedActivity;
    private ProgressReport progressReport;
    private ProgressController progressController;
    private ProgressStorage progressStorage;
    private StepCountStorage stepCountStorage;
    private SharedPreferences sharedpreferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        //databaseUpdateReminder = new DatabaseUpdateReminder(context);
        //databaseUpdateReminder.updateDatabase();

        Log.i("tuba", "alarm has been fired");
        updateDatabase(context);


        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, nb.build());
    }
    public void updateDatabase(Context context)
    {
        initVar();
        //calculate progress and update daily progress database
        dailyActivityStorage = new DailyActivityStorage(context.getSharedPreferences("dailyGoal", MODE_PRIVATE), dailyGoal);
        dailyActivityStorage.loadData();
        dailyGoal = dailyActivityStorage.getDailyActivity();

        //dailyActivity->daily completed activity
        dailyActivityStorage = new DailyActivityStorage(context.getSharedPreferences("dailyActivity", MODE_PRIVATE), dailyCompletedActivity);
        dailyActivityStorage.loadData();
        dailyCompletedActivity = dailyActivityStorage.getDailyActivity();
        dailyCompletedActivity.calculateNetCal();

        progressController = new ProgressController(dailyGoal, dailyCompletedActivity);
        progressController.calculateProgress();
        progressReport = progressController.getDailyProgressReport();
        //save progress
        progressStorage = new ProgressStorage(FirebaseAuth.getInstance().getCurrentUser().getEmail(), progressReport);
        progressStorage.storeProgress();

        ////////////////////////update completed activity
        dailyActivityStorage.resetDailyActivity();
        dailyActivityStorage.storeData();

        /////////////////////////update step count
        stepCountStorage = new StepCountStorage(context.getSharedPreferences("myPref", MODE_PRIVATE));
        stepCountStorage.resetSteps();
        Log.i("tuba", "reset success");

    }

    private void initVar() {
        dailyGoal = new DailyActivity();
        dailyCompletedActivity = new DailyActivity();
        progressReport = new ProgressReport();
    }

}
