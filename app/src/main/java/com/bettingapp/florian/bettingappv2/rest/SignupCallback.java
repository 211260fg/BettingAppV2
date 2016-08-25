package com.bettingapp.florian.bettingappv2.rest;

import android.util.Log;

import com.bettingapp.florian.bettingappv2.model.User;
import com.bettingapp.florian.bettingappv2.repo.Repository;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by Florian on 25/08/2016.
 */
public class SignupCallback implements Callback<User> {


    public SignupCallback( String username, String password) {
        RestClient restClient = new RestClient(username, password);
        RestClient.UserApiInterface service = restClient.getUserClient();
        Call<User> userCall = service.signup(username, password);
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
        Log.d("user signup failed", t.getMessage());
    }
}