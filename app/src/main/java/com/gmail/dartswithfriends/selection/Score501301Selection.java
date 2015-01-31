package com.gmail.dartswithfriends.selection;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.gmail.dartswithfriends.R;

public class Score501301Selection extends ActionBarActivity {
    private int dartNum = 1;
    private int multiplier = 1;
    private int score = 0;
    private int total_score = 0;
    private AlertDialog alertDialog = null;
    final View.OnClickListener scoreClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = ((TextView) v).getText().toString();
            switch (text.toLowerCase()) {
                case "bullseye":
                    score = 301;
                    break;
                case "miss":
                    score = 0;
                    break;
                default:
                    score = Integer.valueOf(text);
                    break;
            }

            AlertDialog.Builder alert = new AlertDialog.Builder(Score501301Selection.this);
            alert.setTitle("Dart " + dartNum + ": " + text);

            LinearLayout values = new LinearLayout(Score501301Selection.this);
            values.setOrientation(LinearLayout.VERTICAL);
            values.addView(createNumberDialog("Triple", 3));
            values.addView(createNumberDialog("Double", 2));
            values.addView(createNumberDialog("Single", 1));

            alert.setView(values);
            alert.setCancelable(true);
            alertDialog = alert.create();
            alertDialog.show();
        }
    };

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

        miss.setOnClickListener(scoreClick);
        bullseye.setOnClickListener(scoreClick);
        setRowClicks(row1);
        setRowClicks(row2);
        setRowClicks(row3);
        setRowClicks(row4);
    }

    public void setRowClicks(TableRow row) {
        for (int i = 0; i < row.getChildCount(); i++) {
            row.getChildAt(i).setOnClickListener(scoreClick);
        }
    }

    public void select(int score) {
        total_score += score;
        if (dartNum == 3) {
            Intent result = new Intent();
            result.putExtra("score", total_score);
            setResult(Activity.RESULT_OK, result);
            finish();
            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
        } else {
            dartNum++;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    public TextView createNumberDialog(String text, final int m) {
        TextView res = new TextView(Score501301Selection.this);
        res.setText(text);
        res.setTextSize(25);
        res.setPadding(5, 5, 5, 5);
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiplier = m;
                alertDialog.dismiss();
                select(score * multiplier);
            }
        });
        return res;
    }
}
