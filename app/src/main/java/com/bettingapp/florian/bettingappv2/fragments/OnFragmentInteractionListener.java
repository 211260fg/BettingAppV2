package com.bettingapp.florian.bettingappv2.fragments;

/**
 * Created by Florian on 24/08/2016.
 */
public interface OnFragmentInteractionListener {

    void onFragmentInteraction(fragment fragment, int id);

    public enum fragment{
        betsfragment, newfragment, profilefragment, loginfragment, registerfragment
    }

}
