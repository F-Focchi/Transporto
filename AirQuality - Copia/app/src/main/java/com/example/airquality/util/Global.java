package com.example.airquality.util;

public class Global {
    public static String City;
    public static double latitude;
    public static double longitude;
    public static String packageName;
    private final static String aqiApiKey = "965327848f09b6cf4c95403ba38cde3a";
    private final static String searchApiKey = "AIzaSyCwlcJalRGR6LGz4zbPj8VKqkbjdfL_ATI";
    public static String getPackageName() {
        return packageName;
    }
    public static void setPackageName(String packageName) {
        Global.packageName = packageName;
    }
    public static String getCity() {
        return City;
    }
    public static void setCity(String city) {
        City = city;
    }
    public static String getAqiApiKey() {
        return aqiApiKey;
    }
    public static String getSearchApiKey() {
        return searchApiKey;
    }
    public static double getLatitude() {
        return latitude;
    }
    public static void setLatitude(double lat) {
        latitude = lat;
    }
    public static double getLongitude() {
        return longitude;
    }
    public static void setLongitude(double lon) {
        longitude = lon;
    }
}