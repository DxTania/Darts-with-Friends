package me.taniad.dartswithfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.LinearLayout;


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
        Intent opponent = new Intent(
                Select501301Activity.this, Scoreboard501301Activity.class);
        opponent.putExtra("gametype", type);
        startActivity(opponent);
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }
}
