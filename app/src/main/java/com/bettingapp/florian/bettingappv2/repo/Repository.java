package com.bettingapp.florian.bettingappv2.repo;

import com.bettingapp.florian.bettingappv2.model.Bet;
import com.bettingapp.florian.bettingappv2.model.Option;
import com.bettingapp.florian.bettingappv2.model.User;
import com.bettingapp.florian.bettingappv2.rest.BetCallback;
import com.bettingapp.florian.bettingappv2.rest.LoginCallback;
import com.bettingapp.florian.bettingappv2.rest.SignupCallback;
import com.bettingapp.florian.bettingappv2.session.UserSessionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by floriangoeteyn on 22-Apr-16.
 */
public class Repository {

    private static List<Bet> bets_ = new ArrayList<>();
    private static List<LoadedListener> loadedListeners = new ArrayList<>();
    private static List<OnLoggedInListener> loggedInListeners = new ArrayList<>();
    private static BetCallback callback;

    public static List<Bet> getBets(){
        return bets_;
    }

    public static void loadBets(){
        callback = new BetCallback();
    }

    public static void registerListener(LoadedListener listener){
        loadedListeners.add(listener);
    }

    public static void registerListener(OnLoggedInListener listener){
        loggedInListeners.add(listener);
    }

    public static void removeListener(LoadedListener listener){
        loadedListeners.remove(listener);
    }

    public static void removeListener(OnLoggedInListener listener){
        loggedInListeners.remove(listener);
    }

    public static void loginUser(String username, String password){
        new LoginCallback(username, password);
    }

    public static void logoutUser(){
        UserSessionManager.logoutUser();
    }

    public static void onLoginSuccess(User user){
        notifyListenersUserLoggedIn(user);
    }

    public static void onLoginFailed(){
        notifyListenersLoginFailed();
    }

    public static void signupUser(String username, String password){
        new SignupCallback(username, password);
    }

    public static void onBetsLoaded(List<Bet> bets){
        bets_=bets;
        notifyListenersBetsLoaded();
    }

    public static void upvoteAnswer(Bet bet, Option option){
        if(bet.getOptionA()==option){
            bet.getOptionA().upvote();
        }
    }

    public static void onLoadFailed(){
        notifyListenersLoadFailed();
    }

    private static void notifyListenersBetsLoaded(){
        for(LoadedListener listener: loadedListeners){
            listener.onBetsLoaded();
        }
    }

    private static void notifyListenersUserLoggedIn(User user){
        for(OnLoggedInListener listener: loggedInListeners){
            listener.onLoginSuccess(user);
        }
    }

    private static void notifyListenersLoadFailed(){
        for(LoadedListener listener: loadedListeners){
            listener.onLoadFailed();
        }
    }

    private static void notifyListenersLoginFailed(){
        for(OnLoggedInListener listener: loggedInListeners){
            listener.onLoginFailed();
        }
    }


}
