package com.t3man.minesweeper;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.t3man.minesweeper.Bomb_Gen.Engine;

public class MainActivity extends Activity {

    public static TextView text1;
    public static TextView text2;
    public static int timer_ticks;
    public static Handler handler;
    private static boolean running = true;
    public static String TEXT_TIMER;

    public static String last_time;

    public static WinActivity WActivity;
    public static LoseActivity LActivity;
    public static Typeface Arcade;

    TextView TV_Lose;
    TextView TV_Win;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        WActivity = new WinActivity(MainActivity.this);
        LActivity = new LoseActivity(MainActivity.this);

        text1 = (TextView) findViewById(R.id.textView);
        text2 = (TextView) findViewById(R.id.textView3);

        Arcade = Typeface.createFromAsset(getAssets(), "fonts/arcade.ttf");
        text1.setTypeface(Arcade);
        text2.setTypeface(Arcade);

        text1.setText("" + Engine.bomb_counter + "/" + Engine.bomb_num);
        text2.setText("" + 0);

        TEXT_TIMER = "";

        Log.e("MainActivity", "onCreate");
        Engine.getInstance().createGrid(this);

        Button button = (Button) findViewById(R.id.button);
        button.setEnabled(false);

        Button Btn_refresh = (Button) findViewById(R.id.button_refresh);

        Btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Refresher();

            }
        });

        timer();
    }

    public static void timer_stop() {
        running = false;

        last_time = text2.getText().toString();
    }

    public static void text_refresh(int counter) {

        text1.setText("" + counter + "/" + Engine.bomb_num);
    }

    //timer
    public static void timer() {
        handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (running) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            timer_ticks += 1;
                            text2.setText(String.valueOf(timer_ticks));
                        }
                    });
                }
            }
        };
        new Thread(runnable).start();
    }

    //refresher
    public static void Refresher() {
        Engine.getInstance().refreshGrid();
        Log.e("Refresh button", "Refreshed");

        timer_ticks = 0;

        text2.setText("" + 0);

        TEXT_TIMER = "";

        if (running == false) {
            running = true;
            timer();
        }
    }
}
