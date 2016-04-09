package com.example.paolo.spaceinvaders;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Paolo on 4/5/2016.
 */
public class Player {

    int screenBound_x, screenBound_y;

    final int RIGHT = 1;
    final int LEFT  = -1;
    final int STOP = 3;

    public int moveDirection;
    public int position_x;
    public final int position_y = 1450;
    public final int radius = 50;

    private final int dx;

    Bitmap playericon;

    public Player(int screenX, int screenY, Bitmap icon) {
        position_x = 600;
        dx = 50;
        playericon = icon;

        moveDirection = 0;

        this.screenBound_x = screenX;
        this.screenBound_y = screenY;

        Log.d("Log.DEBUG", "width=" + screenX + "height=" + screenY);
    }

    void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);

        Rect coordinates = new Rect();
        coordinates.set(position_x - radius, position_y - radius, position_x + radius, position_y + radius);
        canvas.drawCircle(position_x, position_y, radius, paint);
        canvas.drawBitmap(playericon,null,coordinates,null);
    }

    void update(){
        if(moveDirection == LEFT){
            position_x = position_x - dx;
            if(position_x < 0)
                position_x = 0;
        }
        if(moveDirection == RIGHT){
            position_x = position_x + dx;
            if(position_x > this.screenBound_x) {
                Log.d("Log.DEBUG", "screenBound_x = " + this.screenBound_x + "position_x = " + position_x);
                position_x = this.screenBound_x;
            }
        }
    }
}
