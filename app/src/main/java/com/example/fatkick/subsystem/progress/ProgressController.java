package com.example.fatkick.subsystem.progress;

import com.example.fatkick.subsystem.main.DailyActivity;

public class ProgressController {
    ProgressReport progressReport = new ProgressReport();
    DailyActivity dailyGoal;
    DailyActivity dailyCompletedActivity;

    public ProgressController(DailyActivity dailyGoal, DailyActivity dailyCompletedActivity) {
        this.dailyGoal = dailyGoal;
        this.dailyCompletedActivity = dailyCompletedActivity;
    }

    public ProgressReport getDailyProgressReport() {
        return progressReport;
    }

    public void setDailyProgressReport(ProgressReport progressReport) {
        this.progressReport = progressReport;
    }

    public void calculateProgress(){
        //progress is calculated through percentage calculation
        Double percentage = dailyCompletedActivity.getCalorieIntake()-dailyGoal.getCalorieIntake();
        percentage= percentage/dailyGoal.getCalorieIntake() * 100;
        percentage= 100 - percentage;
        if(percentage<0.0) percentage = 0.0;
        if(percentage>100.0) percentage = 100.0;
        progressReport.setCalorieIntakeProgress(percentage);

        String actv_level = dailyCompletedActivity.getActivityLevel().substring(0,dailyCompletedActivity.getActivityLevel().length()-1);
        progressReport.setActivityProgress(Double.parseDouble(actv_level));

        percentage = dailyCompletedActivity.getWaterIntake()/dailyGoal.getWaterIntake()*100;
        if(percentage>100.0) percentage = 100.0;
        progressReport.setWaterIntakeProgress(percentage);

        percentage = dailyCompletedActivity.getSleep()/dailyGoal.getSleep()*100;
        if(percentage>100.0) percentage = 100.0;
        progressReport.setSleepProgress(percentage);

        percentage = dailyCompletedActivity.getMeditation()/dailyGoal.getMeditation()*100;
        if(percentage>100.0) percentage = 100.0;
        progressReport.setMeditationProgress(percentage);
    }

}
