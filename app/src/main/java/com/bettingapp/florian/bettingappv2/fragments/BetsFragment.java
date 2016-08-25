package com.bettingapp.florian.bettingappv2.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bettingapp.florian.bettingappv2.R;
import com.bettingapp.florian.bettingappv2.adapters.BetAdapter;
import com.bettingapp.florian.bettingappv2.model.Bet;
import com.bettingapp.florian.bettingappv2.repo.LoadedListener;
import com.bettingapp.florian.bettingappv2.repo.Repository;

/**
 * A simple {@link Fragment} subclass.
 */
public class BetsFragment extends Fragment implements LoadedListener{


    private RecyclerView rv;
    private BetAdapter adapter;
    private View rootView;

    public BetsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView  = inflater.inflate(R.layout.fragment_bets, container, false);

        rv = (RecyclerView) rootView.findViewById(R.id.betRecyclerView);
        createItemView();
        Repository.registerListener(this);
        Repository.loadBets();


        return rootView;
    }





    private void createItemView(){

        //opbouw van de adapter voor de recyclerview
        LinearLayoutManager llm = new LinearLayoutManager(rootView.getContext());
        rv.setLayoutManager(llm);

        adapter = new BetAdapter(Repository.getBets(), rootView.getContext());
        rv.setAdapter(adapter);
    }


    @Override
    public void onBetsLoaded() {
        Log.d("bets loaded", "success");
        adapter.setBets(Repository.getBets());
    }

    @Override
    public void onLoadFailed() {
        Log.d("bets loaded", "failed");
    }

    @Override
    public void onBetAdded(Bet bet, int pos) {
        adapter.notifyItemInserted(pos);
    }

    @Override
    public void onBetRemoved(Bet bet, int pos) {
        adapter.notifyItemRemoved(pos);
    }
}
