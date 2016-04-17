package com.example.paolo.spaceinvaders;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

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
    int dx;
    int dy;

    int screen_X, screen_Y;

    Bitmap enemyicon;

    public Rect enemySpace =  new Rect();

    public Enemy(int offset, int screenX, int screenY, Bitmap icon, int enemyLength){
        isAlive = true;

        dx = 10;
        dy = 2 * radius;

        int columns = enemyLength/4;

        x = 220 + 100*(offset % columns);
        y = 220 + 100*(offset / columns);

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
                if (x < 0) {
                    isOutOfBoundsLeft = true;
                    return DONE;
                } else if (x > screen_X) {
                    isOutOfBoundsRight = true;
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

    void update(Player player){
        if(isAlive) {
            if (isOutOfBoundsRight || isOutOfBoundsLeft) {
                y = y + dy;
                dropping = true;
            } else if (horizDirection == RIGHT) {
                x = x + dx;
            } else if (horizDirection == LEFT) {
                x = x - dx;
            }
            if (y > player.position_y - player.radius){
                player.isAlive = false;
            }
        }
    }

    boolean postUpdate() {
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
