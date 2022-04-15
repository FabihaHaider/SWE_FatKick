package com.example.fatkick.subsystem.progress;

import android.util.Log;

import com.example.fatkick.subsystem.main.DailyActivity;

public class ProgressController {
    DailyProgressReport dailyProgressReport = new DailyProgressReport();
    DailyActivity dailyGoal;
    DailyActivity dailyCompletedActivity;

    public ProgressController(DailyActivity dailyGoal, DailyActivity dailyCompletedActivity) {
        this.dailyGoal = dailyGoal;
        this.dailyCompletedActivity = dailyCompletedActivity;
    }

    public DailyProgressReport getDailyProgressReport() {
        return dailyProgressReport;
    }

    public void setDailyProgressReport(DailyProgressReport dailyProgressReport) {
        this.dailyProgressReport = dailyProgressReport;
    }

    public void calculateProgress(){
        //progress is calculated through percentage calculation
        Double percentage = dailyCompletedActivity.getCalorieIntake()-dailyGoal.getCalorieIntake();
        percentage= percentage/dailyGoal.getCalorieIntake();
        percentage= 100 - percentage;
        if(percentage<0.0) percentage = 0.0;
        if(percentage>100.0) percentage = 100.0;
        dailyProgressReport.setCalorieIntakeProgress(percentage);

        String actv_level = dailyCompletedActivity.getActivityLevel().substring(0,dailyCompletedActivity.getActivityLevel().length()-1);
        dailyProgressReport.setActivityProgress(Double.parseDouble(actv_level));

        percentage = dailyCompletedActivity.getWaterIntake()/dailyGoal.getWaterIntake()*100;
        if(percentage>100.0) percentage = 100.0;
        dailyProgressReport.setWaterIntakeProgress(percentage);

        percentage = dailyCompletedActivity.getSleep()/dailyGoal.getSleep()*100;
        if(percentage>100.0) percentage = 100.0;
        dailyProgressReport.setSleepProgress(percentage);

        percentage = dailyCompletedActivity.getMeditation()/dailyGoal.getMeditation()*100;
        if(percentage>100.0) percentage = 100.0;
        dailyProgressReport.setMeditationProgress(percentage);
    }

}
