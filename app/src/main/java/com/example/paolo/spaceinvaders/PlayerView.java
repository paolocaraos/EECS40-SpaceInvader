package com.example.paolo.spaceinvaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

/**
 * Created by Paolo on 4/5/2016.
 */
public class PlayerView extends SurfaceView implements SurfaceHolder.Callback{

    Context context;

    private int screen_width;
    private int screen_height;
    private int moveControl_upperBound;

    private GameThread playerThread;

    private Projectile[] playerBullet = new Projectile[30];
    private Projectile[] enemyBullet  = new Projectile[100];
    public final int DOWN = 1;
    public final int UP = -1;

    private Player player;
    private Enemy[] enemy = new Enemy[20];
    private Enemy motherShip;
    private Bitmap motherIcon;
    private Bitmap playericon;
    private Bitmap[] enemyicon = new Bitmap[20];

    Canvas canvas;

    private volatile boolean stillPlaying;

    public Rect[] enemyRect = new Rect[20];

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
        for(int i = 0; i < playerBullet.length; i++) {
            playerBullet[i].draw(canvas, player.radius);
        }
        //Check enemy states
        for(int i = 0; i < enemy.length; i++){
            if(enemy[i].checkNextState())
                break;
        }//Draw
        for(int i = 0; i < enemy.length; i++){
            enemy[i].draw(canvas);
        }
        motherShip.draw(canvas);

        //Update all elements
        player.update();
        for(int i = 0; i < playerBullet.length; i++) {
            if(playerBullet[i].update(enemy)){
                player.killScore += 1;
            }
            playerBullet[i].update(motherShip);
        }
        for(int i = 0; i < enemy.length; i++){
            enemy[i].update(player);
        }
        motherShip.update();
        for(int i = 0; i < enemy.length; i++){
            if(enemy[i].postUpdate()){
                break;
            }
        }

        if(motherShip.isAlive == false){
            Random r = new Random();
            int motherSpawnScore = r.nextInt(500 - 0) + 0;
            Log.d("Log.DEBUG", "Spawn score = " + motherSpawnScore);
            if(motherSpawnScore == 15)
                motherShip.spawn();
        }
        if(player.killScore == enemy.length){
            player.killScore = 0;
            enemy[0].waveNumber++;
            for(int i = 0; i < enemy.length; i++){
                enemy[i].spawn(i, enemy.length);
            }
        }

        if(player.isAlive == false){
            Paint gameOverPaint = new Paint();
            canvas.drawPaint(gameOverPaint);
            gameOverPaint.setColor(Color.WHITE);
            gameOverPaint.setTextSize(180);
            canvas.drawText("Game Over", canvas.getWidth() / 8 - 50, (canvas.getHeight() / 2) - ((gameOverPaint.descent()+ gameOverPaint.ascent())/2), gameOverPaint );
        }
    }



    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        //Launch emulator thread
        this.screen_width = getWidth();
        this.screen_height = getHeight();
        this.moveControl_upperBound = (this.screen_height) / 3;

        playericon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        motherIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        for(int i = 0; i < enemy.length; i++){
            enemyicon[i] = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            enemy[i] = new Enemy(i, this.screen_width, this.screen_height, enemyicon[i], enemy.length);
        }
        player = new Player(this.screen_width, this.screen_height, playericon);
        motherShip = new Enemy(motherIcon, this.screen_width, this.screen_height);
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
        playerThread.interrupt();
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
