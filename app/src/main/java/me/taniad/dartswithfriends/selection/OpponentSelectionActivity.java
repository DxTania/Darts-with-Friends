package me.taniad.dartswithfriends.selection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.LinearLayout;

import me.taniad.dartswithfriends.R;


public class OpponentSelectionActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_opponent_selection);

        // since we are creating the game, we get to go first

        LinearLayout challengeFriend = (LinearLayout) findViewById(R.id.challenge_friend);
        challengeFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // find friend ..
                // then choose game type
                chooseGame();
            }
        });

        LinearLayout randomOpponent = (LinearLayout) findViewById(R.id.random_opponent);
        randomOpponent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // match with person ...
                // then choose game type
                chooseGame();
            }
        });
    }

    public void chooseGame() {
        Intent gameSelection = new Intent(
                OpponentSelectionActivity.this, GameSelectionActivity.class);
        startActivity(gameSelection);
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }
}
