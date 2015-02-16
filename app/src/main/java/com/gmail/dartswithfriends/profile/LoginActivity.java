package com.gmail.dartswithfriends.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.gmail.dartswithfriends.MainActivity;
import com.gmail.dartswithfriends.async.FacebookLoginTask;


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
        final Intent main = new Intent(LoginActivity.this, MainActivity.class);
        final String accessToken = session.getAccessToken();
        Log.d("ASLDASD", accessToken);
        startActivity(main);
        new Request(
                Session.getActiveSession(),
                "/me",
                null,
                HttpMethod.GET,
                new Request.Callback() {
                    public void onCompleted(Response response) {
                        if (response != null) {
                            String id = response.getGraphObject().getProperty("id").toString();
                            FacebookLoginTask task = new FacebookLoginTask(accessToken, id);
                            task.execute((Void) null);
                        }
                    }
                }
        ).executeAsync();
    }
}
