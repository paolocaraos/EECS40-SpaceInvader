package com.example.paolo.spaceinvaders;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by Paolo on 4/5/2016.
 */
public class Projectile {

    int screenUpperBound, screenLowerBound;

    public int trajectory;

    public final int bulletSpeed = 100;
    public boolean isActive;
    public final int bulletRadius = 15;

    int x, y;

    Paint ammoPaint;

    public Projectile(int UpperBound) {
        this.screenLowerBound = 0;
        this.screenUpperBound = UpperBound;
        isActive = false;

        ammoPaint = new Paint();
        ammoPaint.setColor(Color.WHITE);
    }

    void draw(Canvas canvas, int gunPoint_offset) {
        if(isActive)
            canvas.drawCircle(x, y - gunPoint_offset, bulletRadius, ammoPaint);
    }

    void update(){
        if(isActive){
            y = y + bulletSpeed * this.trajectory;

            Log.d("Log.DEBUG", "screenLower = " + screenLowerBound + "bullet y position = " + y);

            if(y > screenUpperBound || y < screenLowerBound)
                isActive = false;
        }
    }

    void shoot(int shooter_x, int shooter_y, int traj){
        isActive = true;
        x = shooter_x;
        y = shooter_y;

        trajectory = traj;
    }

}

