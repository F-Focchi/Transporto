package com.example.airquality.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;


import com.example.airquality.data.repository.aqi.AqiData;
import com.example.airquality.data.repository.user.IUserRepository;
import com.example.airquality.notification.AirQualityWorker;
import com.example.airquality.ui.welcome.UserViewModel;
import com.example.airquality.ui.welcome.UserViewModelFactory;
import com.example.airquality.ui.welcome.WelcomeActivity;
import com.example.airquality.util.Global;
import com.example.airquality.util.ServiceLocator;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import com.example.airquality.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private TextView aqi;
    private  TextView city;
    private  TextView description;
    private  TextView co;
    private  TextView no2;
    private  TextView o3;
    private  TextView so2;
    private  TextView pm2_5;
    private  TextView pm10;

    private static final String TAG = MainActivity.class.getSimpleName();
    private UserViewModel userViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!Places.isInitialized()) {
            String apikey = Global.getSearchApiKey();
            Places.initialize(getApplicationContext(), apikey);
        }
/*
        Intent serviceIntent = new Intent(this, NotificationService.class);
        startService(serviceIntent);

 */

        IUserRepository userRepository = ServiceLocator.getInstance()
                .getUserRepository(getApplication());
        userViewModel = new ViewModelProvider(
                this,
                new UserViewModelFactory(userRepository)).get(UserViewModel.class);


        aqi = findViewById(R.id.aqi_value);
        city = findViewById(R.id.cityName);
        description = findViewById(R.id.airQualityDescription);
        co = findViewById(R.id.co_value);
        no2 = findViewById(R.id.no2_value);
        o3 = findViewById(R.id.o3_value);
        so2 = findViewById(R.id.so2_value);
        pm2_5 = findViewById(R.id.pm25_value);
        pm10 = findViewById(R.id.pm10_value);

        setUI();

        scheduleAirQualityCheck();

        Button logoutButton = findViewById(R.id.logoutButton);
        Button searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(View -> {
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                    .setTypeFilter(TypeFilter.CITIES)
                    .build(this);
            startAutocomplete.launch(intent);
        });

        logoutButton.setOnClickListener(View -> {
            userViewModel.logout().observe(this, result -> {
                if (result.isSuccess()) {
                    Log.d(TAG, "Logout success");
                    Intent intentLogout = new Intent(MainActivity.this, WelcomeActivity.class);
                    startActivity(intentLogout);
                } else {
                    Snackbar.make(View, getString(R.string.unexpected_error), Snackbar.LENGTH_SHORT).show();
                }
            });
        });


        /*
        Button testButton = findViewById(R.id.test_button);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OneTimeWorkRequest testRequest = new OneTimeWorkRequest.Builder(AirQualityWorker.class)
                        .build();
                WorkManager.getInstance(MainActivity.this).enqueue(testRequest);
            }
        });

        Button delayedNotificationButton = findViewById(R.id.delayed_notification_button);
        delayedNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleDelayedNotification();
            }
        });

         */
    }


    private final ActivityResultLauncher<Intent> startAutocomplete = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();
                    if (intent != null) {
                        Place place = Autocomplete.getPlaceFromIntent(intent);

                        String cityName = place.getName();
                        LatLng latLng = place.getLatLng();
                        if (latLng != null) {
                            double latitude = latLng.latitude;
                            double longitude = latLng.longitude;

                            Global.setCity(cityName);
                            Global.setLatitude(latitude);
                            Global.setLongitude(longitude);

                            Intent intentWait = new Intent(MainActivity.this, WaitActivity.class);
                            startActivity(intentWait);

                            Log.i(TAG, "Place: " + cityName + ", Lat: " + latitude + ", Lng: " + longitude);
                        } else {
                            Log.e(TAG, "LatLng is null");
                        }

                        Log.i(TAG, "Place: ${place.getName()}, ${place.getId()}");
                    }
                } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    // The user canceled the operation.
                    Log.i(TAG, "User canceled autocomplete");
                }
            });



    private void scheduleAirQualityCheck() {
        PeriodicWorkRequest airQualityCheckRequest = new PeriodicWorkRequest.Builder(
                AirQualityWorker.class, 15, TimeUnit.MINUTES)
                .build();

        WorkManager.getInstance(this).enqueue(airQualityCheckRequest);
    }

/*
    private void scheduleDelayedNotification() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                OneTimeWorkRequest testRequest = new OneTimeWorkRequest.Builder(AirQualityWorker.class)
                        .build();
                WorkManager.getInstance(MainActivity.this).enqueue(testRequest);
            }
        }, 30000); // Ritardo di 30 secondi
    }
 */

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
