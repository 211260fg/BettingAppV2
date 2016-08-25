package com.bettingapp.florian.bettingappv2.repo;


import com.bettingapp.florian.bettingappv2.model.User;

/**
 * Created by floriangoeteyn on 12-Jul-16.
 */
public interface OnLoggedInListener {

    public void onLoginSuccess(User user);

    public void onLoginFailed();

}
