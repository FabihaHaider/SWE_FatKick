package com.example.fatkick.subsystem.main;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class DailyActivityController {
    private DailyActivity dailyActivity;
    DailyActivityCallback activityCallback;


    public DailyActivityController() {
        this.dailyActivity = new DailyActivity(0.0,"little or no activity", 2.0,6.0,1.0);

    }

    public void setActivityCallback(DailyActivityCallback dailyActivityCallback){
        this.activityCallback = dailyActivityCallback;
    }


    public DailyActivity getDailyActivity() {
        return dailyActivity;
    }

    public void setDailyActivity(DailyActivity dailyActivity) {
        this.dailyActivity = dailyActivity;
    }




    //////////////
    public void generateDailyActivity(Number age, String gender, Number height, Number weight, String activity_level ){

        //generate activity_level
        if(activity_level.trim().equals("level_1"))  dailyActivity.setActivityLevel("Try to exercise a little whenever you can");
        if(activity_level.trim().equals("level_2"))  dailyActivity.setActivityLevel("Exercise 1-3 times/week");
        if(activity_level.trim().equals("level_3"))  dailyActivity.setActivityLevel("Exercise 4-5 times/week");
        if(activity_level.trim().equals("level_4"))  dailyActivity.setActivityLevel("Daily Exercise or intense exercise 3-4 times/week");
        if(activity_level.trim().equals("level_5"))  dailyActivity.setActivityLevel("Intense exercise 6-7 times/week");
        if(activity_level.trim().equals("level_6"))  dailyActivity.setActivityLevel("Very intense exercise daily");


        //generate water intake
        if(gender.trim().equals("male")) dailyActivity.setWaterIntake(3.5);
        if(gender.trim().equals("female")) dailyActivity.setWaterIntake(2.5);

        //generate meditation
        dailyActivity.setMeditation(15.0);

        //generate sleep
        dailyActivity.setSleep(7.0);



        //generates calorie intake
        OkHttpClient client = new OkHttpClient();

        String url = "https://fitness-calculator.p.rapidapi.com/dailycalorie?age="+age+"&gender="+gender+"&height="+height+"&weight="+weight+"&activitylevel="+activity_level;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("X-RapidAPI-Host", "fitness-calculator.p.rapidapi.com")
                .addHeader("X-RapidAPI-Key", "51434c9f6fmshf357c3b5f512284p18ce3fjsnf60e0881670d")
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String stringResponse = response.body().string();

                    try {
                        JSONObject JSONresponse = new JSONObject(stringResponse);
                        Double cal1 = JSONresponse.getJSONObject("data").getJSONObject("goals")
                                .getJSONObject("Extreme weight loss").getDouble("calory");

                        dailyActivity.setCalorieIntake(cal1);
                        activityCallback.updateDailyActivity(dailyActivity);
                        Log.i("output", "daily activity created");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public interface DailyActivityCallback{
        void updateDailyActivity(DailyActivity dailyActivity);
    }
}
