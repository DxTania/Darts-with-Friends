package me.taniad.dartswithfriends.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.facebook.Session;
import com.facebook.SessionState;

import me.taniad.dartswithfriends.MainActivity;


public class LoginActivity extends FragmentActivity implements LoginFragment.OnFragmentInteractionListener {
    private LoginFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            // Add the fragment on initial activity setup
            mainFragment = LoginFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, mainFragment)
                    .commit();
        } else {
            // Or set the fragment from restored state info
            mainFragment = (LoginFragment) getSupportFragmentManager()
                    .findFragmentById(android.R.id.content);
        }
    }

    @Override
    public void onSessionSuccess(Session session, SessionState state) {
        Intent main = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(main);
    }
}
