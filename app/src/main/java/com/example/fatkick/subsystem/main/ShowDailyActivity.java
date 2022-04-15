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

public class ShowDailyActivity extends AppCompatActivity implements DailyActivityController.DailyActivityCallback{


    private Authenticator authenticator;
    private UserStorage userStorage;

    //initialization
    /* Later this is taken from database*/
    Number age=25;
    String gender = "male";
    Number height = 180; //in cm
    Number weight = 70;  //in kg
    String activity_level = "level_1";


    DailyActivityController dailyActivityController = new DailyActivityController();
    DailyActivityStorage dailyActivityStorage;
    SharedPreferences sharedPref;
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


        String user_email = authenticator.getCurrentUser().trim();
        Log.i("tuba", user_email);

        ////
        userStorage.getUser(user_email, new UserInterface() {
            @Override
            public void onCallBack(User user) {
                try {
                    age= user.calculateAge();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                gender = user.getGender();
                height = user.getHeight();
                weight = user.getWeight();
                activity_level = "level_2";

                Log.i("tuba", age.toString()+gender+height.toString()+weight.toString()+activity_level);

                dailyActivityController.generateDailyActivity(age,gender,height,weight,activity_level);
                dailyActivityController.setActivityCallback(ShowDailyActivity.this);


            }
        });



    }

    private void bindUI() {
        mtv_calorie_intake = (TextView) findViewById(R.id.tv_calorie_intake);
        mtv_activity_level = (TextView) findViewById(R.id.tv_activity_level);
        mtv_water_intake = (TextView) findViewById(R.id.tv_water_intake);
        mtv_meditation = (TextView) findViewById(R.id.tv_meditation);
        mtv_sleep = (TextView) findViewById(R.id.tv_sleep);

        userStorage = new UserStorage();
        authenticator = new Authenticator();
        sharedPref = getSharedPreferences("dailyGoal", MODE_PRIVATE);
    }

    @Override
    public void updateDailyActivity(DailyActivity dailyActivity) {
        Log.i("tuba", dailyActivity.getCalorieIntake().toString()+" from update");

        ShowDailyActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Handle UI here
                mtv_calorie_intake.setText("Calorie Intake(Net): " + String.format("%.2f", dailyActivity.getCalorieIntake())+" kcal");
                mtv_activity_level.setText("Activity Level: "+dailyActivity.getActivityLevel().toString());
                mtv_water_intake.setText("Water Intake: Try to drink at least "+dailyActivity.getWaterIntake().toString()+" liters");
                mtv_meditation.setText("Meditation: Try to meditate at least "+dailyActivity.getMeditation().toString()+" minutes");
                mtv_sleep.setText("Sleep: Try to sleep at least "+dailyActivity.getSleep().toString()+" hours");

                //shared pref
                dailyActivityStorage = new DailyActivityStorage(sharedPref,dailyActivity);
                dailyActivityStorage.storeGoalData();


            }
        });

    }
}