package com.coolweather.anrdoid.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hasee on 2017/10/6/0006.
 */

public class Suggestion {
    @SerializedName("comf")
    public Comfort comfort;
    @SerializedName("cw")
    public CarWash weatherId;

    public Sport sport;

    private class Comfort {
        @SerializedName("txt")
        public CarWash info;
    }

    private class CarWash {
        @SerializedName("txt")
        public CarWash info;
    }

    private class Sport {
        @SerializedName("txt")
        public CarWash info;
    }
}


