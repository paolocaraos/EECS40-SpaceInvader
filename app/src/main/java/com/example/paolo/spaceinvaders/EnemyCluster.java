package com.example.paolo.spaceinvaders;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Paolo on 4/8/2016.
 */
public class EnemyCluster {

    int clusterSize;

    int screen_x,  screen_y;
    int dy;
    int dx;

    Bitmap[] enemyBitmaps;

    Paint enemyColor;

    public EnemyCluster(int border_x, int border_y, int unitAlienHeight, Bitmap[] enemyBitmaps){
        clusterSize = 20;
        dy = unitAlienHeight;
        screen_x = border_x;
        screen_y = border_y;
        this.enemyBitmaps = enemyBitmaps;
        dx = 10;
    }

    void draw(Canvas canvas){

    }


}
