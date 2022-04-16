
package com.example.fatkick.subsystem.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.fatkick.R;
import com.example.fatkick.subsystem.authenticator.Authenticator;
import com.example.fatkick.subsystem.authenticator.ShowProfileActivity;
import com.example.fatkick.subsystem.authenticator.SignUpLoginActivity;
import com.example.fatkick.subsystem.authenticator.User;
import com.example.fatkick.subsystem.goal_setting.ShowMyGoalActivity;
import com.example.fatkick.subsystem.progress.ProgressActivity;
import com.example.fatkick.subsystem.storage.DailyActivityStorage;
import com.example.fatkick.subsystem.storage.UserInterface;
import com.example.fatkick.subsystem.storage.UserStorage;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,DailyActivityController.DailyActivityCallback{

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Authenticator authenticator;
    private UserStorage userStorage;

    //initialization
    /* Later this is taken from database*/
    Number age=25;
    String gender = "male";
    Number height = 180; //in cm
    Number weight = 70;  //in kg
    String activity_level = "level_1";


    DailyActivityController dailyActivityController = new DailyActivityController();
    DailyActivityStorage dailyActivityStorage;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bindUI();

        isOnlineUser();

        //reset at the start of a day
        autoReset();


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        String user_email = authenticator.getCurrentUser().trim();

        ////
        userStorage.getUser(user_email, new UserInterface() {
            @Override
            public void onCallBack(User user) {
                try {
                    age= user.calculateAge();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                gender = user.getGender();
                height = user.getHeight();
                weight = user.getWeight();
                activity_level = "level_2";

                Log.i("tuba", age.toString()+gender+height.toString()+weight.toString()+activity_level);

                dailyActivityController.generateDailyActivity(age,gender,height,weight,activity_level);
                dailyActivityController.setActivityCallback(MainActivity.this);


            }
        });

    }


    ///api call synchronization
    @Override
    public void updateDailyActivity(@NonNull DailyActivity dailyActivity) {
        Log.i("tuba", dailyActivity.getCalorieIntake().toString()+" from update");

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //shared pref
                dailyActivityStorage = new DailyActivityStorage(sharedPref,dailyActivity);
                dailyActivityStorage.storeGoalData();
                Log.i("tuba", "daily activity stored");


            }
        });

    }

    //reset at 11.59 pm
    private void autoReset() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 0);

        if(cal.after(Calendar.getInstance())){
            long time= cal.getTimeInMillis();

            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            //creating a new intent specifying the broadcast receiver
            Intent i = new Intent(this, ScheduleReset.class);


            //creating a pending intent using the intent
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, i,0);

            //setting the repeating alarm that will be fired every day
            am.setRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY, pi);
            Log.i("tuba", "Alarm has set");

        }

    }


    private void isOnlineUser() {
        if(! authenticator.isActiveUser())
        {
            authenticator.logout();
            Log.i("main activity", "onStart: user not active" );
            Intent intent = new Intent(MainActivity.this, SignUpLoginActivity.class);
            startActivity(intent);
        }
        else
            Log.i("main activity", "onStart: user active");
    }

    @Override
    protected void onStart() {
        super.onStart();

        isOnlineUser();
    }


    private void bindUI() {
        this.toolbar = findViewById(R.id.toolbar);
        this.drawerLayout = findViewById(R.id.drawer_layout);
        this.navigationView = findViewById(R.id.navigatoin_view);
        this.authenticator = new Authenticator();

        userStorage = new UserStorage();
        sharedPref = getSharedPreferences("dailyGoal", MODE_PRIVATE);
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.finalGoalMenuItem:
                //view final goal
                Intent intent = new Intent(this, ShowMyGoalActivity.class);
                startActivity(intent);
                break;


            case R.id.viewActivityMenuItem:
                //view activity
                Intent intent3 = new Intent(this, ShowDailyActivity.class);
                startActivity(intent3);
                break;


            case R.id.stepCounterMenuItem:
                //step counter
                Intent intent4 = new Intent(this, StepCountActivity.class);
                startActivity(intent4);
                break;

            case R.id.updateActivityMenuItem:
                //update activity
                Intent intent5 = new Intent(this, UpdateDailyActivity.class);
                startActivity(intent5);
                break;

            case R.id.progressMenuItem:
                //progress
                Intent intent6 = new Intent(this, ProgressActivity.class);
                startActivity(intent6);
                break;


            case R.id.logoutMenuItem:
                //logout
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
                authenticator.logout();
                finish();
                Intent intent1 = new Intent(this, SignUpLoginActivity.class);
                startActivity(intent1);
                break;

            case R.id.profileMenuItem:
                Intent intent2 = new Intent(this, ShowProfileActivity.class);
                startActivity(intent2);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}