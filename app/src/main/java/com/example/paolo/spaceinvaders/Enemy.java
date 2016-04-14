package com.example.paolo.spaceinvaders;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Paolo on 4/8/2016.
 */
public class Enemy{

    final int RIGHT = 1;
    final int LEFT  = -1;
    final int DOWN = 2;
    final int radius = 40;
    final boolean DONE = true;

    static boolean isOutOfBoundsLeft;
    static boolean isOutOfBoundsRight;
    static int horizDirection;
    static boolean dropping;

    boolean isAlive;

    int x, start_x;
    int y, start_y;
    int dx = 10;
    int dy;

    int screen_X, screen_Y;

    Bitmap enemyicon;

    public Rect enemySpace = new Rect();

    public Enemy(int offset, int screenX, int screenY, Bitmap icon){
        isAlive = true;

        x = 220 + 100*(offset % 5);
        y = 220 + 100*(offset / 5);

        dy = 100;

        screen_X = screenX;
        screen_Y = screenY;

        isOutOfBoundsLeft = false;
        isOutOfBoundsRight = false;
        horizDirection = RIGHT;
        dropping = false;

        enemyicon = icon;

    }

    void draw(Canvas canvas){
        if(isAlive) {
            enemySpace.set(x - radius, y - radius, x + radius, y + radius);
            canvas.drawBitmap(enemyicon, null, enemySpace, null);
        }
    }

    boolean checkNextState() {
        if(isAlive) {
            //Check the state
            if (dropping == false){
                Log.d("Log.DEBUG", "dropping = false");
                if (x < 0) {
                    isOutOfBoundsLeft = true;
                    return DONE;
                } else if (x > screen_X) {
                    isOutOfBoundsRight = true;
                    Log.d("Log.DEBUG", "OutofBoundsRight = true");
                    return DONE;
                }
            }
            else {
                dropping = false;
                return DONE;
            }
        }
        return false;
    }

    void update(){
        if(isAlive) {
            Log.d("Log.DEBUG", "isOutOfBoundsRight = " + isOutOfBoundsRight + " isOutOfBoundsLeft = " + isOutOfBoundsLeft);
            if (isOutOfBoundsRight || isOutOfBoundsLeft) {
                y = y + dy;
                dropping = true;
            } else if (horizDirection == RIGHT) {
                x = x + dx;
            } else if (horizDirection == LEFT) {
                x = x - dx;
            }
        }
    }

    boolean postUpdate() {
        Log.d("Log.DEBUG", "PostUpdate starts.");
        if (isOutOfBoundsRight){
            isOutOfBoundsRight = false;
            isOutOfBoundsLeft = false;
            horizDirection = LEFT;
        }else if(isOutOfBoundsLeft) {
            isOutOfBoundsLeft = false;
            isOutOfBoundsRight = false;
            horizDirection = RIGHT;
        }
        return DONE;
    }

    public Rect getRect(){
        return enemySpace;
    }
}
