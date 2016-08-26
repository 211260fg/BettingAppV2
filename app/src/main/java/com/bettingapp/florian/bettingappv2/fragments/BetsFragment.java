package com.bettingapp.florian.bettingappv2.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    private Spinner sortspinner;
    private BetAdapter adapter;
    private View rootView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private OnFragmentInteractionListener mListener;

    public BetsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView  = inflater.inflate(R.layout.fragment_bets, container, false);

        sortspinner = (Spinner) rootView.findViewById(R.id.sortSpinner);

        rv = (RecyclerView) rootView.findViewById(R.id.betRecyclerView);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Repository.loadBets();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(true);

        createItemView();
        createSpinner();
        Repository.registerListener(this);
        Repository.loadBets();


        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    private void createItemView(){

        //opbouw van de adapter voor de recyclerview
        LinearLayoutManager llm = new LinearLayoutManager(rootView.getContext());
        rv.setLayoutManager(llm);

        adapter = new BetAdapter(Repository.getBets(), rootView.getContext());
        rv.setAdapter(adapter);
    }

    private void createSpinner(){

        final TextView sortTv = (TextView) rootView.findViewById(R.id.sortText);
        sortspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSort = parent.getItemAtPosition(position).toString();
                sortTv.setText(selectedSort);
                ((TextView)view).setText(null);
                if(position==0)
                    Repository.sortbets(true);
                else
                    Repository.sortbets(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(getContext(), R.array.array_sort, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortspinner.setAdapter(dataAdapter);
    }


    @Override
    public void onBetsLoaded() {
        Log.d("bets loaded", "success");
        if(swipeRefreshLayout!=null)
            swipeRefreshLayout.setRefreshing(false);
        adapter.setBets(Repository.getBets());
    }

    @Override
    public void onLoadFailed() {
        Toast.makeText(getContext(), "Failed to load bets", Toast.LENGTH_LONG).show();
        if(swipeRefreshLayout!=null)
            swipeRefreshLayout.setRefreshing(false);
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

    @Override
    public void onBetUpdated(Bet bet) {
        adapter.notifyItemChanged(Repository.getBets().indexOf(bet));
        //adapter.notifyDataSetChanged();
    }

    @Override
    public void onUpdateFailed() {
        Toast.makeText(rootView.getContext(), "You already voted on this question", Toast.LENGTH_LONG).show();
    }
}
