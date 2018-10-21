package com.ultra.muhammad.selectiontask.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;
import com.ultra.muhammad.selectiontask.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String PREF_COUNTRY_CODE = "pref_country_code";
    private static final String PREF_FILE_NAME = "soleeklab_pref";
    private Button mSignOutButton;
    private FirebaseAuth mAuth;
    private CountryCodePicker codePicker;

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
        codePicker = findViewById(R.id.country_picker_view);
    }

    private void setUpListeners() {
        Log.d(TAG, "setUpListeners() has been instantiated!");
        mSignOutButton.setOnClickListener(this);
        codePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                Log.d(TAG, "Saving the country name to the preference file");
                SharedPreferences sharedPref = getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
                Editor editor = sharedPref.edit();
                int countryCode = codePicker.getSelectedCountryCodeAsInt();
                editor.putInt(PREF_COUNTRY_CODE, countryCode);
                editor.apply();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() has been instantiated!");
        Log.d(TAG, "restoring the saved country name from the preference file");
        SharedPreferences sharedPref = getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        int countryCode = sharedPref.getInt(PREF_COUNTRY_CODE, codePicker.getDefaultCountryCodeAsInt());
        codePicker.setCountryForPhoneCode(countryCode);

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
