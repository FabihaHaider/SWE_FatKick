package com.example.fatkick.subsystem.progress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.fatkick.R;
import com.example.fatkick.subsystem.authenticator.User;
import com.example.fatkick.subsystem.goal_setting.FinalGoalActivity;
import com.example.fatkick.subsystem.main.DailyActivity;
import com.example.fatkick.subsystem.main.MainActivity;
import com.example.fatkick.subsystem.main.MyCallBack;
import com.example.fatkick.subsystem.storage.DailyActivityStorage;
import com.example.fatkick.subsystem.storage.ProgressInterface;
import com.example.fatkick.subsystem.storage.ProgressStorage;
import com.example.fatkick.subsystem.storage.UserInterface;
import com.google.firebase.auth.FirebaseAuth;

public class ProgressActivity extends AppCompatActivity {
    DailyActivity dailyGoal;
    DailyActivity dailyCompletedActivity;
    DailyActivityStorage dailyActivityStorage;
    ProgressController progressController;
    DailyProgressReport dailyProgressReport;
    ProgressStorage progressStorage;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        dailyGoal = new DailyActivity();
        dailyCompletedActivity = new DailyActivity();
        dailyActivityStorage = new DailyActivityStorage(getSharedPreferences("dailyGoal", MODE_PRIVATE), dailyGoal);
        dailyActivityStorage.loadData();
        dailyGoal = dailyActivityStorage.getDailyActivity();

        dailyActivityStorage = new DailyActivityStorage(getSharedPreferences("dailyActivity", MODE_PRIVATE), dailyCompletedActivity);
        dailyActivityStorage.loadData();
        dailyCompletedActivity = dailyActivityStorage.getDailyActivity();

        progressController = new ProgressController(dailyGoal, dailyCompletedActivity);
        progressController.calculateProgress();

        dailyProgressReport = new DailyProgressReport();
        dailyProgressReport = progressController.getDailyProgressReport();

        ///save progress
        progressStorage = new ProgressStorage(FirebaseAuth.getInstance().getCurrentUser().getEmail(), dailyProgressReport);
        Log.i("tuba", "progress storage created");
        //progressStorage.storeProgress();

        //retrieve progress
        progressStorage.readProgress(new ProgressInterface() {
            @Override
            public void onCallBack(DailyProgressReport dailyProgress) {

                Log.i("tuba", "calProg"+ dailyProgress.getCalorieIntakeProgress());
                Log.i("tuba", "actvProg"+ dailyProgress.getActivityProgress());
                Log.i("tuba", "wtrProg"+dailyProgress.getWaterIntakeProgress());
                Log.i("tuba", "slpProg"+ dailyProgress.getSleepProgress());
                Log.i("tuba", "mdtProg"+dailyProgress.getMeditationProgress());

            }
        });






    }
}