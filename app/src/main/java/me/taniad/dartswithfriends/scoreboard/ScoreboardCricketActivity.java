package me.taniad.dartswithfriends.scoreboard;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import me.taniad.dartswithfriends.R;


public class ScoreboardCricketActivity extends ActionBarActivity {

    private int ourScore, opponentScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_scoreboard_cricket);
        setTitle("Scoreboard");
    }
}
