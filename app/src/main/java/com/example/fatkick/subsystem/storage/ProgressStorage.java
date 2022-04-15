package com.example.fatkick.subsystem.storage;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.fatkick.subsystem.authenticator.User;
import com.example.fatkick.subsystem.goal_setting.FinalGoal;
import com.example.fatkick.subsystem.main.MyCallBack;
import com.example.fatkick.subsystem.progress.DailyProgressReport;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProgressStorage {
    private String userEmail;
    private DailyProgressReport dailyProgressReport;
    private DatabaseReference databaseReference;

    public ProgressStorage(String userEmail, DailyProgressReport dailyProgressReport) {
        this.userEmail = userEmail;
        this.dailyProgressReport = dailyProgressReport;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("DailyProgress").child(userEmail.replace(".",""));
    }

    public ProgressStorage(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("DailyProgress");
    }

    public void storeProgress()
    {
        databaseReference.push().setValue(dailyProgressReport);
    }

    public void readProgress(ProgressInterface progressInterface)
    {   dailyProgressReport = new DailyProgressReport(0.0, 0.0, 0.0, 0.0, 0.0);
        Log.i("tuba", "blank report"+ dailyProgressReport.getCalorieIntakeProgress());
        Log.i("tuba", "beforeAddValueEvent");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int cnt=0;
                Log.i("tuba", "beforeDataSnapshot"+cnt);
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Log.i("tuba", "indatasnapshot");
                    if (snapshot.exists()) {
                        cnt++;
                        DailyProgressReport tmp = dataSnapshot.getValue(DailyProgressReport.class);

                        //Log.i("tuba", "from tmp"+tmp.getCalorieIntakeProgress());

                        dailyProgressReport.setCalorieIntakeProgress(dailyProgressReport.getCalorieIntakeProgress()+tmp.getCalorieIntakeProgress());
                        dailyProgressReport.setActivityProgress(dailyProgressReport.getActivityProgress()+tmp.getActivityProgress());
                        dailyProgressReport.setWaterIntakeProgress(dailyProgressReport.getWaterIntakeProgress()+tmp.getWaterIntakeProgress());
                        dailyProgressReport.setSleepProgress(dailyProgressReport.getSleepProgress()+tmp.getSleepProgress());
                        dailyProgressReport.setMeditationProgress(dailyProgressReport.getMeditationProgress()+tmp.getMeditationProgress());


                    }
                    else{
                        Log.i("tuba", "snapshot does not exist");
                    }
                }

                dailyProgressReport.avg(cnt);
                progressInterface.onCallBack(dailyProgressReport);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("tuba", "onCancelled", error.toException());

            }
        });
    }

}
