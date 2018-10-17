package com.ultra.muhammad.selectiontask.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.ultra.muhammad.selectiontask.Model.User;
import com.ultra.muhammad.selectiontask.R;
import com.ultra.muhammad.selectiontask.Utils.CustomToast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;

public class SignUpFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = SignUpFragment.class.getSimpleName();
    // Email Validation pattern
    public static final String REG_EX = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";
    private View view;
    private EditText mEmail, mPassword, mConfirmPassword;
    private FragmentManager mFragmentManager;
    private Button mLoginButton, mSignUpButton;
    private String email, password, confirmPassword;
    private LinearLayout mSignUpLayout;
    private Animation mShakeAnimation;
    private FirebaseAuth mAuth;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() has been instantiated");
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() has been instantiated");

        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initViews();
        setListeners();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() has been instantiated");

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
                    mFragmentManager.popBackStack();
                    mFragmentManager
                            .beginTransaction()
                            //.setCustomAnimations(R.anim.left_out, R.anim.right_enter)
                            .replace(R.id.frameContainer, new LoginFragment(), LoginFragment.TAG)
                            .commit();
                    return true;
                }
                return false;
            }
        });
    }

    private void initViews() {
        Log.d(TAG, "initViews() has been instantiated");
        mFragmentManager = getActivity().getSupportFragmentManager();
        mEmail = view.findViewById(R.id.input_email);
        mPassword = view.findViewById(R.id.input_password);
        mConfirmPassword = view.findViewById(R.id.input_confirm_password);
        mSignUpButton = view.findViewById(R.id.btn_sign_up);
        mLoginButton = view.findViewById(R.id.login_button);
        mSignUpLayout = view.findViewById(R.id.sign_up_layout);
        // load shake animation
        mShakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
    }


    private void setListeners() {
        Log.d(TAG, "setListeners() has been instantiated");
        mSignUpButton.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
        mSignUpLayout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_up:
                SignUpUser();
                break;
            case R.id.login_button:
                goToLoginFragment();
                break;
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void goToLoginFragment() {
        Log.d(TAG, "login button has been clicked");
        hideKeyboard(getActivity());
        mFragmentManager.popBackStack();
        mFragmentManager
                .beginTransaction()
                .addToBackStack(TAG)
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.frameContainer, new LoginFragment(), LoginFragment.TAG)
                .commit();
    }

    /**
     * Validates user input before logging him in
     *
     * @return false if input is not valid, true if valid
     */
    private boolean checkValidation() {
        Log.d(TAG, "checkValidation() has been instantiated");
        email = mEmail.getText().toString();
        password = mPassword.getText().toString();
        confirmPassword = mConfirmPassword.getText().toString();

        // Pattern match for email id
        Pattern p = Pattern.compile(REG_EX);
        Matcher m = p.matcher(email);

        // Check if all strings are null or not
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            mSignUpLayout.startAnimation(mShakeAnimation);
            new CustomToast().showToast(getContext(), view, "Enter all credentials!");
            return false;
        }

        // Check if email id valid or not
        if (!m.find()) {
            new CustomToast().showToast(getContext(), view, "Your Email Id is Invalid!");
            return false;
        }

        if (password.length() < 6) {
            mPassword.startAnimation(mShakeAnimation);
            new CustomToast().showToast(getContext(), view, "Password is too short!");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            mPassword.startAnimation(mShakeAnimation);
            mConfirmPassword.startAnimation(mShakeAnimation);
            new CustomToast().showToast(getContext(), view, "Passwords does not match!");
            return false;
        }
        return true;
    }

    private void SignUpUser() {
        Log.d(TAG, "sign up button has been clicked");
        hideKeyboard(getActivity());
        if (checkValidation()) {
            final AlertDialog waitingDialog = new SpotsDialog(getActivity(), R.style.Custom);
            waitingDialog.setCancelable(false);
            waitingDialog.setTitle("Loading...");
            waitingDialog.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        User user = new User(email, password);
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(mAuth.getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                waitingDialog.dismiss();
                                mFragmentManager
                                        .beginTransaction()
                                        .addToBackStack(TAG)
                                        .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                                        .replace(R.id.frameContainer, new LoginFragment(), LoginFragment.TAG)
                                        .commit();
                                Toast.makeText(getContext(), "Registered successfully!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }
    }
}
