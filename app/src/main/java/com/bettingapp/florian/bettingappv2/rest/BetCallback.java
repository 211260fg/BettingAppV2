package com.bettingapp.florian.bettingappv2.rest;

import android.util.Log;

import com.bettingapp.florian.bettingappv2.model.Bet;
import com.bettingapp.florian.bettingappv2.repo.Repository;

import java.util.List;

import retrofit.Callback;
import retrofit.Response;

/**
 * Created by floriangoeteyn on 22-Apr-16.
 */
public class BetCallback implements Callback<List<Bet>> {


    private RestClient restClient;


    public BetCallback() {

        restClient = new RestClient("", "");

        restClient.getItemClient().getBets().enqueue(this);
    }

    @Override
    public void onResponse(Response<List<Bet>> response) {
        if(response.isSuccess()) {
            Repository.onBetsLoaded(response.body());
            Log.d("load success", "size = "+response.body().size());
        }
        else {
            Repository.onLoadFailed();
            Log.d("load failed", String.valueOf(response.errorBody()));
        }
    }

    @Override
    public void onFailure(Throwable t) {
        Repository.onLoadFailed();
        Log.d("load failed", t.getMessage());
    }
}
