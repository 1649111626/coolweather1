package com.coolweather.anrdoid.gson;

/**
 * Created by Hasee on 2017/10/6/0006.
 */

public class AQI {
    public AQICity city;

    private class AQICity {
        public String aqi;
        public String pm25;
    }
}
