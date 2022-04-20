package com.example.fatkick.subsystem.main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fatkick.R;
import com.example.fatkick.subsystem.storage.StepCountStorage;

import java.util.Calendar;

public class StepCountActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    SharedPreferences sharedpreferences;
    StepCountStorage stepCountStorage;
    Boolean running= false;
    float totalSteps= 0.0F;
    float prevTotalSteps= 0.0F;
    public static TextView tvSteps;
    Button btReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_count);

        bindUI();


        //loadData
        stepCountStorage.loadSteps();
        Log.i("tuba", "stCnt"+ stepCountStorage.getPrevCount() + " "+ stepCountStorage.getTotalCount());
        prevTotalSteps = stepCountStorage.getPrevCount();

        //reset through button
        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stepCountStorage.resetSteps();
                tvSteps.setText("0");
            }
        });

    }



    private void bindUI() {

        tvSteps = (TextView) findViewById(R.id.tv_steps);
        btReset = (Button) findViewById(R.id.bt_reset);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sharedpreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        stepCountStorage = new StepCountStorage(sharedpreferences);
    }


    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor != null){
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        }
        else{
            Toast.makeText(this, "sensor not found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
        //sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (running){
            totalSteps = sensorEvent.values[0];
            //loadData;
            stepCountStorage.loadSteps();
            prevTotalSteps = stepCountStorage.getPrevCount();
            int currentSteps= (int) (totalSteps - prevTotalSteps);
            tvSteps.setText(Integer.toString(currentSteps));

            //save data for totalSteps
            stepCountStorage.setTotalCount(totalSteps);
            stepCountStorage.saveTotalSteps();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}