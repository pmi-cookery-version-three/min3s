package com.t3man.minesweeper;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class LoseActivity extends Dialog implements View.OnClickListener {
    public Activity activity;
    public Dialog dialog;
    public Button back;
    public Button lossRefresh;
    public static TextView text_time;

    public LoseActivity(Activity activityLose) {
        super(activityLose);
        this.activity = activityLose;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alert_lose);

        text_time = (TextView) findViewById(R.id.text_time);

        text_time.setTypeface(MainActivity.Arcade);

        back = (Button) findViewById(R.id.button_back);
        back.setOnClickListener(this);

        lossRefresh = (Button) findViewById(R.id.button_lossRefresh);
        lossRefresh.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                dismiss();
                break;
            case R.id.button_lossRefresh:
                dismiss();
                MainActivity.Refresher();
                break;
            default:
                break;
        }
    }
}
