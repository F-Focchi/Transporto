package com.example.airquality.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.airquality.R;
import com.example.airquality.util.Global;
import com.example.airquality.util.Caller;

public class WaitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wait);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setPackageName();
        Caller.aqiApi(Global.getLatitude(),Global.getLongitude(),this);
       // Intent intent = new Intent(WaitActivity.this, MainActivity.class);
       // startActivity(intent);

      /*  new Handler().postDelayed(() -> {
            Intent intent = new Intent(WaitActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }, 2000); // 2 secondi di ritardo

       */
    }
    void setPackageName() {
        String packageName = getApplicationContext().getPackageName();
        Global.setPackageName(packageName);
    }
}