package com.coolweather.anrdoid.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hasee on 2017/10/6/0006.
 */

public class Now {
    @SerializedName("tmp")
    public String temprature;
    @SerializedName("cond")
    public More more;


    private class More {
        @SerializedName("txt")
        public String info;
    }
}


