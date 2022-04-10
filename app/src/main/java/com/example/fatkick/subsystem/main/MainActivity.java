package com.example.fatkick.subsystem.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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
import com.example.fatkick.subsystem.goal_setting.ShowMyGoalActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Authenticator authenticator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bindUI();

        isOnlineUser();

        if(isFirstTime())
            authenticator.logout();


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

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

    private void isOnlineUser() {
        if(! authenticator.isActiveUser())
        {
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