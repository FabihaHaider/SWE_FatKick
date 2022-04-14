package com.example.fatkick.subsystem.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fatkick.R;
import com.example.fatkick.subsystem.storage.DailyActivityStorage;

public class UpdateDailyActivity extends AppCompatActivity {

    Spinner spinnerActivityLevel;
    EditText calorieIntake;
    EditText waterIntake;
    EditText sleep;
    EditText meditation;

    EditText swimming;
    EditText running;
    EditText walking;
    EditText cycling;

    TextView stepInfo;
    CheckBox stepCheckout;
    Button saveButton;
    DailyActivity dailyActivity;
    DailyActivityStorage dailyActivityStorage;
    SharedPreferences sharedPref;


    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_daily);

        bindUI();

        dailyActivityStorage.loadData();
        dailyActivity = dailyActivityStorage.getDailyActivity();

        showData();

        saveButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                saveLocal();
                //daily activity -> completed daily activity
                dailyActivity.calculateNetCal();
                finish();


            }


            private void saveLocal() {
                String sCalIntake = calorieIntake.getText().toString().trim();
                String sWaterIntake =waterIntake.getText().toString().trim();
                String sActivityLevel =spinnerActivityLevel.getSelectedItem().toString().trim();
                String sMeditation =meditation.getText().toString().trim();
                String sSleep =sleep.getText().toString().trim();

                String sSwimming =swimming.getText().toString().trim();
                String sRunning =running.getText().toString().trim();
                String sWalking =walking.getText().toString().trim();
                String sCycling =cycling.getText().toString().trim();

                if(sCalIntake.equals("")||sActivityLevel.equals("")||sWaterIntake.equals("")||sMeditation.equals("")||sSleep.equals("")
                        ||sSwimming.equals("")||sRunning.equals("")||sWalking.equals("")||sCycling.equals("")) {

                    Toast.makeText(getApplicationContext(), "Please fill up all fields.", Toast.LENGTH_LONG).show();
                }

                else{
                    //step count activity
                    float time=0;
                    if(stepCheckout.isChecked()){
                        SharedPreferences sp = getSharedPreferences("myPref", Context.MODE_PRIVATE);
                        float prevSteps = sp.getFloat("key1", 0);
                        float totalSteps = sp.getFloat("key2", 0);
                        int completedSteps = (int) (totalSteps - prevSteps);
                        time = (float)completedSteps / 1000 * 10;

                        SharedPreferences.Editor editor = sp.edit();
                        editor.putFloat("key1", totalSteps);  //key1 is for prev total step, key2 is for currentSteps
                        editor.apply();
                        StepCountActivity.tvSteps.setText("0");

                    }


                    dailyActivity.setCalorieIntake(Double.parseDouble(calorieIntake.getText().toString().trim()));
                    dailyActivity.setActivityLevel(spinnerActivityLevel.getSelectedItem().toString().trim());
                    dailyActivity.setWaterIntake(Double.parseDouble(waterIntake.getText().toString().trim()));
                    dailyActivity.setMeditation(Double.parseDouble(meditation.getText().toString().trim()));
                    dailyActivity.setSleep(Double.parseDouble(sleep.getText().toString().trim()));

                    dailyActivity.addExercise(new Exercise("swimming", Double.parseDouble(swimming.getText().toString().trim())));
                    dailyActivity.addExercise(new Exercise("running", Double.parseDouble(running.getText().toString().trim())));
                    dailyActivity.addExercise(new Exercise("walking", Double.parseDouble(walking.getText().toString().trim())+time));
                    dailyActivity.addExercise(new Exercise("cycling", Double.parseDouble(cycling.getText().toString().trim())));


                    dailyActivityStorage.setDailyActivity(dailyActivity);
                    dailyActivityStorage.storeData();


                }
            }

        });

    }


    private void setTextSpinner() {
        if(dailyActivity.getActivityLevel().trim().equals("0%"))
            spinnerActivityLevel.setSelection(4);
        else if(dailyActivity.getActivityLevel().trim().equals("25%"))
            spinnerActivityLevel.setSelection(3);
        else if(dailyActivity.getActivityLevel().trim().equals("50%"))
            spinnerActivityLevel.setSelection(2);
        else if(dailyActivity.getActivityLevel().trim().equals("75%"))
            spinnerActivityLevel.setSelection(1);
        else if(dailyActivity.getActivityLevel().trim().equals("100%"))
            spinnerActivityLevel.setSelection(0);
    }


    private void showData() {
        calorieIntake.setText(dailyActivity.getCalorieIntake().toString());
        //spinner
        setTextSpinner();
        waterIntake.setText(dailyActivity.getWaterIntake().toString());
        meditation.setText(dailyActivity.getMeditation().toString());
        sleep.setText(dailyActivity.getSleep().toString());

        swimming.setText(dailyActivity.getExercise()[0].getDuration().toString());
        running.setText(dailyActivity.getExercise()[1].getDuration().toString());
        walking.setText(dailyActivity.getExercise()[2].getDuration().toString());
        cycling.setText(dailyActivity.getExercise()[3].getDuration().toString());

        //step information
        SharedPreferences sp = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        float prevSteps = sp.getFloat("key1", 0);
        float totalSteps = sp.getFloat("key2", 0);
        int completedSteps = (int) (totalSteps - prevSteps);
        float time = (float)completedSteps * 10/ 1000 ;
        stepInfo.setText("You have currently completed " + completedSteps + " steps\nwhich is equivalent to walking approximately " + time + " minutes.");

    }

    private void bindUI() {

        spinnerActivityLevel =findViewById(R.id.spinner);
        adapter=ArrayAdapter.createFromResource(this, R.array.activity_levels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerActivityLevel.setAdapter(adapter);

        calorieIntake =(EditText) findViewById(R.id.et_calorie_intake);
        waterIntake =(EditText) findViewById(R.id.et_water_intake);
        meditation =(EditText) findViewById(R.id.et_meditation);
        sleep =(EditText) findViewById(R.id.et_sleep);

        swimming =(EditText) findViewById(R.id.et_swimming);
        running =(EditText) findViewById(R.id.et_running);
        walking =(EditText) findViewById(R.id.et_walking);
        cycling =(EditText) findViewById(R.id.et_cycling);
        stepInfo = (TextView) findViewById(R.id.tv_step_info);
        stepCheckout = (CheckBox) findViewById(R.id.cb_steps);
        saveButton = (Button) findViewById(R.id.bt_save);

        dailyActivity= new DailyActivity(0.0, "0%", 0.0, 0.0, 0.0);
        sharedPref = getSharedPreferences("dailyActivity", MODE_PRIVATE);
        dailyActivityStorage = new DailyActivityStorage(sharedPref, dailyActivity);

    }
}