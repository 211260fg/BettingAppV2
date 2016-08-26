package com.bettingapp.florian.bettingappv2.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bettingapp.florian.bettingappv2.R;
import com.bettingapp.florian.bettingappv2.fragments.LoginFragment;
import com.bettingapp.florian.bettingappv2.fragments.OnFragmentInteractionListener;
import com.bettingapp.florian.bettingappv2.fragments.RegisterFragment;

public class AuthenticationActivity extends AppCompatActivity implements OnFragmentInteractionListener {


    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;
    private ImageView headerimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);


        loginFragment = LoginFragment.getNewInstance();

        headerimg = (ImageView) findViewById(R.id.img_authheader);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmenPane, loginFragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_authentication, menu);
        return true;
    }


    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    @Override
    public void onFragmentInteraction(OnFragmentInteractionListener.fragment fragment, int pos) {
        if(loginFragment!=null&&loginFragment.isVisible()){
            registerFragment = RegisterFragment.getNewInstance();

            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                    .replace(R.id.fragmenPane, registerFragment).commit();

            Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
            toggleTranslateImg(slideUp);

        }else{

            loginFragment = LoginFragment.getNewInstance();

            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                    .replace(R.id.fragmenPane, loginFragment).commit();

            Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
            toggleTranslateImg(slideDown);

        }
    }


    private void toggleTranslateImg(Animation animation){

        animation.setFillEnabled(true);
        animation.setFillAfter(true);

        headerimg.startAnimation(animation);
    }
}
