package com.delivery.quickie.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.delivery.quickie.R;
import com.delivery.quickie.ui.activities.home;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getColor(R.color.white));
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(MainActivity.this, home.class);
                startActivity(i);
                finish();

            }
        }, 3000);
    }
}