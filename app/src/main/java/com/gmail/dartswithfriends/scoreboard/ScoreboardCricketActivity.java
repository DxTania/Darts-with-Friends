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

import java.util.ArrayList;


public class ScoreboardCricketActivity extends ActionBarActivity {
    private TextView waitingText;
    private RelativeLayout waitingScreen;
    private LinearLayout scoreboard;
    private AlertDialog alertDialog;
    private int dartNum = 1;
    private PlayerScore player, opponent;

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

        // Set up players
        ArrayList<NumberState> numberStates = new ArrayList<>();
        for (int i = 20; i >= 15; i--) {
            NumberState number = new NumberState();
            number.score = i;
            number.open = false;
            numberStates.add(number);
        }
        NumberState bulls = new NumberState();
        bulls.score = 25;
        numberStates.add(bulls);

        player = new PlayerScore();
        player.numberStates = numberStates;
        opponent = new PlayerScore();
        opponent.numberStates = numberStates;

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

    private void markScore(boolean us, TableRow row, int marks) {
        TextView markOne = (TextView) row.getChildAt(0);
        TextView markTwo = (TextView) row.getChildAt(1);
        String text = ((TextView) row.getChildAt(2)).getText().toString();

        if (us) {
            int score = 0;
            dartNum++;
            if (text.equals(getString(R.string.bulls))) {
                score = 25;
            } else if (text.equals(getString(R.string.miss))) {
                score = 0;
            } else {
                score = Integer.valueOf(text);
            }

            NumberState number = updateNumberState(score, marks, player);

            int prevMarks = number.marks - marks;
            if (prevMarks == 0) {
                if (marks == 1) {
                    markTwo.setText("/");
                } else if (marks == 2) {
                    markTwo.setText("X");
                } else {
                    markTwo.setText("O");
                }
            } else if (prevMarks == 1) {
                if (marks == 1) {
                    markTwo.setText("X");
                } else if (marks == 2) {
                    markTwo.setText("O/");
                }
            } else if (prevMarks == 2) {
                markTwo.setText("XXⓍ");
            }

            if (dartNum == 4) {
                // opponent's turn! TODO: send scores to server and wait
                dartNum = 1;
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

    /**
     * @param number 's state to update
     * @param marks  how many times
     * @param player who's turn it is
     */
    private NumberState updateNumberState(int number, int marks, PlayerScore player) {
        int scored = 0;
        NumberState num = null;
        for (int i = 0; i < player.numberStates.size(); i++) {
            num = player.numberStates.get(i);
            if (num.score == number) {
                num.marks += marks;
                if (num.marks >= 3) {
                    num.open = true;
                    num.openFor = player;
                    scored += (num.marks - 3) * number;
                }
                break;
            }
        }
        player.totalScore += scored;
        return num;
    }

    static class NumberState {
        int score, marks = 0;
        boolean open;
        PlayerScore openFor;
    }

    static class PlayerScore {
        int totalScore = 0;
        int turnScore = 0;
        ArrayList<NumberState> numberStates;
    }
}
