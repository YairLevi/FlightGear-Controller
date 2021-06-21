package com.example.myapplication22.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.myapplication22.R;

import java.sql.SQLOutput;

public class JoystickView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {
    private float centerX;
    private float centerY;
    private float baseRadius;
    private float hatRadius;
    private JoystickListener joystickCallback;
    public JoystickView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if (context instanceof JoystickListener) {
            joystickCallback = (JoystickListener)context;
        }
    }
    public JoystickView (Context context, AttributeSet attributes, int style) {
        super(context,attributes, style);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if (context instanceof JoystickListener) {
            joystickCallback = (JoystickListener)context;
        }
    }
    public JoystickView(Context context, AttributeSet attributes) {
        super(context, attributes);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if (context instanceof JoystickListener) {
            joystickCallback = (JoystickListener)context;
        }
    }
    public boolean onTouch(View v, MotionEvent e) { // On touching
        if (v.equals(this)) { // on this view
            if(e.getAction() != e.ACTION_UP) { // As long as the user touching the screen
                float displacement = (float) Math.sqrt((Math.pow(e.getX() - centerX, 2)) + Math.pow(e.getY() - centerY,2));
                if (displacement < baseRadius) {
                    drawJoystick(e.getX(), e.getY());
                    joystickCallback.onJoystickMoved((e.getX() - centerX) / baseRadius, (e.getY() - centerY)/baseRadius, getId());
                    //System.out.println(e.getX() + "," + e.getY());
                }
                else {
                    float ratio = baseRadius / displacement;
                    float constrainedX = centerX + (e.getX() - centerX) * ratio;
                    float constrainedY = centerY + (e.getY() - centerY) * ratio;
                    drawJoystick(constrainedX, constrainedY);
                    joystickCallback.onJoystickMoved((constrainedX-centerX)/baseRadius, (constrainedY-centerY)/baseRadius, getId());
                }
                // if (Math.sqrt((e.getX() * e.getX()) + (e.getY() * e.getY())) <= )
            }
            else {
                drawJoystick(centerX, centerY);
                joystickCallback.onJoystickMoved(0,0,getId());
            }
        }
        return true;
    }

    public interface JoystickListener {
        void onJoystickMoved(float xPercent, float yPercent, int id); // we can put anything here - placement of where we touch the screen, or what ever we want
    }
    private void setupDimensions() {
        centerX = (float)getWidth() / 2;
        centerY = (float)getHeight() / 2;
        baseRadius = (float)Math.min(getWidth(), getHeight()) / 3;
        hatRadius = (float)Math.min(getWidth(), getHeight()) / 5;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setupDimensions(); // ?
        drawJoystick(centerX, centerY);
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
    private void drawJoystick(float newX, float newY) {
        if (getHolder().getSurface().isValid()) {
            Canvas myCanvas = this.getHolder().lockCanvas();
            Paint colors = new Paint();
            myCanvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
            colors.setARGB(255,250,50,50); // Color of base of joystick
            myCanvas.drawCircle(centerX, centerY, baseRadius, colors);
            colors.setARGB(255,0,0,255); // Color of the cap [joystick itself]
            myCanvas.drawCircle(newX, newY, hatRadius, colors);
            getHolder().unlockCanvasAndPost(myCanvas);
        }
    }




}

