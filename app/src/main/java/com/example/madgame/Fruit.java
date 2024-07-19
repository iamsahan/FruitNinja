package com.example.madgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class Fruit {
    private Bitmap bitmap;
    private float x, y;
    private float speedY;

    public Fruit(Bitmap bitmap, float x, float y) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.speedY = -15;
    }

    public void update() {
        y += speedY;
        speedY += 1;
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bitmap, x, y, paint);
    }

    public boolean isOffScreen(int screenHeight) {
       return y > screenHeight;
    }

    public boolean isSliced(Swipe swipe) {
        RectF fruitRect = new RectF(x, y, x + bitmap.getWidth(), y + bitmap.getHeight());
        return fruitRect.contains(swipe.getX(), swipe.getY());
    }

}
