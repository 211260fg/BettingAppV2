package com.bettingapp.florian.bettingappv2.rest;

import android.util.Log;

import com.bettingapp.florian.bettingappv2.model.User;
import com.bettingapp.florian.bettingappv2.repo.Repository;
import com.squareup.okhttp.Headers;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by floriangoeteyn on 04-Apr-16.
 */
public class LoginCallback implements Callback<User> {


    public LoginCallback( String username, String password) {
        RestClient restClient = new RestClient(username, password);
        RestClient.UserApiInterface service = restClient.getUserClient();
        Call<User> userCall = service.getUser();
        userCall.enqueue(this);
    }


    @Override
    public void onResponse(Response<User> response) {

        if(response.isSuccess()){
            Repository.onLoginSuccess(response.body());
        }
        else{
            Repository.onLoginFailed();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        Repository.onLoginFailed();
        Log.d("user login failed", t.getMessage());
    }
}
