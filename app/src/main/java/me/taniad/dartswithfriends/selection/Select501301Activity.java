package me.taniad.dartswithfriends.selection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.LinearLayout;

import me.taniad.dartswithfriends.R;
import me.taniad.dartswithfriends.scoreboard.Scoreboard501301Activity;


public class Select501301Activity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_501301_selection);

        LinearLayout select301 = (LinearLayout) findViewById(R.id.select301);
        select301.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendGame("301");
            }
        });

        LinearLayout select501 = (LinearLayout) findViewById(R.id.select501);
        select501.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendGame("501");
            }
        });
    }

    public void sendGame(String type) {
        Intent scoreboard = new Intent(
                Select501301Activity.this, Scoreboard501301Activity.class);
        scoreboard.putExtra("gametype", type);
        // TODO: send game creation to server with opponent selection + wait server response
        scoreboard.putExtra("creation", true);

        startActivity(scoreboard);
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
        finish();
    }
}
