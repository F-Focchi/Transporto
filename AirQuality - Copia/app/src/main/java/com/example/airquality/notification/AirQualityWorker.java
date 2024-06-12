package com.example.airquality.notification;




import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;


public class AirQualityWorker extends Worker {
    private static final String TAG = AirQualityWorker.class.getSimpleName();
    public AirQualityWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "Worker is executing");
        Intent intent = new Intent(getApplicationContext(), AirQualityService.class);
        AirQualityService.enqueueWork(getApplicationContext(), intent);
        return Result.success();
    }
}
