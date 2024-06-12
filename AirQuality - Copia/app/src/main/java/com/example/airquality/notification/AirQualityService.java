package com.example.airquality.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.toolbox.Volley;
import com.example.airquality.R;
import com.example.airquality.data.repository.aqi.AqiData;



public class AirQualityService extends JobIntentService {
    private static final String TAG = AirQualityService.class.getSimpleName();
    private static final int JOB_ID = 1000;
    private static final String CHANNEL_ID = "air_quality_alerts";
    private RequestQueue requestQueue;

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, AirQualityService.class, JOB_ID, work);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue = Volley.newRequestQueue(this);
        createNotificationChannel();
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d(TAG, "Service is handling work");
        checkAirQuality();
    }

    private void checkAirQuality() {
        int airQualityIndex = 4;
        //int airQualityIndex = AqiData.getInfo().getAqiInt();
        Log.d(TAG, "Current air quality index: " + airQualityIndex);
        if (airQualityIndex > 3) {
            sendNotification();
        }
    }

    private void sendNotification() {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Allerta Qualità dell'Aria")
                .setContentText("L'indice di qualità dell'aria ha superato la soglia di 3.")
                .setSmallIcon(R.drawable.aq)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Air Quality Alerts";
            String description = "Notifiche per l'allerta della qualità dell'aria";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
