package com.coolweather.anrdoid.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hasee on 2017/10/6/0006.
 */

public class Basic {
    @SerializedName("city")
    public String cityName;
    @SerializedName("id")
    public String weatherId;

    public Updata updata;

    private class Updata {
        @SerializedName("loc")
        public String updateTime;
    }
}


