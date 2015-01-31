package me.taniad.dartswithfriends.selection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.LinearLayout;

import me.taniad.dartswithfriends.R;
import me.taniad.dartswithfriends.scoreboard.ScoreboardCricketActivity;


public class GameSelectionActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_game_selection);

        LinearLayout cricket = (LinearLayout) findViewById(R.id.cricket);
        cricket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cricket = new Intent(
                        GameSelectionActivity.this, ScoreboardCricketActivity.class);
                // TODO: send game creation to server with opponent selection + wait server response
                cricket.putExtra("creation", true);
                startActivity(cricket);
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                finish();
            }
        });

        LinearLayout game501301 = (LinearLayout) findViewById(R.id.game501301);
        game501301.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent select501301 = new Intent(
                        GameSelectionActivity.this, Select501301Activity.class);
                startActivity(select501301);
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                finish();
            }
        });
    }
}
