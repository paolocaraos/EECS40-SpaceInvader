package com.example.paolo.spaceinvaders;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Paolo on 4/5/2016.
 */
public class Projectile {

    int screenUpperBound, screenLowerBound;

    public int trajectory;

    public final int bulletSpeed = 40;
    public boolean isActive;
    public final int bulletRadius = 20;

    int x, y;

    Paint ammoPaint;

    Rect bulletSpace;

    public Projectile(int UpperBound) {
        this.screenLowerBound = 0;
        this.screenUpperBound = UpperBound;
        isActive = false;

        ammoPaint = new Paint();
        ammoPaint.setColor(Color.WHITE);
    }

    void draw(Canvas canvas, int gunPoint_offset) {
        if(isActive) {
            bulletSpace = new Rect(x - bulletRadius, y - gunPoint_offset - bulletRadius, x + bulletRadius, y - gunPoint_offset + bulletRadius);
            canvas.drawRect(bulletSpace, ammoPaint);
        }
    }

    /*Return true if bullets hits*/
    boolean update(Enemy[] enemies){
        if(isActive){
            y = y + bulletSpeed * this.trajectory;
                if (y > screenUpperBound || y < screenLowerBound) {
                    isActive = false;
                } else {
                    for(int i = 0; i < enemies.length; i++) {
                        if(enemies[i].isAlive) {
                            if (bulletSpace.intersects(bulletSpace, enemies[i].getRect())) {
                                Log.d("Log.DEBUG", "Bullet " + "Hit Alien " + i);
                                isActive = false;
                                enemies[i].isAlive = false;
                                return true;
                            }
                        }
                }
            }
        }
        return false;
    }

    boolean update(Enemy enemy){
        if(isActive){
            y = y + bulletSpeed * this.trajectory;
            if (y > screenUpperBound || y < screenLowerBound) {
                isActive = false;
            } else {
                if(enemy.isAlive) {
                    if (bulletSpace.intersects(bulletSpace, enemy.getRect())) {
                        Log.d("Log.DEBUG", "Bullet " + "Hit Alien ");
                        isActive = false;
                        enemy.isAlive = false;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    void shoot(int shooter_x, int shooter_y, int traj){
        isActive = true;
        x = shooter_x;
        y = shooter_y;

        trajectory = traj;
    }

}

