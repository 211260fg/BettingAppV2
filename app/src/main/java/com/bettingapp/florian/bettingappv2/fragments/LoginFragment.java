package com.bettingapp.florian.bettingappv2.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bettingapp.florian.bettingappv2.R;
import com.bettingapp.florian.bettingappv2.activities.MainActivity;
import com.bettingapp.florian.bettingappv2.model.User;
import com.bettingapp.florian.bettingappv2.repo.OnLoggedInListener;
import com.bettingapp.florian.bettingappv2.repo.Repository;
import com.bettingapp.florian.bettingappv2.session.UserSessionManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements OnLoggedInListener {

    private EditText input_email;
    private EditText input_password;
    private Button btnLogin;
    private TextView link_signup;

    private ProgressDialog progressDialog;

    private View rootView;

    private OnFragmentInteractionListener mListener;


    public static LoginFragment getNewInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);


        input_email = (EditText) rootView.findViewById(R.id.input_email);
        input_password = (EditText) rootView.findViewById(R.id.input_password);
        btnLogin = (Button) rootView.findViewById(R.id.btnLogin);
        link_signup = (TextView) rootView.findViewById(R.id.link_signup);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        link_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Repository.registerListener(this);
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
        Repository.removeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(progressDialog!=null)
            progressDialog.dismiss();
    }

    private void login(){
        if(!validateFields()){
            return;
        }
        Repository.loginUser(input_email.getText().toString(), input_password.getText().toString());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

    }

    private void signup(){
        mListener.onFragmentInteraction( 0);
    }


    @Override
    public void onLoginSuccess(User user){

        if(progressDialog!=null)
            progressDialog.dismiss();

        UserSessionManager session = new UserSessionManager(getActivity().getApplicationContext());
        session.createUserLoginSession(input_email.getText().toString(), input_password.getText().toString());
        session.saveCurrentUser(user);

        Intent i = new Intent(getActivity(), MainActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    @Override
    public void onLoginFailed(){

        if(progressDialog!=null)
            progressDialog.dismiss();

        //TODO: juiste boodschap weergeven
        Toast.makeText(getActivity(), "LOGIN ERROR", Toast.LENGTH_LONG).show();
    }


    private boolean validateFields() {
        boolean valid = true;

        String email = input_email.getText().toString();
        String password = input_password.getText().toString();

        if (email.isEmpty()) {
            input_email.setError("username cannot be empty");
            valid = false;
        } else {
            input_email.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 20) {
            input_password.setError("between 4 and 20 characters");
            valid = false;
        } else {
            input_password.setError(null);
        }

        return valid;
    }


}
