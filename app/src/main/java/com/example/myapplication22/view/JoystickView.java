package com.example.myapplication22.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import com.example.myapplication22.R;

public class JoystickView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {
    private float centerX;
    private float centerY;
    private float baseRadius;
    private float hatRadius;
    private JoystickListener joystickCallback;
    private AppCompatImageView base;
    private AppCompatImageView controller;
    private boolean firstTouch = true;

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

    public void addImages(AppCompatImageView base, AppCompatImageView controller) {
        this.base = base;
        this.controller = controller;
    }


    public boolean onTouch(View v, MotionEvent e) { // On touching
        if (v.equals(this)) { // on this view
            if(e.getAction() != e.ACTION_UP) { // As long as the user touching the screen
                float displacement = (float) Math.sqrt((Math.pow(e.getX() - centerX, 2)) + Math.pow(e.getY() - centerY,2));
                if (displacement < baseRadius) {
                    float xPercent = (e.getX() - centerX) / baseRadius;
                    float yPercent = (e.getY() - centerY) / baseRadius;
                    drawJoystick(xPercent, yPercent);
                    joystickCallback.onJoystickMoved(xPercent, yPercent, getId());
                    //System.out.println(e.getX() + "," + e.getY());
                }
                else {
                    float ratio = baseRadius / displacement;
                    float constrainedX = centerX + (e.getX() - centerX) * ratio;
                    float constrainedY = centerY + (e.getY() - centerY) * ratio;
                    float xPercent = (constrainedX-centerX)/baseRadius;
                    float yPercent = (constrainedY-centerY)/baseRadius;
                    drawJoystick(xPercent, yPercent);
                    joystickCallback.onJoystickMoved(xPercent, yPercent, getId());
                }
                // if (Math.sqrt((e.getX() * e.getX()) + (e.getY() * e.getY())) <= )
            }
            else {
                firstTouch = true;
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
        //drawJoystick(centerX, centerY);
        int bias = 90;
        Canvas myCanvas = this.getHolder().lockCanvas();
        base.setImageResource(R.drawable.base);
        base.requestLayout();
        base.getLayoutParams().height = (int) (2 * baseRadius + bias);
        base.getLayoutParams().width = (int) (2 * baseRadius + bias);
        base.draw(myCanvas);
        controller.setImageResource(R.drawable.controller);
        controller.requestLayout();
        controller.getLayoutParams().height = (int) (2 * hatRadius);
        controller.getLayoutParams().width = (int) (2 * hatRadius);
        controller.draw(myCanvas);
        getHolder().unlockCanvasAndPost(myCanvas);
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
    private void drawJoystick(float xPercent, float yPercent) {
        if (getHolder().getSurface().isValid()) {
            int bias = 90;
            Canvas myCanvas = this.getHolder().lockCanvas();
            float x = base.getX();
            float y = base.getY();
            controller.setImageResource(R.drawable.controller);
            controller.requestLayout();
            controller.getLayoutParams().height = (int) (2 * hatRadius);
            controller.getLayoutParams().width = (int) (2 * hatRadius);
            controller.setX(x + baseRadius - hatRadius + (int)(bias/2));
            controller.setY(y + baseRadius - hatRadius + (int)(bias/2));
            if (!firstTouch) {
                controller.setX(x + baseRadius - hatRadius + (int)(bias/2) + xPercent*baseRadius);
                controller.setY(y + baseRadius - hatRadius + (int)(bias/2) + yPercent*baseRadius);
            }
            controller.draw(myCanvas);
            firstTouch = false;
            getHolder().unlockCanvasAndPost(myCanvas);
        }
    }




}

