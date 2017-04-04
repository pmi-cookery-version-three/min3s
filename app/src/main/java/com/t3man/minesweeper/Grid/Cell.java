package com.t3man.minesweeper.Grid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.t3man.minesweeper.Bomb_Gen.Engine;
import com.t3man.minesweeper.MainActivity;
import com.t3man.minesweeper.R;

public class Cell extends BaseCell implements View.OnClickListener , View.OnLongClickListener{

    public static int flag_end_counter = 0;
    public static int flagged = 0;

    public static int[][] flags = new int[Engine.width][Engine.height];

    MediaPlayer mediaPlayer;

    public Cell( Context context , int x , int y ){
        super(context);

        setPosition(x,y);

        setOnClickListener(this);
        setOnLongClickListener(this);
    }

    public static void refreshFlags(){

        for( int x = 0 ; x < Engine.width ; x++ ){
            for( int y = 0 ; y < Engine.height ; y++ ){
                flags[x][y] = 0;
            }
        }

        flag_end_counter = Engine.bomb_counter;

        flagged = 0;

        MainActivity.last_time = "" + 0;

        MainActivity.text_refresh(flag_end_counter);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    public void onClick(View v) {

        if(flags[getXPos()][getYPos()] == 1){}
        else {

            mediaPlayer = MediaPlayer.create(getContext(), R.raw.beep_touch);

            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = MediaPlayer.create(getContext(), R.raw.beep_touch);
                } mediaPlayer.start();
            } catch(Exception e) { e.printStackTrace(); }

            Engine.getInstance().click(getXPos(), getYPos());
        }
    }

    @Override
    public boolean onLongClick(View v) {

        if(flags[getXPos()][getYPos()] == 1 && flagged != 0) {
            flagged--;
        }
        if(flagged < Engine.bomb_num && !isRevealed()) {

            mediaPlayer = MediaPlayer.create(getContext(), R.raw.beep_long);

            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = MediaPlayer.create(getContext(), R.raw.beep_long);
                } mediaPlayer.start();
            } catch(Exception e) { e.printStackTrace(); }

            Engine.getInstance().flag(getXPos(), getYPos());

            flagCheck(getXPos(), getYPos());

            //if all flags are at positions of bombs
            Engine.getInstance().checkEnd();

            flag_end_counter = Engine.bomb_counter;
            flagged = 0;

            for (int i = 0; i < Engine.width; i++) {
                for (int j = 0; j < Engine.height; j++) {
                    if (flags[i][j] == 1) {
                        flag_end_counter--;
                        flagged++;
                    }
                }
            }

            MainActivity.text_refresh(flag_end_counter);
        }
        return true;
    }

    public void flagCheck(int x, int y){

        if(flags[x][y] == 1){
            flags[x][y] = 0;
        }
        else {
            flags[x][y] = 1;
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("Minesweeper" , "Cell::onDraw");
        drawButton(canvas);

        if( isFlagged() ){
            drawFlag(canvas);
        }else if( isRevealed() && isBomb() && !isClicked() ){
            drawNormalBomb(canvas);
        }else {
            if( isClicked() ){
                if( getValue() == -1 ){
                    drawBombExploded(canvas);
                }else {
                    drawNumber(canvas);
                }
            }else{
                drawButton(canvas);
            }
        }
    }

    private void drawBombExploded(Canvas canvas ){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.mipmap.cell_bomb);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private void drawFlag( Canvas canvas ){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.mipmap.cell_flag);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private void drawButton(Canvas canvas ){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.mipmap.cell_standart);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private void drawNormalBomb(Canvas canvas ){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.mipmap.cell_bomb_normal);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private void drawNumber( Canvas canvas ){
        Drawable drawable = null;

        switch (getValue() ){
            case 0:
                drawable = ContextCompat.getDrawable(getContext(), R.mipmap.cell_empty);
                break;
            case 1:
                drawable = ContextCompat.getDrawable(getContext(), R.mipmap.cell_1);
                break;
            case 2:
                drawable = ContextCompat.getDrawable(getContext(), R.mipmap.cell_2);
                break;
            case 3:
                drawable = ContextCompat.getDrawable(getContext(), R.mipmap.cell_3);
                break;
            case 4:
                drawable = ContextCompat.getDrawable(getContext(), R.mipmap.cell_4);
                break;
            case 5:
                drawable = ContextCompat.getDrawable(getContext(), R.mipmap.cell_5);
                break;
            case 6:
                drawable = ContextCompat.getDrawable(getContext(), R.mipmap.cell_6);
                break;
            case 7:
                drawable = ContextCompat.getDrawable(getContext(), R.mipmap.cell_7);
                break;
            case 8:
                drawable = ContextCompat.getDrawable(getContext(), R.mipmap.cell_8);
                break;
        }

        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }
}
