package com.example.paolo.spaceinvaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Paolo on 4/5/2016.
 */
public class PlayerView extends SurfaceView implements SurfaceHolder.Callback{

    Context context;

    private int screen_width;
    private int screen_height;
    private int moveControl_upperBound;

    private GameThread gThread;
    private GameThread playerThread;
    private GameThread inputThread;

    private Projectile[] playerBullet = new Projectile[10];
    private Projectile[] enemyBullet;
    public final int DOWN = 1;
    public final int UP = -1;

    private Player player;
    private Bitmap playericon;

    Canvas canvas;

    private volatile boolean stillPlaying;

    public PlayerView(Context context){

        super(context);
        getHolder().addCallback(this);
        setFocusable(true);

        //Initialize game state variables
        canvas = new Canvas();
    }

    public void draw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);

        player.draw(canvas);
        player.update();

        for(int i = 0; i < playerBullet.length; i++) {
            playerBullet[i].draw(canvas, player.radius);
            playerBullet[i].update();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        //Launch emulator thread
        this.screen_width = getWidth();
        this.screen_height = getHeight();
        this.moveControl_upperBound = (this.screen_height) / 3;

        playericon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        player = new Player(this.screen_width, this.screen_height, playericon);
        playerThread = new GameThread(this);
        playerThread.start();

        for(int i = 0; i < playerBullet.length; i++)
            playerBullet[i] = new Projectile(this.screen_height);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder,
                               int format, int width, int height){
        // Respond to surface changes
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        gThread.interrupt();
    }


    @Override
    public boolean onTouchEvent(MotionEvent moveEvent){

        float touch_X = moveEvent.getX();
        float touch_Y = moveEvent.getY();

        Log.d("Log.DEBUG", "moveControl_upperBound = " + moveControl_upperBound + " touch_Y = " + touch_Y);

        switch(moveEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(touch_Y > moveControl_upperBound ) {
                    if (touch_X > screen_width / 2) {
                        player.moveDirection = player.RIGHT;
                    } else {
                        player.moveDirection = player.LEFT;
                    }
                }
                else{/*touch upper third of the screen to shoot*/
                    for(int i = 0; i < playerBullet.length; i++) {
                        if (playerBullet[i].isActive == false) {
                            playerBullet[i].shoot(player.position_x, player.position_y, UP);
                            break;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                player.moveDirection = player.STOP;
                break;
        }

        return true;
    }

}
