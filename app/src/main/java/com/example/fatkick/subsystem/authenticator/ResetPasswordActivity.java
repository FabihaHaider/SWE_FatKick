package com.example.fatkick.subsystem.authenticator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fatkick.R;
import com.example.fatkick.subsystem.main.MyCallBack;


public class ResetPasswordActivity extends AppCompatActivity {

    private EditText etEmail;
    private Button btReset;
    private Authenticator authenticator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        setTitle("Reset Password");

        bindUI();

        btReset.setOnClickListener(getBtReset_OnClickListener());
    }

    private void bindUI() {
        etEmail = findViewById(R.id.etResetMail);
        btReset = findViewById(R.id.btResetPassword);
        authenticator = new Authenticator();
    }

    private View.OnClickListener getBtReset_OnClickListener() {
        View.OnClickListener btReset_OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticator.ResetPass(etEmail.getText().toString().trim(), new MyCallBack() {
                    @Override
                    public void onCallback(String value) {
                        Toast.makeText(ResetPasswordActivity.this, value, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        return btReset_OnClickListener;
    }
}