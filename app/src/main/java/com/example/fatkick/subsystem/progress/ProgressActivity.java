package com.example.fatkick.subsystem.progress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.fatkick.R;
import com.example.fatkick.subsystem.main.DailyActivity;
import com.example.fatkick.subsystem.storage.DailyActivityStorage;
import com.example.fatkick.subsystem.storage.ProgressInterface;
import com.example.fatkick.subsystem.storage.ProgressStorage;
import com.google.firebase.auth.FirebaseAuth;

public class ProgressActivity extends AppCompatActivity {
    DailyActivity dailyGoal;
    DailyActivity dailyCompletedActivity;
    DailyActivityStorage dailyActivityStorage;
    ProgressController progressController;
    ProgressReport progressReport;
    ProgressStorage progressStorage;
    SharedPreferences sp;

    TextView calorieProgress;
    TextView waterProgress;
    TextView sleepProgress;
    TextView activityProgress;
    TextView meditationProgress;
    TextView overallProgress;
    int days=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        bindUI();


        dailyActivityStorage = new DailyActivityStorage(getSharedPreferences("dailyGoal", MODE_PRIVATE), dailyGoal);
        dailyActivityStorage.loadData();
        dailyGoal = dailyActivityStorage.getDailyActivity();

        //dailyActivity->daily completed activity
        dailyActivityStorage = new DailyActivityStorage(getSharedPreferences("dailyActivity", MODE_PRIVATE), dailyCompletedActivity);
        dailyActivityStorage.loadData();
        dailyCompletedActivity = dailyActivityStorage.getDailyActivity();
        dailyCompletedActivity.calculateNetCal();

        progressController = new ProgressController(dailyGoal, dailyCompletedActivity);
        progressController.calculateProgress();

        progressReport = new ProgressReport();
        progressReport = progressController.getDailyProgressReport();

        ///save progress
        progressStorage = new ProgressStorage(FirebaseAuth.getInstance().getCurrentUser().getEmail(), progressReport);
        Log.i("tuba", "progress storage created");
        progressStorage.storeProgress();

        //retrieve progress
        progressStorage.readProgress(new ProgressInterface() {
            @Override
            public void onCallBack(ProgressReport progressReport) {
                if(progressReport.getDays() == 0){
                    overallProgress.setText("No Progress record found");
                }
                else {
                    Double totalProgress = ((progressReport.getCalorieIntakeProgress()+ progressReport.getActivityProgress()+
                            progressReport.getWaterIntakeProgress()+ progressReport.getSleepProgress()+
                            progressReport.getMeditationProgress())/progressReport.getDays());

                    showProgress(totalProgress, progressReport);

                }

            }

            private void showProgress(Double totalProgress, ProgressReport progressReport) {

                overallProgress.setText("Overall Progress of Last "+ progressReport.getDays()+ " Days: "+totalProgress+ "%");
                calorieProgress.setText("Calorie Intake Progress: " + progressReport.getCalorieIntakeProgress()+"%");
                activityProgress.setText("Activity Progress: " + progressReport.getActivityProgress()+"%");
                waterProgress.setText("Water Intake Progress: " + progressReport.getWaterIntakeProgress()+"%");
                sleepProgress.setText("Sleep Progress: " + progressReport.getSleepProgress()+"%");
                meditationProgress.setText("Meditation Progress: " + progressReport.getMeditationProgress()+"%");
            }
        });






    }

    private void bindUI() {
        overallProgress = (TextView) findViewById(R.id.tv_overallProgress);
        calorieProgress = (TextView) findViewById(R.id.tv_calorieProgress);
        activityProgress = (TextView) findViewById(R.id.tv_activityProgress);
        sleepProgress = (TextView) findViewById(R.id.tv_sleepProgress);
        waterProgress = (TextView) findViewById(R.id.tv_waterProgress);
        meditationProgress = (TextView) findViewById(R.id.tv_meditationProgress);

        dailyGoal = new DailyActivity();
        dailyCompletedActivity = new DailyActivity();
    }
}