package com.coolweather.anrdoid.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Hasee on 2017/10/4/0004.
 */

public class County extends DataSupport {
    private int id;
    private String countyName;
    int cityId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
