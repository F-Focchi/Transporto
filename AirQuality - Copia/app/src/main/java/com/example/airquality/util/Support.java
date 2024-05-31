package com.example.airquality.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Support {

 public static String setCityToString (double lat, double lon , Context context){
     Geocoder geocoder = new Geocoder(context, Locale.getDefault());
     try {
         List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
         if (addresses != null && !addresses.isEmpty()) {
             String cityName = addresses.get(0).getLocality();
             Global.setCity(cityName);
             Log.d("City", cityName);
             return cityName;
         }
     } catch (IOException e) {
         e.printStackTrace();
     }
     return null;
 }

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
}
