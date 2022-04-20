package com.example.fatkick.subsystem.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.fatkick.R;
import com.example.fatkick.subsystem.authenticator.Authenticator;
import com.example.fatkick.subsystem.authenticator.User;
import com.example.fatkick.subsystem.storage.DailyActivityStorage;
import com.example.fatkick.subsystem.storage.GoalStorage;
import com.example.fatkick.subsystem.storage.UserInterface;
import com.example.fatkick.subsystem.storage.UserStorage;

import java.text.ParseException;

public class ShowDailyActivity extends AppCompatActivity {

    DailyActivityStorage dailyActivityStorage;
    SharedPreferences sharedPref;
    DailyActivity dailyActivity;
    TextView mtv_calorie_intake;
    TextView mtv_activity_level;
    TextView mtv_water_intake;
    TextView mtv_meditation;
    TextView mtv_sleep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_daily);

        setTitle("Daily Activity");

        bindUI();

        dailyActivityStorage.loadGoalData();
        dailyActivity = dailyActivityStorage.getDailyActivity();

        mtv_calorie_intake.setText("Calorie Intake(Net): " + String.format("%.2f", dailyActivity.getCalorieIntake())+" kcal");
        mtv_activity_level.setText("Activity Level: "+dailyActivity.getActivityLevel().toString());
        mtv_water_intake.setText("Water Intake: Try to drink at least "+dailyActivity.getWaterIntake().toString()+" liters");
        mtv_meditation.setText("Meditation: Try to meditate at least "+dailyActivity.getMeditation().toString()+" minutes");
        mtv_sleep.setText("Sleep: Try to sleep at least "+dailyActivity.getSleep().toString()+" hours");


    }

    private void bindUI() {
        mtv_calorie_intake = (TextView) findViewById(R.id.tv_calorie_intake);
        mtv_activity_level = (TextView) findViewById(R.id.tv_activity_level);
        mtv_water_intake = (TextView) findViewById(R.id.tv_water_intake);
        mtv_meditation = (TextView) findViewById(R.id.tv_meditation);
        mtv_sleep = (TextView) findViewById(R.id.tv_sleep);

        sharedPref = getSharedPreferences("dailyGoal", MODE_PRIVATE);
        dailyActivity = new DailyActivity();
        dailyActivityStorage = new DailyActivityStorage(sharedPref,dailyActivity);
    }


}