package com.example.airquality.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Support {
    public static String setDescriptionToString(int descriptionValue) {
        String descrizione = "";
        switch(descriptionValue) {
            case (1):
                descrizione = "Good";
                break;
            case (2):
                descrizione = "Fair";
                break;
            case (3):
                descrizione = "Moderate";
                break;
            case (4):
                descrizione = "Poor";
                break;
            case (5):
                descrizione = "Very poor";
                break;
            default:
                descrizione = "Unknown";
                break;
        }
        return descrizione;
    }
    public static String setAqiToString(int aqiValue) {
        String aqi = "";
        switch(aqiValue) {
            case (1):
                aqi = "1";
                break;
            case (2):
                aqi = "2";
                break;
            case (3):
                aqi = "3";
                break;
            case (4):
                aqi = "4";
                break;
            case (5):
                aqi = "5";
                break;
            default:
                aqi = "6?";
                break;
        }
        return aqi;
    }
}
