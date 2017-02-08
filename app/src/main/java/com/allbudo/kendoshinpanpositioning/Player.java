package com.allbudo.kendoshinpanpositioning;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by Nils on 2017-01-16.
 */

public class Player extends ImageView{
    private int size = 80;
    private String color;
    private float xPos; // Curent X-position
    private float yPos; // Current Y-position
    private float touchDownX; // Mouse/Touch down X-position
    private float touchDownY; // Mouse/Touch down Y-position
    private float dX;
    private float dY;
    private float xMin; // Boundary
    private float yMin; // Boundary
    private float xMax; // Boundary
    private float yMax; // Boundary
    private PlayerInterval playerInterval;
    private Drawable d;

    @Override
    public Drawable getDrawable() {
        return super.getDrawable();
    }

    /**
     * Constructor
     * @param context required by super
     * @param color String (must be either red or white)
     */
    public Player(Context context, String color){
        super(context);
        this.init(color);
    }

    private void init(String color){
        this.color = color;
        switch(color){
            case "red":
                this.setImageResource(R.drawable.red);
                break;
            case "white":
                this.setImageResource(R.drawable.white);
                break;
        }
        this.setSize(this.size, this.size);
        this.d = getDrawable();
    }

    @Override
    public boolean onTouchEvent(MotionEvent myEvent) {
        int myAction = myEvent.getAction();
        switch(myAction){
            case 0: // Touch/Mouse Down
                touchDownX = myEvent.getX(); // Coordinates within the image
                touchDownY = myEvent.getY();
                break;
            case 1: // Touch/Mouse Up
                break;
            case 2: // Touch/Mouse Move
                dX = myEvent.getX() - touchDownX;
                dY = myEvent.getY() - touchDownY;

                // Try testing raw touch position for crash with the other player
                // If crash the break
                float interval = this.playerInterval.getLength();
                //Log.d("Interval", Float.toString(interval));

                if(interval < this.size){
                    if( (this.playerInterval.xMax == this && dX < 0) || (this.playerInterval.xMax != this && dX > 0) ){
                        //Do not allow x move
                        //Log.d("X move","forbidden");
                        dX = 0;
                    }
                    if( (this.playerInterval.yMax == this && dY < 0) || (this.playerInterval.yMax != this && dY > 0)){
                        //Do not allow y move
                        //Log.d("Y move","forbidden");
                        dY = 0;
                    }
                } else {
                    //Nothing
                }

                // Calculate new position
                xPos += dX;
                yPos += dY;

                // Verify that pointer is within bounds
                if(xPos <= xMin){
                    xPos = xMin;
                }
                if(xPos >= xMax){
                    xPos = xMax;
                }
                if(yPos <= yMin){
                    yPos = yMin;
                }
                if(yPos >= yMax){
                    yPos = yMax;
                }
                this.setPos(xPos, yPos);
                break;
        }
        return true;
    }

    public String getColor(){
        return this.color;
    }


    /**
     * Following members manipulates size and position
     */
    public void setPlayerInterval(PlayerInterval p) {
        this.playerInterval = p;
    }

    public void setBounds(View v){
        xMin = v.getX();
        yMin = v.getY();
        yMax = yMin + v.getMeasuredHeight() - size;
        xMax = xMin + v.getMeasuredWidth() - size;
    }

    public void setStartPos(){
        switch(color){
            case "red":
                xPos = xMin - size / 2;
                yPos = yMin + ( yMax - yMin - size ) / 2;
                break;
            case "white":
                xPos = xMax - size / 2;
                yPos = yMin + ( yMax - yMin - size ) / 2;
                break;
            default:
                xPos = (xMin + xMax) / 2;
                yPos = (yMin + yMax) / 2;
        }
        setPos( xPos, yPos );
    }

    private void setSize( int width, int height ){
        this.setScaleType(ScaleType.CENTER);
        this.setLayoutParams( new FrameLayout.LayoutParams( this.size, this.size) );
        this.requestLayout();
        this.getLayoutParams().height = this.size;
        this.setBackgroundColor( Color.argb(10, 20, 70, 200 ) );
    }


    public void setPos( float x, float y ){
        //setXPos( x );
        this.setX( x );
        //setYPos( y );
        this.setY( y );
    }

    public void setXPos( float x ){
        xPos = x;
        this.setX( x );
    }

    public void setYPos( float y ){
        yPos = y;
        this.setY( y );
    }

    public float[] getPos(){
        return new float[]{this.xPos, this.yPos};
    }
}
