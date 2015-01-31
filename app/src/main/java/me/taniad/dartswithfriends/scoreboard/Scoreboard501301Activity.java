package me.taniad.dartswithfriends.scoreboard;

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

import org.json.JSONObject;

import me.taniad.dartswithfriends.R;
import me.taniad.dartswithfriends.selection.Score501301Selection;


public class Scoreboard501301Activity extends ActionBarActivity {
    private final static int SEND_SCORE = 100;
    private static String type;
    private TextView waitingText, prevScoreUs, prevScoreOpponent;
    private RelativeLayout waitingScreen;
    private LinearLayout scoreboardYou, scoreboardOpponent, scoreboard;

    private int ourScore, opponentScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_scoreboard501301);
        setTitle("Scoreboard");

        final ImageButton addEntry = (ImageButton) findViewById(R.id.add_entry);
        addEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scoreSelect = new Intent(
                        Scoreboard501301Activity.this, Score501301Selection.class);
                startActivityForResult(scoreSelect, SEND_SCORE);
            }
        });
        waitingText = (TextView) findViewById(R.id.waiting_text);
        waitingScreen = (RelativeLayout) findViewById(R.id.waiting_screen);
        scoreboardYou = (LinearLayout) findViewById(R.id.scoreboard_you);
        scoreboardOpponent = (LinearLayout) findViewById(R.id.scoreboard_opponent);
        scoreboard = (LinearLayout) findViewById(R.id.scoreboard);

        type = getIntent().getStringExtra("gametype");
        // scores start at 501 or 301
        prevScoreUs = (TextView) findViewById(R.id.starting_score_you);
        prevScoreUs.setText(type);
        prevScoreOpponent = (TextView) findViewById(R.id.starting_score_opponent);
        prevScoreOpponent.setText(type);
        ourScore = Integer.valueOf(type);
        opponentScore = Integer.valueOf(type);

        boolean creation = getIntent().getBooleanExtra("creation", false);


        // TODO: wait for response from server before getting rid of loading
        waitingScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waitingScreen.setVisibility(View.INVISIBLE);
                addEntry.setVisibility(View.VISIBLE);
                scoreboard.setVisibility(View.VISIBLE);
            }
        });

        if (creation) {
            waitingText.setText("Creating game");
        } else {
            waitingText.setText("Loading game");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SEND_SCORE) {
            int score = data.getIntExtra("score", 0);
            subScore(score, true);
            // TODO: send score to server + opponent's turn now
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

    public void subScore(int sub, boolean us) {
        // add row to scoreboard with new score
        // cross out score above the new one
        TextView row = new TextView(this);
        row.setGravity(Gravity.CENTER);
        row.setPadding(5, 5, 5, 5);
        row.setTextSize(20);
        if (us) {
            ourScore -= sub;
            row.setText(String.valueOf(ourScore));
            scoreboardYou.addView(row);
            prevScoreUs.setPaintFlags(prevScoreUs.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            prevScoreUs = row;
        } else {
            opponentScore -= sub;
            row.setText(String.valueOf(opponentScore));
            scoreboardOpponent.addView(row);
            prevScoreOpponent.setPaintFlags(prevScoreOpponent.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            prevScoreOpponent = row;
        }
    }
}
