package com.gmail.dartswithfriends.selection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.gmail.dartswithfriends.R;
import com.gmail.dartswithfriends.profile.FriendSelectionActivity;


public class OpponentSelectionActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_opponent_selection);

        LinearLayout challengeFriend = (LinearLayout) findViewById(R.id.challenge_friend);
        challengeFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // find friend .. id
                Intent chooseFriend = new Intent(
                        OpponentSelectionActivity.this, FriendSelectionActivity.class);
                startActivity(chooseFriend);
                finish();
            }
        });

        LinearLayout randomOpponent = (LinearLayout) findViewById(R.id.random_opponent);
        randomOpponent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // match with person ... get id
                Intent gameSelection = new Intent(
                        OpponentSelectionActivity.this, GameSelectionActivity.class);
                // TODO: have id of opponent to send to game selection
                startActivity(gameSelection);
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                finish();
            }
        });
    }
}
