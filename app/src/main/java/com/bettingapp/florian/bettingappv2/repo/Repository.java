package com.bettingapp.florian.bettingappv2.repo;

import com.bettingapp.florian.bettingappv2.model.Bet;
import com.bettingapp.florian.bettingappv2.model.Option;
import com.bettingapp.florian.bettingappv2.model.User;
import com.bettingapp.florian.bettingappv2.rest.BetCallback;
import com.bettingapp.florian.bettingappv2.rest.LoginCallback;
import com.bettingapp.florian.bettingappv2.rest.SignupCallback;
import com.bettingapp.florian.bettingappv2.session.UserSessionManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by floriangoeteyn on 22-Apr-16.
 */
public class Repository {

    private static List<Bet> bets = new ArrayList<>();
    private static List<LoadedListener> loadedListeners = new ArrayList<>();
    private static List<OnLoggedInListener> loggedInListeners = new ArrayList<>();
    private static BetCallback callback;

    static{
        callback=new BetCallback();
    }

    public static List<Bet> getBets(){
        return bets;
    }

    public static User getCurrentUser() {
        return UserSessionManager.getCurrentUser();
    }

    public static void loadBets(){
        callback.loadBets();
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
        Repository.bets =bets;
        Repository.sortbets(true);
        notifyListenersBetsLoaded();
    }

    public static void addBet(Bet bet){
        callback.addBet(bet);
    }

    public static void upvoteAnswer(Bet bet, Option option){
        if(option.equals(bet.getOptionA())){
            callback.upvoteA(bet);
        }else{
            callback.upvoteB(bet);
        }
    }

    public static void onUpdateSuccess(Bet bet){
        for(LoadedListener listener: loadedListeners){
            listener.onBetUpdated(bet);
            //listener.onBetsLoaded();
        }
    }

    public static void onUpdateFailed(){
        for(LoadedListener listener: loadedListeners){
            listener.onUpdateFailed();
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

    public static void sortbets(boolean sortByNewest){
        if(sortByNewest){
            Collections.sort(bets, new BetDateComparator());
        }else{
            Collections.sort(bets, new BetPopularityComparator());
        }
        notifyListenersBetsLoaded();
    }

    public static class BetDateComparator implements Comparator<Bet> {
        @Override
        public int compare(Bet bet1, Bet bet2) {
            return bet1.getDate().compareTo(bet2.getDate());
        }
    }

    public static class BetPopularityComparator implements Comparator<Bet> {
        @Override
        public int compare(Bet bet1, Bet bet2) {
            int bet1totalvotes = bet1.getOptionA().getVotes()+bet1.getOptionB().getVotes();
            int bet2totalvotes = bet2.getOptionA().getVotes()+bet2.getOptionB().getVotes();
            return bet1totalvotes>bet2totalvotes?-1:1;
        }
    }

}
