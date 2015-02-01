package com.gmail.dartswithfriends.scoreboard;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.gmail.dartswithfriends.R;


public class ScoreboardCricketActivity extends ActionBarActivity {
    private TextView waitingText;
    private RelativeLayout waitingScreen;
    private LinearLayout scoreboard;
    private int dartNum = 1;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_scoreboard_cricket);
        setTitle("Scoreboard");

        boolean creation = getIntent().getBooleanExtra("creation", false);

        waitingText = (TextView) findViewById(R.id.waiting_text);
        waitingScreen = (RelativeLayout) findViewById(R.id.waiting_screen);
        scoreboard = (LinearLayout) findViewById(R.id.scoreboard);

        TableLayout score_numbers = (TableLayout) findViewById(R.id.score_numbers);
        for (int i = 0; i < score_numbers.getChildCount(); i++) {
            final TableRow row = (TableRow) score_numbers.getChildAt(i);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View.OnClickListener click = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            markScore(true, row, (Integer) v.getTag());
                            alertDialog.dismiss();
                        }
                    };
                    // there are 5 cols, middle is label
                    String text = ((TextView) row.getChildAt(2)).getText().toString();
                    alertDialog = ScoreboardUtils.getSingleTripleAlert(
                            ScoreboardCricketActivity.this, dartNum, text, click);
                    alertDialog.show();
                }
            });
        }

        // TODO: wait for response from server before getting rid of loading
        waitingScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waitingScreen.setVisibility(View.INVISIBLE);
                scoreboard.setVisibility(View.VISIBLE);
            }
        });

        if (creation) {
            waitingText.setText("Creating game");
        } else {
            waitingText.setText("Loading game");
        }
    }

    private void markScore(boolean us, TableRow row, int mark) {
        TextView markOne = (TextView) row.getChildAt(0);
        TextView markTwo = (TextView) row.getChildAt(1);
        if (us) {
            dartNum++;
            if (markTwo.getVisibility() == View.INVISIBLE) {
                markTwo.setVisibility(View.VISIBLE);
            } else if (markOne.getText().toString().isEmpty()) {
                markOne.setVisibility(View.VISIBLE);
            }
            if (dartNum == 4) {
                // opponent's turn!
            }
        } else {

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }
}
