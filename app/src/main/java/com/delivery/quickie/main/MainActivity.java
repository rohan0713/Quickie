package com.delivery.quickie.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.delivery.quickie.R;
import com.delivery.quickie.ui.activities.LoginActivity;
import com.delivery.quickie.ui.activities.home;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        Boolean access = sharedPreferences.getBoolean("access", false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(access){
                    Intent i = new Intent(MainActivity.this, home.class);
                    startActivity(i);
                    finish();
                }else {
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        }, 3000);
    }
}