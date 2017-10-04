package com.coolweather.anrdoid.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Hasee on 2017/10/4/0004.
 */

public class Province extends DataSupport {
    private int id;
    private String provinceName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }

    private int provinceCode;

}
