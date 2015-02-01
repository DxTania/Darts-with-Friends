package com.gmail.dartswithfriends.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.facebook.Session;
import com.facebook.SessionState;
import com.gmail.dartswithfriends.MainActivity;


public class LoginActivity extends FragmentActivity implements LoginFragment.OnFragmentInteractionListener {
    private LoginFragment mLoginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            // Add the fragment on initial activity setup
            mLoginFragment = LoginFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, mLoginFragment)
                    .commit();
        } else {
            // Or set the fragment from restored state info
            mLoginFragment = (LoginFragment) getSupportFragmentManager()
                    .findFragmentById(android.R.id.content);
        }
    }

    @Override
    public void onSessionSuccess(Session session, SessionState state) {
        Intent main = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(main);
    }
}
