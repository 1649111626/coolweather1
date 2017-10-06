package com.coolweather.anrdoid;

import android.app.ProgressDialog;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.LogTime;
import com.coolweather.anrdoid.db.City;
import com.coolweather.anrdoid.db.County;
import com.coolweather.anrdoid.db.Province;
import com.coolweather.anrdoid.util.HttpUtil;
import com.coolweather.anrdoid.util.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by Hasee on 2017/10/4/0004.
 */

public class ChooseAreaFragment extends Fragment {
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;
    ProgressDialog progressDialog;
    TextView titleText;
    Button backButton;
    ListView listView;
    ArrayAdapter<String> adapter;
    List<String> dataList = new ArrayList<>();
    List<Province> provinceList= new ArrayList<>();
    List<City> cityList= new ArrayList<>();
    List<County> countyList=new ArrayList<>();
    Province selecteProvince;
    City selecteCity;
    int currentLevel;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area, container, false);
        titleText = (TextView) view.findViewById(R.id.title_text);
        backButton = (Button) view.findViewById(R.id.back_button);
        listView = (ListView) view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selecteProvince = provinceList.get(position);
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    selecteCity = cityList.get(position);
                    queryCounties();
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLevel == LEVEL_COUNTY) {
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    queryProvinces();
                }
            }
        });
        queryProvinces();
    }
    private void queryProvinces() {
        titleText.setText("中国");
        backButton.setVisibility(View.GONE);
        String address = "http://guolin.tech/api/china";
        queryFromServer(address, "province");
    }
    private void queryCities() {
        titleText.setText(selecteProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        int provinceCode = selecteProvince.getProvinceCode();
        String address = "http://guolin.tech/api/china/" + provinceCode;
        queryFromServer(address, "city");
    }
    private void queryCounties() {
        titleText.setText(selecteCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
            int provinceCode = selecteProvince.getProvinceCode();
            int cityCode = selecteCity.getCityCode();
            String address = "http://guolin.tech/api/china" + provinceCode + "/" + cityCode;
            queryFromServer(address, "county");
    }

    private void queryFromServer(String address, final String type) {
        //   showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();

                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Log.e("qqq", "result" + responseText);
                if ("province".equals(type)) {
                    dataList.clear();
                    if (!TextUtils.isEmpty(responseText)) {
                        try {
                            JSONArray allProvinces = new JSONArray(responseText);
                            for (int i = 0; i < allProvinces.length(); i++) {
                                JSONObject provinceObject = allProvinces.getJSONObject(i);
                                Province province = new Province();
                                province.setProvinceName(provinceObject.getString("name"));
                                province.setProvinceCode(provinceObject.getInt("id"));
                                    dataList.add(province.getProvinceName());
                                 provinceList.add(province);
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                    listView.setSelection(0);
                                    currentLevel = LEVEL_PROVINCE;
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else if ("city".equals(type)) {
                    if (!TextUtils.isEmpty(responseText)) {
                        try {
                            JSONArray allCitis = new JSONArray(responseText);
                            dataList.clear();
                            for (int i = 0; i < allCitis.length(); i++) {
                                JSONObject cityObject = allCitis.getJSONObject(i);
                                City city = new City();
                                city.setCityName(cityObject.getString("name"));
                                city.setCityCode(cityObject.getInt("id"));
                                city.setProvinceId(selecteProvince.getId());
                                dataList.add(city.getCityName());
                                cityList.add(city);
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                    listView.setSelection(0);
                                    currentLevel = LEVEL_CITY;
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else if ("county".equals(type)) {
                    dataList.clear();
                    if (!TextUtils.isEmpty(responseText)) {
                        try {
                            JSONArray allCountis = new JSONArray(responseText);
                            for (int i = 0; i < allCountis.length(); i++) {
                                JSONObject countyObject = allCountis.getJSONObject(i);
                                County county = new County();
                                county.setCountyName(countyObject.getString("name"));
                                county.setWeatherId(countyObject.getString("weather_id"));
                                county.setCityId(selecteCity.getId());
                                dataList.add(county.getCountyName());
                                countyList.add(county);
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                    listView.setSelection(0);
                                    currentLevel = LEVEL_COUNTY;
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


}
