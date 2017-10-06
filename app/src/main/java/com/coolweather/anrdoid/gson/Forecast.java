package com.coolweather.anrdoid.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hasee on 2017/10/6/0006.
 */

public class Forecast {
    public String date;
    @SerializedName("tmp")
    public Temperature temperature;
    @SerializedName("cond")
    public More more;



    private class Temperature {
        public String max;
        public String min;
    }

    private class More {
        @SerializedName("txt_d")
        public String info;
    }


}


