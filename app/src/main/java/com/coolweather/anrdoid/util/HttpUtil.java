package com.coolweather.anrdoid.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Hasee on 2017/10/4/0004.
 */

public class HttpUtil {
    public  static void sendOkHttpRequest(String address,okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
