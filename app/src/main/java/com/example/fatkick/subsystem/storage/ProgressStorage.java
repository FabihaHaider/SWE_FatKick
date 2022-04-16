package com.example.fatkick.subsystem.storage;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.fatkick.subsystem.progress.ProgressReport;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProgressStorage {
    private String userEmail;
    private ProgressReport progressReport;
    private DatabaseReference databaseReference;

    public ProgressStorage(String userEmail, ProgressReport progressReport) {
        this.userEmail = userEmail;
        this.progressReport = progressReport;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("DailyProgress").child(userEmail.replace(".",""));
    }

    public ProgressStorage(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("DailyProgress");
    }

    public void storeProgress()
    {
        databaseReference.push().setValue(progressReport);
    }

    public void readProgress(ProgressInterface progressInterface)
    {   progressReport = new ProgressReport(0.0, 0.0, 0.0, 0.0, 0.0);
        Log.i("tuba", "blank report"+ progressReport.getCalorieIntakeProgress());
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
                        ProgressReport tmp = dataSnapshot.getValue(ProgressReport.class);

                        //Log.i("tuba", "from tmp"+tmp.getCalorieIntakeProgress());

                        progressReport.setCalorieIntakeProgress(progressReport.getCalorieIntakeProgress()+tmp.getCalorieIntakeProgress());
                        progressReport.setActivityProgress(progressReport.getActivityProgress()+tmp.getActivityProgress());
                        progressReport.setWaterIntakeProgress(progressReport.getWaterIntakeProgress()+tmp.getWaterIntakeProgress());
                        progressReport.setSleepProgress(progressReport.getSleepProgress()+tmp.getSleepProgress());
                        progressReport.setMeditationProgress(progressReport.getMeditationProgress()+tmp.getMeditationProgress());


                    }
                    else{
                        Log.i("tuba", "snapshot does not exist");
                    }
                }

                progressReport.avg(cnt);
                progressReport.setDays(cnt);
                progressInterface.onCallBack(progressReport);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("tuba", "onCancelled", error.toException());

            }
        });
    }

}
