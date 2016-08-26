package com.bettingapp.florian.bettingappv2.rest;

import android.util.Log;

import com.bettingapp.florian.bettingappv2.model.Bet;
import com.bettingapp.florian.bettingappv2.repo.Repository;
import com.bettingapp.florian.bettingappv2.session.UserSessionManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;

/**
 * Created by floriangoeteyn on 22-Apr-16.
 */
public class BetCallback implements Callback<List<Bet>> {


    private RestClient restClient;


    public BetCallback() {

        try{
            HashMap<String, String> user = UserSessionManager.getUserDetails();
            restClient = new RestClient(user.get(UserSessionManager.KEY_NAME), user.get(UserSessionManager.KEY_PASSWORD));
        }
        catch(NullPointerException ignored){
        }
    }

    public void loadBets(){
        restClient.getBetClient().getBets().enqueue(this);
    }

    public void upvoteA(Bet bet){
        restClient.getBetClient().voteA(bet.getId()).enqueue(new SingleBetCallback());
    }

    public void upvoteB(Bet bet){
        restClient.getBetClient().voteB(bet.getId()).enqueue(new SingleBetCallback());
    }

    public void addBet(Bet bet){
        restClient.getBetClient().addBet(bet.getTitle(), bet.getOptionA().getText(), bet.getOptionB().getText(), bet.getReward(), bet.getCategory().name()).enqueue(new SingleBetCallback());
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

    private class SingleBetCallback implements Callback<Bet>{

        @Override
        public void onResponse(Response<Bet> response) {
            if(response.isSuccess()) {
                Log.d("update success", "bet = "+response.body().getTitle());
                Repository.onUpdateSuccess(response.body());
            }else{
                try {
                    Log.d("update failed", response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Repository.onUpdateFailed();
            }
        }

        @Override
        public void onFailure(Throwable t) {
            Log.d("upvote failed", t.getMessage());
            Repository.onUpdateFailed();
        }
    }

}
