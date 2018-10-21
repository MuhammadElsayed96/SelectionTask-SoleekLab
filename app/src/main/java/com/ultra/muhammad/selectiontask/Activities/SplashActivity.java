package com.ultra.muhammad.selectiontask.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ultra.muhammad.selectiontask.R;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.wtf(TAG, "onCreate() has been instantiated");

        checkUserSession();
    }

    private void checkUserSession() {
        Log.d(TAG, "checkUserSession() has been instantiated!");

        try {
            Thread.sleep(2000);
            startActivity(new Intent(SplashActivity.this, AuthActivity.class));
            finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
