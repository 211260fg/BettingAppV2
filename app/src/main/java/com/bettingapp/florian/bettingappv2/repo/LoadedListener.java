package com.bettingapp.florian.bettingappv2.repo;


import com.bettingapp.florian.bettingappv2.model.Bet;

/**
 * Created by floriangoeteyn on 22-Apr-16.
 */
public interface LoadedListener {
    void onBetsLoaded();
    void onLoadFailed();
    void onBetAdded(Bet bet, int pos);
    void onBetRemoved(Bet bet, int pos);
    void onBetUpdated(Bet bet);
    void onUpdateFailed();
}
