package com.example.fatkick.subsystem.reminder;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.fatkick.subsystem.goal_setting.FinalGoal;
import com.example.fatkick.subsystem.main.DailyActivity;
import com.example.fatkick.subsystem.progress.ProgressController;
import com.example.fatkick.subsystem.progress.ProgressReport;
import com.example.fatkick.subsystem.storage.DailyActivityStorage;
import com.example.fatkick.subsystem.storage.ProgressStorage;
import com.example.fatkick.subsystem.storage.StepCountStorage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.Calendar;

public class DatabaseUpdateReminder {
    private Context context;
    private DatabaseReference databaseReference;
    private DailyActivityStorage dailyActivityStorage;
    private DailyActivity dailyGoal;
    private DailyActivity dailyCompletedActivity;
    private ProgressReport progressReport;
    private ProgressController progressController;
    private ProgressStorage progressStorage;
    private StepCountStorage stepCountStorage;
    private SharedPreferences sharedpreferences;

    public DatabaseUpdateReminder(Context context) {
        this.context = context;
        //databaseReference = FirebaseDatabase.getInstance().getReference().child("message");
    }

    public void buildDatabaseUpdateNotification()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 0);

        if(calendar.after(Calendar.getInstance())) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, AlertReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            Log.i("tuba", "alarm has been set");
        }

    }


    public void updateDatabase()
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

        progressController.calculateProgress();
        progressReport = progressController.getDailyProgressReport();
        Log.i("tuba",progressReport.getCalorieIntakeProgress().toString());
        //save progress
        progressStorage = new ProgressStorage(FirebaseAuth.getInstance().getCurrentUser().getEmail(), progressReport);
        progressStorage.storeProgress();

        ////////////////////////update completed activity
        dailyActivityStorage.resetDailyActivity();
        dailyActivityStorage.storeData();

        /////////////////////////update step count
        sharedpreferences = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        stepCountStorage = new StepCountStorage(sharedpreferences);
        stepCountStorage.loadSteps();
        stepCountStorage.resetSteps();

    }

    private void initVar() {
        dailyGoal = new DailyActivity();
        dailyCompletedActivity = new DailyActivity();
        progressController = new ProgressController(dailyGoal, dailyCompletedActivity);
        progressReport = new ProgressReport();
    }
}
