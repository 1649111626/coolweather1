package com.coolweather.anrdoid.util;

import android.text.TextUtils;

import com.coolweather.anrdoid.db.City;
import com.coolweather.anrdoid.db.County;
import com.coolweather.anrdoid.db.Province;
import com.coolweather.anrdoid.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hasee on 2017/10/4/0004.
 */

public class Utility {
    public static Weather handleWeatherResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static boolean handleProvinceResponse(String respone) {
        if (!TextUtils.isEmpty(respone)) {
            try {
                JSONArray allProvinces = new JSONArray(respone);
                for (int i = 0; i < allProvinces.length(); i++) {
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

 public static boolean handleCityResponse(String respone,int provinceId) {
        if (!TextUtils.isEmpty(respone)) {
            try {
                JSONArray allCitis = new JSONArray(respone);
                for (int i = 0; i < allCitis.length(); i++) {
                    JSONObject cityObject = allCitis.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
public static boolean handleCountyResponse(String respone,int cityId) {
        if (!TextUtils.isEmpty(respone)) {
            try {
                JSONArray allCountis = new JSONArray(respone);
                for (int i = 0; i < allCountis.length(); i++) {
                    JSONObject countyObject = allCountis.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }



}
