package com.example.fatkick.subsystem.reminder;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Calendar;

public class TimerPicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private TextView textView;
    private Context context;
    private DailyTaskReminderController dailyTaskReminderController;
    private int task;
    private int hour;
    private int minute;
    private ArrayList<String> tasks = new ArrayList<>();

    public TimerPicker(TextView textView, Context context, int task)
    {
        this.textView = textView;
        this.context = context;
        this.task = task;

        tasks.add("calorie intake");
        tasks.add("water intake");
        tasks.add("sleep");
        tasks.add("meditation");
        tasks.add("exercise");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        this.hour = c.get(Calendar.HOUR_OF_DAY);
        this.minute = c.get(Calendar.MINUTE );

        return new TimePickerDialog(getActivity(), this, hour, minute+1, DateFormat.is24HourFormat(getActivity()));
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);


        updateTimeText(calendar,textView);

        dailyTaskReminderController = new DailyTaskReminderController(context, task);
        dailyTaskReminderController.setReminder(calendar);


    }

    private void updateTimeText(Calendar calendar, TextView textView) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        String timeText = "Alarm set for " + tasks.get(task) + " at " + hour + ":" + min;
        textView.setText(timeText);
    }
}


