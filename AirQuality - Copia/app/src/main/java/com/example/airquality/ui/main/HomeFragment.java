package com.example.airquality.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.airquality.R;
import com.example.airquality.data.repository.aqi.AqiData;
import com.example.airquality.util.ServiceLocator;
import com.example.airquality.data.service.AqiApiService;
import com.example.airquality.model.AirQualityData;
import com.example.airquality.util.Global;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        //setCityName(airQualityData.getCoord().getLat(), airQualityData.getCoord().getLon());

        setAqiValue(AqiData.getInfo().getAqi());

        setDescriptionText(AqiData.getInfo().getDescription());

        setCoValue(AqiData.getInfo().getAqi());

        setNo2Value(AqiData.getInfo().getAqi());

        setO3Value(AqiData.getInfo().getAqi());

        setSo2Value(AqiData.getInfo().getAqi());

        setPm2_5Value(AqiData.getInfo().getAqi());

        setPm10Value(AqiData.getInfo().getAqi());

    }


/*

    public void loadAirQualityData(double lon, double lat) {
        ServiceLocator.getInstance();
        AqiApiService mainService = ServiceLocator.getAqiApiService();
        String apiKey = Global.getAqiApiKey();

        mainService.getAqi(lon, lat, apiKey).enqueue(new Callback<AirQualityData>() {
            @Override
            public void onResponse(Call<AirQualityData> call, Response<AirQualityData> response) {
                if (response.isSuccessful()) {
                    AirQualityData airQualityData = response.body();
                    assert airQualityData != null;
                    updateUI(airQualityData);

                }  else {
                    showErrorToast();
                }
            }

            @Override
            public void onFailure(Call<AirQualityData> call, Throwable t) {
                showErrorToast();
            }


        });
    }





    private void updateUI(@NonNull AirQualityData airQualityData) {

        setCityName(airQualityData.getCoord().getLat(), airQualityData.getCoord().getLon());

        setAqi(airQualityData.getList().get(0).getMain().getAqi());

        setDescriptionText(airQualityData.getList().get(0).getMain().getAqi());

        setCo(airQualityData.getList().get(0).getComponents().getCo());

        setNo2(airQualityData.getList().get(0).getComponents().getNo2());

        setO3(airQualityData.getList().get(0).getComponents().getO3());

        setSo2(airQualityData.getList().get(0).getComponents().getSo2());

        setPm2_5(airQualityData.getList().get(0).getComponents().getPm2_5());

        setPm10(airQualityData.getList().get(0).getComponents().getPm10());

    }


 */

    void setCityName(String cityNameValue) {
        city.setText(cityNameValue);
    }

     void setAqiValue(int aqiValue) {
        aqi.setText(Integer.toString(aqiValue));
    }
     void setDescriptionText(String descr) {
        description.setText(descr);
     }
     void setCoValue(float coValue) {co.setText("" + coValue);}
     void setNo2Value(float no2Value) {no2.setText("" + no2Value);}
     void setO3Value(float o3Value) {o3.setText("" +o3Value);}
     void setSo2Value(float so2Value) {so2.setText("" +so2Value);}
     void setPm2_5Value(float pm2_5Value) {pm2_5.setText("" +pm2_5Value);}
     void setPm10Value(float pm10Value) {pm10.setText("" +pm10Value);}

}










