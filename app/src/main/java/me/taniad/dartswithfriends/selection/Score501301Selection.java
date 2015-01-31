package me.taniad.dartswithfriends.selection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import me.taniad.dartswithfriends.R;

public class Score501301Selection extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_score501301_selection);

        TextView bullseye = (TextView) findViewById(R.id.bullseye);
        TextView miss = (TextView) findViewById(R.id.miss);

        TableRow row1 = (TableRow) findViewById(R.id.row1);
        TableRow row2 = (TableRow) findViewById(R.id.row2);
        TableRow row3 = (TableRow) findViewById(R.id.row3);
        TableRow row4 = (TableRow) findViewById(R.id.row4);

        View.OnClickListener scoreClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = ((TextView) v).getText().toString();
                int score = 0;

                if (text.equals("bullseye")) {
                    score = 301;
                } else if (text.equals("miss")) {
                    score = 0;
                } else {
                    score = Integer.valueOf(text);
                }
                select(score);
            }
        };

        miss.setOnClickListener(scoreClick);
        bullseye.setOnClickListener(scoreClick);
        for (int i = 0; i < row1.getChildCount(); i++) {
            row1.getChildAt(i).setOnClickListener(scoreClick);
        }
        for (int i = 0; i < row2.getChildCount(); i++) {
            row2.getChildAt(i).setOnClickListener(scoreClick);
        }
        for (int i = 0; i < row3.getChildCount(); i++) {
            row3.getChildAt(i).setOnClickListener(scoreClick);
        }
        for (int i = 0; i < row4.getChildCount(); i++) {
            row4.getChildAt(i).setOnClickListener(scoreClick);
        }

    }

    public void select(int score) {
        // TODO: Single, double triple
        Intent result = new Intent();
        result.putExtra("score", score);
        setResult(Activity.RESULT_OK, result);
        finish();
    }
}
