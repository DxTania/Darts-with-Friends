package me.taniad.dartswithfriends.scoreboard;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONObject;

import me.taniad.dartswithfriends.R;


public class Scoreboard501301Activity extends ActionBarActivity {
    private static String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_scoreboard501301);
        setTitle("Scoreboard");

        ImageButton addEntry = (ImageButton) findViewById(R.id.add_entry);
        addEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pop up dart score selection
            }
        });


        type = getIntent().getStringExtra("gametype");
        // scores start at 501 or 301
        TextView startingScoreYou = (TextView) findViewById(R.id.starting_score_you);
        startingScoreYou.setText(type);
        TextView startingScoreOpp = (TextView) findViewById(R.id.starting_score_opponent);
        startingScoreOpp.setText(type);

        boolean creation = getIntent().getBooleanExtra("creation", false);
        // TODO: wait for response from server
        if (creation) {
            // ... Creating game ....
        } else {
            // ... Loading game ...
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scoreboard501301, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
