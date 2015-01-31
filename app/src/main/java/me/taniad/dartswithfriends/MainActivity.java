package me.taniad.dartswithfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import me.taniad.dartswithfriends.selection.OpponentSelectionActivity;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Darts with Friends");
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        Button createGame = (Button) findViewById(R.id.create_game_button);
        createGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent opponent = new Intent(MainActivity.this, OpponentSelectionActivity.class);
                startActivity(opponent);
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
            }
        });

        Button joinGame = (Button) findViewById(R.id.join_game_button);
        joinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: send join request to server for random game
                // once opponent is found, use game info to start correct scoreboard
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
