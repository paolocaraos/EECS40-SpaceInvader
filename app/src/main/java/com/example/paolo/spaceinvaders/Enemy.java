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
    final int radius = 30;

    static boolean isOutOfBounds;
    static int horizDirection;

    int x, start_x;
    int y, start_y;
    int dx = 100;
    int dy;

    int screen_X, screen_Y;

    Bitmap enemyicon;

    public Enemy(int offset, int screenX, int screenY, Bitmap icon){
        x = 220 + 100*(offset % 5);
        y = 220 + 100*(offset / 5);

        screen_X = screenX;
        screen_Y = screenY;

        isOutOfBounds = false;
        horizDirection = RIGHT;

        enemyicon = icon;

    }

    void draw(Canvas canvas){
        Rect enemySpace = new Rect();

        enemySpace.set(x - radius, y - radius, x + radius, y + radius);
        canvas.drawBitmap(enemyicon, null, enemySpace, null);
    }

    void update(){
        if(horizDirection == RIGHT){
            x = x + dx;
            if(x > screen_X){
                x = screen_X;
                isOutOfBounds = true;
                horizDirection = LEFT;
            }
        }
        if(horizDirection == LEFT){
            x = x - dx;
            if(x < 0){
                x = 0;
                isOutOfBounds = true;
                horizDirection = RIGHT;
            }
        }
    }

}
