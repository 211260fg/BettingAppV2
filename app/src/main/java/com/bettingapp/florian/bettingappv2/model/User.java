package com.bettingapp.florian.bettingappv2.model;

import com.bettingapp.florian.bettingappv2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by floriangoeteyn on 05-Apr-16.
 */
public class User {

    private String id;
    private String username;
    private String profilePictureUrl;
    private int betscreated;
    private List<String> votedBetsIds;
    private int betsCreated;

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getProfilePictureUrl() {
        if(profilePictureUrl==null)
            return "";
        else
            return profilePictureUrl;
    }

    public int getBetscreated() {
        return betscreated;
    }

    public List<String> getVotedBetsIds() {
        if(votedBetsIds==null)
            return new ArrayList<>();
        else
            return votedBetsIds;
    }
}
