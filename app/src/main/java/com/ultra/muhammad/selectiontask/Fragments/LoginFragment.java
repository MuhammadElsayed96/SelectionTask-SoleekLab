package com.ultra.muhammad.selectiontask.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ultra.muhammad.selectiontask.R;
import com.ultra.muhammad.selectiontask.Utils.CustomToast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = LoginFragment.class.getSimpleName();
    // Email Validation pattern
    public static final String REG_EX = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";
    private View view;
    private FragmentManager mFragmentManager;
    private Animation mShakeAnimation;
    private EditText mEmail, mPassword;
    private CheckBox mShowHidePassword;
    private Button mLoginButton, mSignUpButton;
    private LinearLayout mLoginLayout;
    private String email, password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() has been instantiated");

        view = inflater.inflate(R.layout.fragment_login, container, false);

        initViews();
        setListeners();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.wtf(TAG, "onResume() has been instantiated");

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener

                    getActivity().onBackPressed();
                    return true;
                }
                return false;
            }
        });
    }

    public LoginFragment() {
        // Required empty public constructor
    }


    /**
     * sets up all Fragment Views
     */
    private void initViews() {
        Log.wtf(TAG, "initViews() has been instantiated");
        mFragmentManager = getActivity().getSupportFragmentManager();

        mEmail = view.findViewById(R.id.input_email);
        mPassword = view.findViewById(R.id.input_password);
        mLoginButton = view.findViewById(R.id.btn_login);
        mSignUpButton = view.findViewById(R.id.sign_up_button);
        mLoginLayout = view.findViewById(R.id.login_layout);
        mShowHidePassword = view.findViewById(R.id.show_hide_password);
        // load shake animation
        mShakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
    }

    private void setListeners() {
        Log.wtf(TAG, "setListeners() has been instantiated");
        mLoginButton.setOnClickListener(this);
        mSignUpButton.setOnClickListener(this);
        mLoginLayout.setOnClickListener(this);

        mShowHidePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mShowHidePassword.setText(R.string.hide_pwd);
                    mPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    mPassword.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());// show password
                } else {
                    mShowHidePassword.setText(R.string.show_pwd);

                    mPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mPassword.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());// hide password
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                Log.d(TAG, "login button has been clicked");


                if (checkValidation()) {

                }
                break;

            case R.id.sign_up_button:
                Log.d(TAG, "sign up button has been clicked");
                mFragmentManager.beginTransaction()
                        .addToBackStack(TAG)
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer, new SignUpFragment(), SignUpFragment.TAG)
                        .commit();
                break;
        }
    }

    /**
     * Validates user input before logging him in
     *
     * @return false if input is not valid, true if valid
     */
    private boolean checkValidation() {
        Log.wtf(TAG, "checkValidation() has been instantiated");
        email = mEmail.getText().toString();
        password = mPassword.getText().toString();

        // checking email pattern
        Pattern pattern = Pattern.compile(REG_EX);
        Matcher matcher = pattern.matcher(email);

        // Check for both field is empty or not
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            mLoginLayout.startAnimation(mShakeAnimation);
            new CustomToast().showToast(getContext(), mLoginLayout, "Enter all credentials!");
            return false;
        }
        // Check if email id is valid or not
        if (!matcher.find()) {
            new CustomToast().showToast(getContext(), mLoginLayout, "Your Email Id is Invalid.");
            return false;
        }

        return true;
    }

}