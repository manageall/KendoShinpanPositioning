package com.allbudo.kendoshinpanpositioning;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

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
        this.setLook();
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

                float interval = this.playerInterval.getLength();

                if(interval < this.size){
                    if( (this.playerInterval.xMax == this && dX < 0) || (this.playerInterval.xMax != this && dX > 0) ){
                        //Do not allow x move
                        dX = 0;
                    }
                    if( (this.playerInterval.yMax == this && dY < 0) || (this.playerInterval.yMax != this && dY > 0)){
                        //Do not allow y move
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

    private int pixelsToDp(int pixels){
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        float fpixels = metrics.density * pixels;
        int dp = (int) (fpixels + 0.5f); // 0.5f rounds up to prevent 0;
        return dp;
    }

    /**
     * Limits player to move only within shiaijo
     */
    public void setBounds(View v){
        xMin = v.getX();
        yMin = v.getY();
        yMax = yMin + v.getMeasuredHeight() - size;
        xMax = xMin + v.getMeasuredWidth() - size;
    }

    public void setStartPos(View v){
        float x = v.getX();
        float y = v.getY();
        float w = v.getMeasuredWidth();
        float h = v.getMeasuredHeight();
        float cx = x + w/2;
        float cy = y + h/2;

        switch(color){
            case "red":
                xPos = cx - ( size / 2 ) - 200;
                yPos = cy - (size / 2 );
                break;
            case "white":
                xPos = cx - ( size / 2 ) + 200;
                yPos = cy - (size / 2 );
                break;
            default:
                //If something fails, pop them in the center
                xPos = cx;
                yPos =  cy;
        }
        setPos( xPos, yPos );
    }

    private void setLook(){
        this.setAdjustViewBounds(true);
        this.setPadding(0,0,0,0);
        this.setLayoutParams( new FrameLayout.LayoutParams(size, size) );
        this.getLayoutParams().height = size;
        this.getLayoutParams().width = size;
        this.setBackgroundColor( Color.argb(0, 20, 70, 200 ) );
    }

    public void setPos( float x, float y ){
        this.setX( x );
        this.setY( y );
    }

    public float[] getPos(){
        return new float[]{this.xPos, this.yPos};
    }
}