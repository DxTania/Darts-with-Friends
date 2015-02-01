package com.gmail.dartswithfriends.scoreboard;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ScoreboardUtils {

    public static AlertDialog getSingleTripleAlert(Context context, int dartNum, String text,
                                                   View.OnClickListener onClickListener) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Dart " + dartNum + ": " + text);

        LinearLayout values = new LinearLayout(context);
        values.setOrientation(LinearLayout.VERTICAL);
        values.addView(createNumberDialog("Triple", onClickListener, context, 3));
        values.addView(createNumberDialog("Double", onClickListener, context, 2));
        values.addView(createNumberDialog("Single", onClickListener, context, 1));

        alert.setView(values);
        alert.setCancelable(true);
        return alert.create();
    }

    public static TextView createNumberDialog(String text, View.OnClickListener onClickListener,
                                              Context context, int tag) {
        TextView res = new TextView(context);
        res.setText(text);
        res.setTextSize(25);
        res.setPadding(5, 5, 5, 5);
        res.setTag(tag);
        res.setOnClickListener(onClickListener);
        return res;
    }
}
