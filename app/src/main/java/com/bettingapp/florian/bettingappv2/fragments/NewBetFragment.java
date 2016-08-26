package com.bettingapp.florian.bettingappv2.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.bettingapp.florian.bettingappv2.R;
import com.bettingapp.florian.bettingappv2.model.Bet;
import com.bettingapp.florian.bettingappv2.model.BetCategory;
import com.bettingapp.florian.bettingappv2.repo.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class NewBetFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    private View rootView;

    private OnFragmentInteractionListener mListener;

    private EditText bet_title;
    private EditText bet_optionA;
    private EditText bet_optionB;
    private EditText bet_reward;
    private Spinner spinner_category;

    private Button btnCancel;
    private Button btnSave;

    public NewBetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_new_bet, container, false);

        bet_title = (EditText) rootView.findViewById(R.id.bet_title);
        bet_optionA = (EditText) rootView.findViewById(R.id.bet_optionA);
        bet_optionB = (EditText) rootView.findViewById(R.id.bet_optionB);
        bet_reward = (EditText) rootView.findViewById(R.id.bet_reward);

        final Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner_category);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        BetCategory[] possibleValues = BetCategory.class.getEnumConstants();
        List<String> categories = new ArrayList<>();

        // Creating adapter for spinner
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(getContext(), R.array.array_categories, android.R.layout.simple_spinner_item);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


        btnCancel = (Button) rootView.findViewById(R.id.btnCancel);
        btnSave = (Button) rootView.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bet bet = new Bet(bet_title.getText().toString(), bet_optionA.getText().toString(), bet_optionB.getText().toString(), bet_reward.getText().toString(), BetCategory.valueOf(spinner.getSelectedItem().toString()));
                Repository.addBet(bet);
                mListener.onFragmentInteraction(OnFragmentInteractionListener.fragment.betsfragment, 0);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentInteraction(OnFragmentInteractionListener.fragment.betsfragment, 0);
            }
        });

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
