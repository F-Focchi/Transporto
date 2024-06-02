package com.example.airquality.util;

public class Constants {

    public static final String TOP_HEADLINES_ENDPOINT = "air_pollution";
    public static final String LONGITUDE_PARAMETER = "lon";
    public static final String LATITUDE_PARAMETER = "lat";

    public static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";



    public static final String RETROFIT_ERROR = "retrofit_error";
    public static final String API_KEY_ERROR = "api_key_error";
    public static final String UNEXPECTED_ERROR = "unexpected_error";
    public static final String INVALID_USER_ERROR = "invalidUserError";
    public static final String INVALID_CREDENTIALS_ERROR = "invalidCredentials";
    public static final String USER_COLLISION_ERROR = "userCollisionError";
    public static final String WEAK_PASSWORD_ERROR = "passwordIsWeak";

    public static final int MINIMUM_PASSWORD_LENGTH = 6;

    // Constants for Firebase Realtime Database
    public static final String FIREBASE_REALTIME_DATABASE = "https://world-news-pdm-default-rtdb.europe-west1.firebasedatabase.app/";
    public static final String FIREBASE_USERS_COLLECTION = "users";
    public static final String FIREBASE_FAVORITE_NEWS_COLLECTION = "favorite_news";


}
