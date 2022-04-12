package com.example.fatkick.subsystem.authenticator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fatkick.R;
import com.example.fatkick.subsystem.main.MyCallBack;
import com.example.fatkick.subsystem.storage.UserStorage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class RegistrationActivity extends AppCompatActivity {
    private EditText etUsername, etEmail, etDOB, etWeight, etHeight, etPassword;
    private TextView tvBackToSignIn, tvWeightAlert, tvHeightAlert;
    private RadioButton btMale, btFemale;
    private Button btSignup;
    private User user;
    private Authenticator authenticator;
    private UserStorage userStorage;
    private final Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        setTitle("Register");

        bindUI();

        date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };

        btSignup.setOnClickListener(getBtSignup_OnClickListener());
        tvBackToSignIn.setOnClickListener(getTvBackToSignIn_OnClickListener());
        etDOB.setOnClickListener(getEtDOB_OnClickListener());

        etWeight.addTextChangedListener(getEtWeight_AddTextChangedListner());
        etHeight.addTextChangedListener(getEtHeight_AddTextChangedListner());

    }

    private TextWatcher getEtHeight_AddTextChangedListner() {
        TextWatcher etHeightTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String sHeight = etHeight.getText().toString().trim();
                if(! sHeight.isEmpty())
                {
                    Double dHeight = Double.parseDouble(sHeight);
                    if(dHeight < 130 || dHeight > 230) {
                        tvHeightAlert.setVisibility(View.VISIBLE);
                        tvHeightAlert.setText("Height should be in between 130 cm to 230 cm");
                        tvHeightAlert.setTextColor(Color.RED);
                    }
                    else
                    {
                        tvHeightAlert.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        return etHeightTextWatcher;
    }

    private TextWatcher getEtWeight_AddTextChangedListner() {
        TextWatcher etWeightTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String sWeight = etWeight.getText().toString().trim();
                if(! sWeight.isEmpty())
                {
                    Double dWeight = Double.parseDouble(sWeight);
                    if(dWeight < 40 || dWeight > 160) {
                        tvWeightAlert.setVisibility(View.VISIBLE);
                        tvWeightAlert.setText("Weight should be in between 40 kg to 160 kg");
                        tvWeightAlert.setTextColor(Color.RED);
                    }
                    else
                    {
                        tvWeightAlert.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        return etWeightTextWatcher;
    }

    private View.OnClickListener getTvBackToSignIn_OnClickListener(){
        View.OnClickListener tvBackToSignIn_OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, SignUpLoginActivity.class);
                startActivity(intent);
            }
        };

        return tvBackToSignIn_OnClickListener;
    }

    private View.OnClickListener getBtSignup_OnClickListener() {
        View.OnClickListener btSignup_OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){

                    authenticator = new Authenticator(user);
                    userStorage = new UserStorage(user);

                    authenticator.createUserAccount(new MyCallBack() {
                        @Override
                        public void onCallback(String value) {

                            if(value.equals("Verification has been sent")) {
                                Toast.makeText(RegistrationActivity.this, value, Toast.LENGTH_SHORT).show();
                                userStorage.storeUser();

                                finish();
                                Intent intent = new Intent(RegistrationActivity.this, SignUpLoginActivity.class);
                                startActivity(intent);
                            }

                        }
                    });

                }

            }
        };

        return btSignup_OnClickListener;
    }

    private View.OnClickListener getEtDOB_OnClickListener() {
        View.OnClickListener etDOB_OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar CurrentDate = Calendar.getInstance();
                int year = CurrentDate.get(Calendar.YEAR);
                int month = CurrentDate.get(Calendar.MONTH);
                int day = CurrentDate.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog=new DatePickerDialog(RegistrationActivity.this, date, year,
                        month, day);

                //following line to restrict future date selection
                datePickerDialog.getDatePicker().setMaxDate((long) (System.currentTimeMillis() -  (1000 * 60 * 60 * 24 * 365.25 * 16)));
                datePickerDialog.getDatePicker().setMinDate((long) (System.currentTimeMillis() -  (1000 * 60 * 60 * 24 * 365.25 * 80)));
                datePickerDialog.show();
            }
        };

        return etDOB_OnClickListener;
    }

    private void updateLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        etDOB.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void bindUI() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etEmail = findViewById(R.id.et_user_email);
        etDOB = findViewById(R.id.et_user_DOB);
        etWeight = findViewById(R.id.et_user_weight);
        etHeight = findViewById(R.id.et_user_height);
        btSignup = findViewById(R.id.bt_register);
        btMale = findViewById(R.id.radioMale);
        btFemale = findViewById(R.id.radioFemale);
        tvBackToSignIn = findViewById(R.id.tv_back_to_signIn);
        tvWeightAlert = findViewById(R.id.tv_weight_alert);
        tvHeightAlert = findViewById(R.id.tv_height_alert);
    }

    private Boolean validate() {
        Boolean result = false;

        String name = etUsername.getText().toString().trim();
        String user_password = etPassword.getText().toString().trim();
        String user_email = etEmail.getText().toString().trim();
        String user_DOB = etDOB.getText().toString().trim();
        String user_weight = etWeight.getText().toString().trim();
        String user_height = etHeight.getText().toString().trim();

        Double dHeight = Double.parseDouble(user_height);
        Double dWeight = Double.parseDouble(user_weight);

        if(name.isEmpty() || user_password.isEmpty() || user_email.isEmpty() || user_DOB.isEmpty() || user_weight.isEmpty() || user_height.isEmpty()){

            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }
        else if(user_password.length()<6){
            Toast.makeText(this, "Password must contain at least 6 characters",Toast.LENGTH_SHORT).show();
        }
        else if(dWeight < 40 || dWeight > 160)
        {
            tvWeightAlert.setVisibility(View.VISIBLE);
            tvWeightAlert.setText("Weight should be in between 40 kg to 160 kg");
            tvWeightAlert.setTextColor(Color.RED);

        }

        else if ( dHeight < 130 || dHeight > 230)
        {
            tvHeightAlert.setVisibility(View.VISIBLE);
            tvHeightAlert.setText("Height should be in between 130 cm to 230 cm");
            tvHeightAlert.setTextColor(Color.RED);
        }
        else{
            String gender = "female";

            if(btMale.isChecked())
            {
                gender = "male";
            }
            user = new User(name, user_DOB, Double.parseDouble(user_weight), Double.parseDouble(user_height),
                    gender, user_email);
            user.setPassword(user_password);
            result = true;
        }

        return result;
    }

}