package com.ultra.muhammad.selectiontask.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ultra.muhammad.selectiontask.Fragments.LoginFragment;
import com.ultra.muhammad.selectiontask.R;

public class AuthActivity extends AppCompatActivity {


    private static final String TAG = AuthActivity.class.getSimpleName();
    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        Log.d(TAG, "onCreate() has been instantiated");

        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frameContainer, new LoginFragment(),
                            TAG).commit();
        }
    }

    @Override
    public void onBackPressed() {
        Log.wtf(TAG, "onBackPressed() has been instantiated");

        int count = fragmentManager.getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            fragmentManager.popBackStackImmediate();
        }
    }
}
