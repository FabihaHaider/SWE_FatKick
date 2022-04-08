package com.example.fatkick.subsystem.authenticator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.fatkick.R;
import com.example.fatkick.subsystem.main.MainActivity;
import com.example.fatkick.subsystem.main.MyCallBack;

public class SignUpLoginActivity extends AppCompatActivity {

    private RelativeLayout relativeLayout1, relativeLayout2;
    private Button btSignUp, btLogin, btResetPass;
    private Authenticator authenticator;
    private EditText etEmail, etPassword;
    private String user_password, user_email;
    private Boolean FirstTime = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_login);


        if(isFirstTime())
            FirstTime = true;

        bindUI();

        handler.postDelayed(runnable, 2000);

        btLogin.setOnClickListener(getBtLogin_OnCLickListener());
        btSignUp.setOnClickListener(getBtSignUp_OnClickListener());
        btResetPass.setOnClickListener(getBtResetPass_OnClicklistener());
    }

    private boolean isFirstTime() {
        SharedPreferences settings = getSharedPreferences("MyPrefs", 0);

        if (settings.getBoolean("is_first_time", true)) {
            //the app is being launched for first time, do something
            // first time task
            // record the fact that the app has been started at least once
            settings.edit().putBoolean("is_first_time", false).commit();
            return true;
        }
        else
        {
            return false;
        }
    }


    private View.OnClickListener getBtResetPass_OnClicklistener() {
        View.OnClickListener btResetPass_OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpLoginActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
            }
        };
        return  btResetPass_OnClickListener;
    }

    private View.OnClickListener getBtSignUp_OnClickListener() {
        View.OnClickListener btSignup_OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpLoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        };

        return btSignup_OnClickListener;
    }

    private View.OnClickListener getBtLogin_OnCLickListener() {
        View.OnClickListener btLogin_OnClicListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate())
                {
                    authenticator.LoginUser(user_email, user_password, new MyCallBack() {
                        @Override
                        public void onCallback(String value) {

                            Toast.makeText(SignUpLoginActivity.this, value, Toast.LENGTH_SHORT).show();

                            if(value.equals("Logged in successfully."))
                            {
                                finish();

                                Class SecondClass = MainActivity.class;
//                                if(FirstTime)
//                                {
//                                    SecondClass = FinalGoalActivity.class;
//                                }
                                Intent intent = new Intent(SignUpLoginActivity.this, SecondClass).putExtra("FirstTime", FirstTime);
                                startActivity(intent);
                            }

                        }
                    });
                }
            }
        };

        return btLogin_OnClicListener;
    }

    private void bindUI() {
        relativeLayout1 = findViewById(R.id.relative_layout1);
        relativeLayout2 = findViewById(R.id.relative_layout2);
        btSignUp = findViewById(R.id.bt_signup);
        btLogin = findViewById(R.id.bt_login);
        btResetPass = findViewById(R.id.bt_forgot_password);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        authenticator = new Authenticator();

    }


    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    relativeLayout1.setVisibility(View.VISIBLE);
                    relativeLayout2.setVisibility(View.VISIBLE);
                }
            }, 1000);
        }
    };


    private Boolean validate() {
        Boolean result = false;

        user_password = etPassword.getText().toString();
        user_email = etEmail.getText().toString();


        if(user_password.isEmpty() || user_email.isEmpty()){

            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }
        else{
            result = true;
        }

        return result;
    }

}