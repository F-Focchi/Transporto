package com.example.airquality.util;


import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.airquality.data.repository.aqi.AqiData;
import com.example.airquality.data.service.AqiApiService;
import com.example.airquality.model.AirQualityData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Caller {

    public static void aqiApi(double lat, double lon ,Context context){
        AqiApiService mainService = ServiceLocator.getAqiApiService();
        String apiKey = Global.getAqiApiKey();
        mainService.getAqi(lat, lon, apiKey).enqueue(new Callback<AirQualityData>() {
            @Override
            public void onResponse(@NonNull Call<AirQualityData> call, @NonNull Response<AirQualityData> response) {
                if (response.body() != null && response.isSuccessful()) {
                    AirQualityData airQualityData = response.body();
                    AqiData.setAqiData(airQualityData);
                } else {
                    showErrorToast(context);
                }
            }
            @Override
            public void onFailure(@NonNull Call<AirQualityData> call,@NonNull Throwable t) {
                showErrorToast(context);
            }
        });
    }
    private static void showErrorToast(Context context) {
        Toast.makeText(context, "Failed to load weather data", Toast.LENGTH_SHORT).show();
    }
}
