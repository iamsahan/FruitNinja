// GameView.java
package com.example.madgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {
    private static final String TAG = "GameView";

    private Thread gameThread;
    private SurfaceHolder holder;
    private boolean playing;
    private Paint paint;

    private Bitmap backgroundBitmap;
    private Bitmap fruitBitmap;
    private Bitmap bombBitmap;

    private List<Fruit> fruits;
    private List<Swipe> swipes;

    private int score;
    private int lives;
    private Random random;

    private long lastSpawnTime;
    private static final long SPAWN_INTERVAL = 1000; // 1 second

    public GameView(Context context) {
        super(context);
        holder = getHolder();
        paint = new Paint();
        fruits = new ArrayList<>();
        swipes = new ArrayList<>();
        score = 0;
        lives = 30;
        random = new Random();

        backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.playback);
        fruitBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.apple);
        bombBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.banana);

        lastSpawnTime = System.currentTimeMillis(); // Initialize the last spawn time

        Log.d(TAG, "GameView initialized");

        // Start the game loop
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        Log.d(TAG, "Game loop started");
        while (playing) {
            if (holder.getSurface().isValid()) {
                long startTime = System.nanoTime();

                update();
                draw();

                long endTime = System.nanoTime();
                long frameTime = endTime - startTime;
                if (frameTime < 16_666_667) { // ~60 FPS
                    try {
                        Thread.sleep((16_666_667 - frameTime) / 1_000_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Log.d(TAG, "Game loop stopped");
    }

    private void update() {
        Log.d(TAG, "Updating game state");

        // Spawn new fruit if enough time has passed
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastSpawnTime > SPAWN_INTERVAL) {
            spawnFruit();
            lastSpawnTime = currentTime;
        }

        // Update fruits and remove those that are off-screen
        Iterator<Fruit> iterator = fruits.iterator();
        while (iterator.hasNext()) {
            Fruit fruit = iterator.next();
            fruit.update();
            if (fruit.isOffScreen(getHeight())) {
                lives--;
                iterator.remove();
                if (lives <= 0) {
                    playing = false;
                }
            }
        }

        // Check for collisions
        for (Swipe swipe : swipes) {
            Iterator<Fruit> fruitIterator = fruits.iterator();
            while (fruitIterator.hasNext()) {
                Fruit fruit = fruitIterator.next();
                if (fruit.isSliced(swipe)) {
                    score++;
                    fruitIterator.remove();
                    break;
                }
            }
        }
    }

    private void draw() {
        Log.d(TAG, "Drawing frame");
        Canvas canvas = holder.lockCanvas();
        if (canvas != null) {
            canvas.drawBitmap(backgroundBitmap, 0, 0, paint);

            for (Fruit fruit : fruits) {
                fruit.draw(canvas, paint);
            }

            paint.setColor(Color.WHITE);
            paint.setTextSize(50);
            canvas.drawText("Score: " + score, 50, 100, paint);
            canvas.drawText("Lives: " + lives, 50, 150, paint);

            holder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            Swipe swipe = new Swipe(event.getX(), event.getY());
            swipes.add(swipe);
        }
        return true;
    }

    public void pause() {
        Log.d(TAG, "Game paused");
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        Log.d(TAG, "Game resumed");
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void spawnFruit() {
        Log.d(TAG, "Spawning fruit");
        float x = random.nextFloat() * getWidth();
        float y = 100;
        Fruit fruit = new Fruit(fruitBitmap, x, y);
        Fruit fruit1 = new Fruit(bombBitmap, x-y, y+x);
        fruits.add(fruit);
        fruits.add(fruit1);
    }
}
