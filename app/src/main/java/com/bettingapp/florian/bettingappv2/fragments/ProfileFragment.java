package com.bettingapp.florian.bettingappv2.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bettingapp.florian.bettingappv2.R;
import com.bettingapp.florian.bettingappv2.model.User;
import com.bettingapp.florian.bettingappv2.repo.Repository;
import com.bumptech.glide.Glide;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment  extends Fragment {


    private View rootView;

    private User currentuser;


    private ImageView profilephoto;
    private TextView profilename;
    private TextView profileDetails;
    private TextView profileBetscreated;
    private TextView profileBetsVoted;

    public ProfileFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        profilephoto = (ImageView) rootView.findViewById(R.id.profile_photo);
        profilename = (TextView) rootView.findViewById(R.id.profile_name);
        profileDetails = (TextView) rootView.findViewById(R.id.profile_details);
        profileBetscreated = (TextView) rootView.findViewById(R.id.profile_betscreated);
        profileBetsVoted = (TextView) rootView.findViewById(R.id.profile_betsvoted);

        currentuser = Repository.getCurrentUser();

        setupProfile();

        return rootView;

    }


    private void setupProfile(){
        if(Repository.getCurrentUser().getProfilePictureUrl().equals("")){
            Glide.with(this).load(R.drawable.profile).centerCrop().into(profilephoto);
        }else {
            Glide.with(this).load(Repository.getCurrentUser().getProfilePictureUrl()).centerCrop().into(profilephoto);
        }
        profilename.setText(currentuser.getUsername());
        profileBetscreated.setText("Number of votes: "+currentuser.getVotedBetsIds().size());
        profileBetsVoted.setText("Number of bets created: "+currentuser.getBetscreated());
    }

}
