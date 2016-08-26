package com.bettingapp.florian.bettingappv2.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bettingapp.florian.bettingappv2.R;
import com.bettingapp.florian.bettingappv2.activities.MainActivity;
import com.bettingapp.florian.bettingappv2.model.User;
import com.bettingapp.florian.bettingappv2.repo.OnLoggedInListener;
import com.bettingapp.florian.bettingappv2.repo.Repository;
import com.bettingapp.florian.bettingappv2.session.UserSessionManager;


public class RegisterFragment extends Fragment implements OnLoggedInListener{


    private OnFragmentInteractionListener mListener;

    private View rootView;

    private EditText input_username;
    private EditText input_password;
    private EditText input_confirmpassword;

    private ImageButton btnTakepicture;

    private Button link_back;
    private Button btnRegister;

    private ProgressDialog progressDialog;


    public static RegisterFragment getNewInstance() {
        return new RegisterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_register, container, false);

        input_username = (EditText) rootView.findViewById(R.id.input_username);
        input_password = (EditText) rootView.findViewById(R.id.input_password);
        input_confirmpassword = (EditText) rootView.findViewById(R.id.input_confirmpassword);
        btnTakepicture = (ImageButton) rootView.findViewById(R.id.btnTakepicture);

        link_back = (Button) rootView.findViewById(R.id.link_back);
        btnRegister = (Button) rootView.findViewById(R.id.btnRegister);

        link_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentInteraction(OnFragmentInteractionListener.fragment.loginfragment, 0);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        btnTakepicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

        Repository.registerListener(this);

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==0){
            switch (resultCode){
                case Activity.RESULT_OK: break;
                case Activity.RESULT_CANCELED: break;
                default: break;
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(progressDialog!=null)
            progressDialog.dismiss();
    }

    private void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intent, 0);
    }

    private void signup(){

        if(!validateFields()){
            return;
        }
        Repository.signupUser(input_username.getText().toString(), input_password.getText().toString());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }


    private boolean validateFields() {
        boolean valid = true;

        String username = input_username.getText().toString();
        String password = input_password.getText().toString();
        String confirmpassword = input_confirmpassword.getText().toString();

        if (username.isEmpty()) {
            input_username.setError("username cannot be empty");
            valid = false;
        } else {
            input_username.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 20) {
            input_password.setError("between 4 and 20 characters");
            valid = false;
        } else {
            input_password.setError(null);
        }

        if (! confirmpassword.equals(password)) {
            input_confirmpassword.setError("not equal to password");
            valid = false;
        } else {
            input_confirmpassword.setError(null);
        }

        return valid;
    }

    @Override
    public void onLoginSuccess(User user){

        if(progressDialog!=null)
            progressDialog.dismiss();

        UserSessionManager session = new UserSessionManager(getActivity().getApplicationContext());
        session.createUserLoginSession(input_username.getText().toString(), input_password.getText().toString());
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
        Toast.makeText(getActivity(), "SIGNUP ERROR", Toast.LENGTH_LONG).show();
    }
}
