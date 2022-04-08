package com.example.fatkick.subsystem.goal_setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.fatkick.R;
import com.example.fatkick.subsystem.authenticator.Authenticator;
import com.example.fatkick.subsystem.storage.GoalInterface;
import com.example.fatkick.subsystem.storage.GoalStorage;


public class ShowMyGoalActivity extends AppCompatActivity {

    private GoalStorage goalStorage;
    private Authenticator authenticator;
    private EditText etShowWeight, etShowDeadline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_goal);

        setTitle("My Final Goal");

        bindUI();

        String user_email = authenticator.getCurrentUser();
        goalStorage.readGoal(user_email, new GoalInterface() {
            @Override
            public void onCallBack(FinalGoal finalGoal) {
                etShowWeight.setText("Weight: " + finalGoal.getWeight());
                etShowDeadline.setText("Deadline: " + finalGoal.getsDeadline() );
            }
        });
    }

    private void bindUI() {
        goalStorage = new GoalStorage();
        authenticator = new Authenticator();
        etShowWeight = findViewById(R.id.et_show_goal_weight);
        etShowDeadline = findViewById(R.id.et_show_goal_deadline);
    }
}