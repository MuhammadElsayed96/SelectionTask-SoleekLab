package com.ultra.muhammad.selectiontask.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.ultra.muhammad.selectiontask.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Button mSignOutButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate() has been instantiated!");

        mAuth = FirebaseAuth.getInstance();
        initViews();
        setUpListeners();
    }

    private void initViews() {
        Log.d(TAG, "initViews() has been instantiated!");
        mSignOutButton = findViewById(R.id.sign_out_button);
    }

    private void setUpListeners() {
        Log.d(TAG, "setUpListeners() has been instantiated!");
        mSignOutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_out_button:
                signOut();
                break;
        }
    }

    private void signOut() {
        Log.d(TAG, "signOut() has been instantiated!");
        mAuth.signOut();
        startActivity(new Intent(this, AuthActivity.class));
        finish();
    }
}
