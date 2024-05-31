package com.example.airquality.ui.main;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.airquality.R;
import com.example.airquality.data.repository.aqi.AqiData;


public class HomeFragment extends Fragment {

    private  TextView aqi;
    private  TextView city;
    private  TextView description;
    private  TextView co;
    private  TextView no2;
    private  TextView o3;
    private  TextView so2;
    private  TextView pm2_5;
    private  TextView pm10;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        aqi = view.findViewById(R.id.aqi_value);
        city = view.findViewById(R.id.cityName);
        description = view.findViewById(R.id.airQualityDescription);
        co = view.findViewById(R.id.co_value);
        no2 = view.findViewById(R.id.no2_value);
        o3 = view.findViewById(R.id.o3_value);
        so2 = view.findViewById(R.id.so2_value);
        pm2_5 = view.findViewById(R.id.pm25_value);
        pm10 = view.findViewById(R.id.pm10_value);

        setUI();


    }

    private void setUI(){
        setCityName(AqiData.getInfo().getCity());

        setAqiValue(AqiData.getInfo().getAqi());

        setDescriptionText(AqiData.getInfo().getDescription());

        setCoValue(AqiData.getInfo().getCo());

        setNo2Value(AqiData.getInfo().getNo2());

        setO3Value(AqiData.getInfo().getO3());

        setSo2Value(AqiData.getInfo().getSo2());

        setPm2_5Value(AqiData.getInfo().getPm2_5());

        setPm10Value(AqiData.getInfo().getPm10());

    }

    void setCityName(String cityNameValue) {
        city.setText(cityNameValue);
    }

     void setAqiValue(String aqiValue) {
        aqi.setText(aqiValue);
    }
     void setDescriptionText(String descr) {
        description.setText(descr);
     }
     void setCoValue(String coValue) {co.setText(coValue);}
     void setNo2Value(String no2Value) {no2.setText(no2Value);}
     void setO3Value(String o3Value) {o3.setText(o3Value);}
     void setSo2Value(String so2Value) {so2.setText(so2Value);}
     void setPm2_5Value(String pm2_5Value) {pm2_5.setText(pm2_5Value);}
     void setPm10Value(String pm10Value) {pm10.setText(pm10Value);}

}










