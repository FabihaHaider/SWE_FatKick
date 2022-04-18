package com.example.fatkick.subsystem.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;
import android.util.Log;


import com.example.fatkick.subsystem.authenticator.Authenticator;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;

public class DailyTaskReminderController {

    private Context context;
    private ArrayList<Integer>daysOfWeek = new ArrayList<>();
    private ArrayList<String>tasks = new ArrayList<>();
    private int whichTask;
    private int hour;
    private int min;
    private Authenticator authenticator;

    public DailyTaskReminderController(Context context, int whichTask) {
        this.context = context;
        this.whichTask = whichTask;
        authenticator = new Authenticator();

        daysOfWeek.add(Calendar.MONDAY);
        daysOfWeek.add(Calendar.TUESDAY);
        daysOfWeek.add(Calendar.WEDNESDAY);
        daysOfWeek.add(Calendar.THURSDAY);
        daysOfWeek.add(Calendar.FRIDAY);
        daysOfWeek.add(Calendar.SATURDAY);
        daysOfWeek.add(Calendar.SUNDAY);

        tasks.add("calorie intake");
        tasks.add("water intake");
        tasks.add("sleep");
        tasks.add("meditation");
        tasks.add("exercise");


    }


    public void setReminder(Calendar c)
    {
        this.hour = c.get(Calendar.HOUR_OF_DAY);
        this.min = c.get(Calendar.MINUTE);


        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
        intent.putExtra(AlarmClock.EXTRA_HOUR, hour);
        intent.putExtra(AlarmClock.EXTRA_MINUTES, min);
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, tasks.get(whichTask));
        intent.putExtra(AlarmClock.EXTRA_DAYS, daysOfWeek);
        intent.putExtra(AlarmClock.VALUE_RINGTONE_SILENT, true);
        intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);

        context.startActivity(intent);

        saveInLocalCache();

    }

    private void saveInLocalCache() {
        try(FileOutputStream fileOutputStream = context.openFileOutput("FILE_NAME"+authenticator.getCurrentUser()+whichTask, Context.MODE_PRIVATE)) {

            String savedData = "Alarm set for " + tasks.get(whichTask) + " at " + hour + ":" + min;
            fileOutputStream.write(savedData.getBytes(StandardCharsets.UTF_8));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String readData()
    {
        Log.i("read", "readData: reading data");
        String data = "";
        if(localCacheExists())
        {
            data = getData();
        }

        return data;
    }

    private String getData()
    {
        StringBuilder stringBuilder = new StringBuilder();
        String data = "";
        try {
            FileInputStream fileInputStream = context.openFileInput("FILE_NAME"+authenticator.getCurrentUser()+whichTask);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();


           while (line != null)
           {
               stringBuilder.append(line);
               line = bufferedReader.readLine();
           }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            data = stringBuilder.toString();
        }

        return data.replace("\n", "").replace("\r", "");
    }

    private boolean localCacheExists() {
        for (String fileName : context.fileList())
        {
            if(fileName.equals("FILE_NAME"+authenticator.getCurrentUser()+whichTask))
            {
                return  true;
            }
        }

        return false;
    }
}
