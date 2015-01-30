package me.taniad.dartswithfriends;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class Scoreboard501301Activity extends ActionBarActivity {
    private static String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard501301);

        type = getIntent().getStringExtra("gametype");

        TextView startingScoreYou = (TextView) findViewById(R.id.starting_score_you);
        startingScoreYou.setText(type);

        TextView startingScoreOpp = (TextView) findViewById(R.id.starting_score_opponent);
        startingScoreOpp.setText(type);

        // Game begins!
        // TODO: We should know whether we are going first or not by now
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
