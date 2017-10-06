package com.coolweather.anrdoid.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Hasee on 2017/10/6/0006.
 */

public class Weather {
    public String status;
    public   Basic basic;
    public  AQI aqi;
    public  Now now;
    public Suggestion suggestion;
    @SerializedName("daily_forecast")
    public List<Forecast> forecasts;


}


