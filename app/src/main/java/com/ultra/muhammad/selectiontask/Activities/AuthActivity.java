package com.ultra.muhammad.selectiontask.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.ultra.muhammad.selectiontask.Fragments.LoginFragment;
import com.ultra.muhammad.selectiontask.R;

public class AuthActivity extends AppCompatActivity {


    private static final String TAG = AuthActivity.class.getSimpleName();
    private static FragmentManager fragmentManager;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        Log.d(TAG, "onCreate() has been instantiated");

        fragmentManager = getSupportFragmentManager();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            Toast.makeText(this, "Welcome back!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
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
