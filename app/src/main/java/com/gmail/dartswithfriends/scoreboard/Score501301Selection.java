package com.gmail.dartswithfriends.scoreboard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.gmail.dartswithfriends.R;

public class Score501301Selection extends ActionBarActivity {
    private int mDartNum = 1;
    private int mMultiplier = 1;
    private int mTotalScore = 0;
    private AlertDialog mAlertDialog = null;
    final View.OnClickListener mScoreClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text;
            if (v.getTag() != null && v.getTag().equals("miss")) {
                select(0);
            } else if (v.getTag() != null && v.getTag().equals("bullseye")) {
                select(301);
            } else {
                text = ((TextView) v).getText().toString();
                final int score = Integer.valueOf(text);
                View.OnClickListener click = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMultiplier = (Integer) v.getTag();
                        select(score * mMultiplier);
                        mAlertDialog.dismiss();
                    }
                };
                mAlertDialog = ScoreboardUtils.getSingleTripleAlert(
                        Score501301Selection.this, mDartNum, text, click);
                mAlertDialog.show();
            }
        }
    };

    public void select(int score) {
        mTotalScore += score;
        if (mDartNum == 3) {
            Intent result = new Intent();
            result.putExtra("score", mTotalScore);
            setResult(Activity.RESULT_OK, result);
            finish();
            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
        } else {
            mDartNum++;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_score501301_selection);

        TableRow bullseye = (TableRow) findViewById(R.id.bullseye);
        TableRow miss = (TableRow) findViewById(R.id.miss);

        TableRow row1 = (TableRow) findViewById(R.id.row1);
        TableRow row2 = (TableRow) findViewById(R.id.row2);
        TableRow row3 = (TableRow) findViewById(R.id.row3);
        TableRow row4 = (TableRow) findViewById(R.id.row4);

        miss.setOnClickListener(mScoreClick);
        bullseye.setOnClickListener(mScoreClick);
        setRowClicks(row1);
        setRowClicks(row2);
        setRowClicks(row3);
        setRowClicks(row4);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
        }
    }

    public void setRowClicks(TableRow row) {
        for (int i = 0; i < row.getChildCount(); i++) {
            row.getChildAt(i).setOnClickListener(mScoreClick);
        }
    }
}
