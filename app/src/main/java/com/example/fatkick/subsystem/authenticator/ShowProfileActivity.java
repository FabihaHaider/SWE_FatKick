package com.example.fatkick.subsystem.authenticator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.fatkick.R;
import com.example.fatkick.subsystem.storage.UserInterface;
import com.example.fatkick.subsystem.storage.UserStorage;


public class ShowProfileActivity extends AppCompatActivity {

    private Authenticator authenticator;
    private UserStorage userStorage;
    private EditText etName, etDOB, etWeight, etHeight, etGender, etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);

        setTitle("My Profile");

        bindUI();

        String user_email = authenticator.getCurrentUser();
        userStorage.getUser(user_email, new UserInterface() {
            @Override
            public void onCallBack(User user) {
                etName.setText("Name: " + user.getName());
                etDOB.setText("Date of birth: " + user.getDOB());
                etGender.setText("Gender: " + user.getGender());
                etWeight.setText("Weight: " + user.getWeight() + " kg");
                etHeight.setText("Height: " + user.getHeight() + " cm");
                etEmail.setText("Email: " + user.getEmail());
            }
        });

    }

    private void bindUI() {
        authenticator = new Authenticator();
        userStorage = new UserStorage();
        etName = findViewById(R.id.et_name);
        etDOB = findViewById(R.id.et_DOB);
        etGender = findViewById(R.id.et_gender);
        etWeight = findViewById(R.id.et_weight);
        etHeight = findViewById(R.id.et_height);
        etEmail = findViewById(R.id.et_email);
    }
}