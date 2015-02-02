package com.gmail.dartswithfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.gmail.dartswithfriends.profile.LoginActivity;
import com.gmail.dartswithfriends.scoreboard.Scoreboard501301Activity;
import com.gmail.dartswithfriends.selection.OpponentSelectionActivity;

public class MainActivity extends ActionBarActivity {

    private TextView mPlayerWelcome;
    private LinearLayout mMainLayout;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Darts with Friends");
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mPlayerWelcome = (TextView) findViewById(R.id.playerName);
        mMainLayout = (LinearLayout) findViewById(R.id.main);

        Button createGame = (Button) findViewById(R.id.create_game_button);
        createGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent opponent = new Intent(MainActivity.this, OpponentSelectionActivity.class);
                startActivity(opponent);
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
            }
        });

        Button joinGame = (Button) findViewById(R.id.join_game_button);
        joinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: send join request to server for random game
                // once opponent is found, use game info to start correct scoreboard
                Intent game = new Intent(MainActivity.this, Scoreboard501301Activity.class);
                game.putExtra("creation", false);
                startActivity(game);
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
            }
        });

        Button logOut = (Button) findViewById(R.id.logout_button);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session session = Session.getActiveSession();
                if (session != null) {
                    if (!session.isClosed()) {
                        session.closeAndClearTokenInformation();
                        Intent login = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(login);
                        finish();
                    }
                }
            }
        });

        new Request(
                Session.getActiveSession(),
                "/me",
                null,
                HttpMethod.GET,
                new Request.Callback() {
                    public void onCompleted(Response response) {
                        if (response != null) {
                            mProgressBar.setVisibility(View.GONE);
                            mMainLayout.setVisibility(View.VISIBLE);
                            String name = response.getGraphObject().getProperty("name").toString();
                            mPlayerWelcome.setText("Welcome, " + name);
                        }
                    }
                }
        ).executeAsync();
    }
}
