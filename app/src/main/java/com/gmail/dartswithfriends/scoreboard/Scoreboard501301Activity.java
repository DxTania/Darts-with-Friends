package com.gmail.dartswithfriends.scoreboard;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.dartswithfriends.R;

import org.json.JSONObject;


public class Scoreboard501301Activity extends ActionBarActivity {
    private final static int SEND_SCORE = 100;
    private static String mGameType;
    private TextView mWaitingText;
    private RelativeLayout mWaitingScreen;
    private LinearLayout mScoreboard;
    private PlayerScore mPlayer, mOpponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_scoreboard501301);
        setTitle("Scoreboard");

        // Get intent information
        boolean creation = getIntent().getBooleanExtra("creation", false);
        mGameType = getIntent().getStringExtra("gametype");

        final ImageButton addEntry = (ImageButton) findViewById(R.id.add_entry);
        addEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scoreSelect = new Intent(
                        Scoreboard501301Activity.this, Score501301Selection.class);
                startActivityForResult(scoreSelect, SEND_SCORE);
            }
        });
        mWaitingText = (TextView) findViewById(R.id.waiting_text);
        mWaitingScreen = (RelativeLayout) findViewById(R.id.waiting_screen);
        mScoreboard = (LinearLayout) findViewById(R.id.scoreboard);

        // Set up players
        mPlayer = new PlayerScore();
        mPlayer.scoreboard = (LinearLayout) findViewById(R.id.scoreboard_you);
        mPlayer.previousScore = (TextView) findViewById(R.id.starting_score_you);
        mPlayer.previousScore.setText(mGameType);
        mPlayer.score = Integer.valueOf(mGameType);

        mOpponent = new PlayerScore();
        mOpponent.scoreboard = (LinearLayout) findViewById(R.id.scoreboard_opponent);
        mOpponent.previousScore = (TextView) findViewById(R.id.starting_score_opponent);
        mOpponent.previousScore.setText(mGameType);
        mOpponent.score = Integer.valueOf(mGameType);

        // TODO: wait for response from server before getting rid of loading
        mWaitingScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWaitingScreen.setVisibility(View.INVISIBLE);
                addEntry.setVisibility(View.VISIBLE);
                mScoreboard.setVisibility(View.VISIBLE);
            }
        });

        if (creation) {
            mWaitingText.setText("Creating game");
        } else {
            mWaitingText.setText("Loading game");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SEND_SCORE && resultCode == RESULT_OK) {
            int score = data.getIntExtra("score", 0);
            subScore(mPlayer, score);
            // TODO: send score to server + opponent's turn now
            Toast.makeText(this, "Opponent's turn!", Toast.LENGTH_SHORT).show();
        }
    }

    public void serverResponded(boolean ourTurn, JSONObject scores) {
        updateScores(scores);
        if (ourTurn) {
            // prompt user to click add entry
        } else {
            // show user they need to wait
        }
    }

    public void updateScores(JSONObject scores) {
        // get cached scores + send request to server for updated scores?
    }

    /**
     * Add score row to scoreboard
     * Cross out previous score
     *
     * @param player of recent play
     * @param sub    what they hit
     */
    public void subScore(PlayerScore player, int sub) {
        if (player.score - sub < 0) {
            return;
        }

        player.score -= sub;

        TextView row = new TextView(this);
        row.setGravity(Gravity.CENTER);
        row.setPadding(5, 5, 5, 5);
        row.setTextSize(20);
        row.setText(String.valueOf(player.score));

        player.previousScore.setPaintFlags(
                player.previousScore.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        player.previousScore = row;
        player.scoreboard.addView(row);
    }

    static class PlayerScore {
        int score;
        LinearLayout scoreboard;
        TextView previousScore;
    }
}
