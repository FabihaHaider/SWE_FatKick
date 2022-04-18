package com.example.fatkick.subsystem.reminder;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fatkick.R;

import java.util.ArrayList;

public class DailyTaskReminderActivity extends AppCompatActivity {

    private ImageView calorieOn, waterOn, sleepOn, meditationOn, exerciseOn;
    private ArrayList<ImageView> button;
    private TextView tvCalorie, tvWater, tvSleep, tvMeditation, tvExercise;
    private ArrayList<TextView> textview;
    private DailyTaskReminderController dailyTaskReminderController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        
        bindUI();

        getOnReminderOnClick();

        getLocalData();

    }

    private void getLocalData() {
        for(int which_task = 0; which_task<5; which_task ++)
        {
            dailyTaskReminderController = new DailyTaskReminderController(DailyTaskReminderActivity.this, which_task);
            String data = dailyTaskReminderController.readData();

            if(!data.isEmpty())
            {
                textview.get(which_task).setText(data);
            }
        }
    }


    private void getOnReminderOnClick() {

        for (int which_button = 0; which_button < 5; which_button ++)
        {
            button.get(which_button).setOnClickListener(getReminderON_OnClickListener(which_button));
        }
    }


    private View.OnClickListener getReminderON_OnClickListener(int which_task) {
        View.OnClickListener reminderOn_OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimerPicker(textview.get(which_task), DailyTaskReminderActivity.this, which_task);
                timePicker.show(getSupportFragmentManager(), "time picker");

            }
        };

        return reminderOn_OnClickListener;
    }



    private void bindUI() {
        calorieOn = findViewById(R.id.iv_calorie_reminder_on);
        waterOn = findViewById(R.id.iv_water_reminder_on);
        sleepOn = findViewById(R.id.iv_sleep_reminder_on);
        meditationOn = findViewById(R.id.iv_meditaion_reminder_on);
        exerciseOn = findViewById(R.id.iv_exercise_reminder_on);


        tvCalorie = findViewById(R.id.tv_calorie_reminder);
        tvWater = findViewById(R.id.tv_water_reminder);
        tvSleep = findViewById(R.id.tv_sleep_reminder);
        tvMeditation = findViewById(R.id.tv_meditation_reminder);
        tvExercise = findViewById(R.id.tv_exercise_reminder);

        button = new ArrayList<>();
        button.add(calorieOn);
        button.add(waterOn);
        button.add(sleepOn);
        button.add(meditationOn);
        button.add(exerciseOn);

        textview = new ArrayList<TextView>();
        textview.add(tvCalorie);
        textview.add(tvWater);
        textview.add(tvSleep);
        textview.add(tvMeditation);
        textview.add(tvExercise);


    }


}