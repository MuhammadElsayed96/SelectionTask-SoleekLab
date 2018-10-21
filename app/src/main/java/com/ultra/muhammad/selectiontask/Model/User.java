package com.ultra.muhammad.selectiontask.Model;

public final class User {

    private String mUserEmail, mUserPassword, mUserConfirmPassword;

    // Login constructor
    public User(String mUserEmail, String mUserPassword) {
        this.mUserEmail = mUserEmail;
        this.mUserPassword = mUserPassword;
    }

    // Registration constructor
    public User(String mUserEmail, String mUserPassword, String mUserConfirmPassword) {
        this.mUserEmail = mUserEmail;
        this.mUserPassword = mUserPassword;
        this.mUserConfirmPassword = mUserConfirmPassword;
    }

    public String getUserEmail() {
        return mUserEmail;
    }

    public void setUserEmail(String mUserEmail) {
        this.mUserEmail = mUserEmail;
    }

    public String getUserPassword() {
        return mUserPassword;
    }

    public void setUserPassword(String mUserPassword) {
        this.mUserPassword = mUserPassword;
    }

    public String getUserConfirmPassword() {
        return mUserConfirmPassword;
    }

    public void setUserConfirmPassword(String mUserConfirmPassword) {
        this.mUserConfirmPassword = mUserConfirmPassword;
    }

    @Override
    public String toString() {
        return "User{" +
                "mUserEmail='" + mUserEmail + '\'' +
                ", mUserPassword='" + mUserPassword + '\'' +
                '}';
    }
}
