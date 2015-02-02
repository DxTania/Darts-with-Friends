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
import android.widget.Toast;

import com.gmail.dartswithfriends.R;

import java.util.ArrayList;


public class ScoreboardCricketActivity extends ActionBarActivity {
    private TextView mWaitingText;
    private RelativeLayout mWaitingScreen;
    private LinearLayout mScoreboard;
    private AlertDialog mAlertDialog;
    private int mDartNum = 1;
    private PlayerScore mPlayer, mOpponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_scoreboard_cricket);
        setTitle("Scoreboard");

        boolean creation = getIntent().getBooleanExtra("creation", false);

        mWaitingText = (TextView) findViewById(R.id.waiting_text);
        mWaitingScreen = (RelativeLayout) findViewById(R.id.waiting_screen);
        mScoreboard = (LinearLayout) findViewById(R.id.scoreboard);

        TableLayout score_numbers = (TableLayout) findViewById(R.id.score_numbers);
        for (int i = 0; i < score_numbers.getChildCount(); i++) {
            final TableRow row = (TableRow) score_numbers.getChildAt(i);
            row.setBackgroundResource(R.drawable.pressed_grey);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View.OnClickListener click = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            markScore(true, row, (Integer) v.getTag());
                            mAlertDialog.dismiss();
                        }
                    };
                    // there are 5 cols, middle is label
                    String text = ((TextView) row.getChildAt(2)).getText().toString();
                    if (!text.equals(getString(R.string.miss))) {
                        mAlertDialog = ScoreboardUtils.getSingleTripleAlert(
                                ScoreboardCricketActivity.this, mDartNum, text, click);
                        mAlertDialog.show();
                    } else {
                        markScore(true, row, 0);
                    }
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

        mPlayer = new PlayerScore();
        mPlayer.numberStates = numberStates;
        mOpponent = new PlayerScore();
        mOpponent.numberStates = numberStates;

        // TODO: wait for response from server before getting rid of loading
        mWaitingScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWaitingScreen.setVisibility(View.INVISIBLE);
                mScoreboard.setVisibility(View.VISIBLE);
            }
        });

        if (creation) {
            mWaitingText.setText("Creating game");
        } else {
            mWaitingText.setText("Loading game");
        }
    }

    private void markScore(boolean us, TableRow row, int marks) {
        TextView markOne = (TextView) row.getChildAt(0);
        TextView markTwo = (TextView) row.getChildAt(1);
        String text = ((TextView) row.getChildAt(2)).getText().toString();

        if (us) {
            int score = 0;
            mDartNum++;
            if (text.equals(getString(R.string.bulls))) {
                score = 25;
            } else if (text.equals(getString(R.string.miss))) {
                score = 0;
            } else {
                score = Integer.valueOf(text);
            }

            NumberState number = updateNumberState(score, marks, mPlayer);

            int prevMarks = number.marks - marks;
            if (prevMarks == 0) {
                if (marks == 1) {
                    markTwo.setText("/");
                } else if (marks == 2) {
                    markTwo.setText("X");
                } else if (marks >= 3) {
                    markTwo.setText("O");
                }
            } else if (prevMarks == 1) {
                if (marks == 1) {
                    markTwo.setText("X");
                } else if (marks > 1) {
                    markTwo.setText("(/)");
                }
            } else if (prevMarks == 2) {
                markTwo.setText("(X)");
            }

            if (mDartNum == 4) {
                // opponent's turn! TODO: send scores to server and wait
                mDartNum = 1;
                Toast.makeText(this, "Opponent's turn!", Toast.LENGTH_SHORT).show();
            }
        } else {

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
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
