package com.example.airquality.data.service;

import static com.example.airquality.util.Constants.LATITUDE_PARAMETER;
import static com.example.airquality.util.Constants.TOP_HEADLINES_ENDPOINT;
import static com.example.airquality.util.Constants.LONGITUDE_PARAMETER;

import com.example.airquality.model.AirQualityData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AqiApiService {
    @GET(TOP_HEADLINES_ENDPOINT)
    Call<AirQualityData> getAqi(
            @Query(LONGITUDE_PARAMETER) double longitude,
            @Query(LATITUDE_PARAMETER) double latitude,
            @Query("appid") String apiKey );
}
