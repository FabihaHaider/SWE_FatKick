package com.example.fatkick.subsystem.goal_setting;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fatkick.R;
import com.example.fatkick.subsystem.authenticator.Authenticator;
import com.example.fatkick.subsystem.authenticator.User;
import com.example.fatkick.subsystem.main.MainActivity;
import com.example.fatkick.subsystem.main.MyCallBack;
import com.example.fatkick.subsystem.storage.GoalStorage;
import com.example.fatkick.subsystem.storage.UserInterface;
import com.example.fatkick.subsystem.storage.UserStorage;


public class FinalGoalActivity extends AppCompatActivity {

    private TextView tvOutput;
    private Button btSaveGoal, btSuggestGoal, btContinue;
    private EditText etWeight;
    private FinalGoal finalGoal;
    private User user;
    private FinalGoalManager finalGoalManager;
    private View.OnClickListener btSaveGoal_onClickListener, btSuggestGoal_onClickListener, btContinueOnClickListener ;
    private Authenticator authenticator;
    private UserStorage userStorage;
    private GoalStorage goalStorage;
    private String weight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_goal);

        setTitle("Set Goal");

        bindUI();

        getCurrentUser();

        btSaveGoal.setOnClickListener(getBtSaveGoal_onClickListener());
        btSuggestGoal.setOnClickListener(getBtSuggestGoal_OnClickListener());
        btContinue.setOnClickListener(getBtContinue_onClickListener());

    }

    private void getCurrentUser() {
        String user_email = authenticator.getCurrentUser();

        userStorage.getUser(user_email, new UserInterface() {
            @Override
            public void onCallBack(User databaseUser) {
                user = databaseUser;
            }
        });
    }


    private View.OnClickListener getBtSuggestGoal_OnClickListener() {


        btSuggestGoal_onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finalGoalManager = new FinalGoalManager(user);
                tvOutput.setVisibility(View.VISIBLE);
                tvOutput.setText(finalGoalManager.suggestGoal());
            }
        };
        return btSuggestGoal_onClickListener;
    }

    public View.OnClickListener getBtSaveGoal_onClickListener() {

        btSaveGoal_onClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                if (validate()) {
                    Double fWeight = Double.parseDouble(weight);
                    finalGoal = new FinalGoal(user, fWeight);

                    finalGoalManager = new FinalGoalManager(user, finalGoal);
                    String goalVerdict = finalGoalManager.isGoalSafe();

                    if (goalVerdict.equals("safe goal")) {

                        //display time
                        //set continue enabled

                        btContinue.setEnabled(true);
                        btContinue.setVisibility(View.VISIBLE);
                        tvOutput.setVisibility(View.VISIBLE);
                        tvOutput.setText("Deadline: " + finalGoal.calculateDeadline().toString());
                    } else {
                        tvOutput.setVisibility(View.VISIBLE);
                        tvOutput.setText(goalVerdict);
                    }
                }
            }

        };
        return btSaveGoal_onClickListener;
    }

    private View.OnClickListener getBtContinue_onClickListener() {

        btContinueOnClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(validate())
                {
                    Double fWeight = Double.parseDouble(weight);
                    finalGoal = new FinalGoal(user, fWeight);
                    goalStorage = new GoalStorage(user, finalGoal);


                    goalStorage.storeGoal(new MyCallBack() {
                        @Override
                        public void onCallback(String value) {
                            Toast.makeText(FinalGoalActivity.this, value, Toast.LENGTH_SHORT).show();
                            Log.i("finalGoal", "onCallback: " + value);
                            if(value.equals("Goal saved successfully"))
                            {
                                Intent intent = new Intent(FinalGoalActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(FinalGoalActivity.this, "Try again.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        };

        return btContinueOnClickListener;

    }

    private boolean validate() {
        weight = etWeight.getText().toString();


        if(weight.isEmpty()){

            Toast.makeText(this, "Please enter weight", Toast.LENGTH_SHORT).show();

            return false;
        }

        return true;
    }

    private void bindUI() {
        etWeight = findViewById(R.id.et_weight);
        btSaveGoal = findViewById(R.id.bt_save_goal);
        tvOutput = findViewById(R.id.tv_output);
        btSuggestGoal = findViewById(R.id.bt_suggest_goal);
        btContinue = findViewById(R.id.bt_continue);
        authenticator = new Authenticator();
        userStorage = new UserStorage();
        tvOutput =findViewById(R.id.tv_output);
        user = new User();
        finalGoal = new FinalGoal();
    }


}