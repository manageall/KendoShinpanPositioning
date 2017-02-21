package com.allbudo.kendoshinpanpositioning;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import static java.lang.Math.PI;
import static java.lang.Math.cos;

/**
 * Created by Nils on 2017-01-16.
 */

abstract public class Referee extends ImageView{
    private int size = 80;
    protected float xPos; // Curent X-position
    protected float yPos; // Current Y-position
    public PlayerInterval playerInterval;

    /**
     * Constructor
     * @param context required by super
     */
    public Referee(Context context){
        super(context);
        init();
    }

    @Override
    public Drawable getDrawable() {
        return super.getDrawable();
    }

    protected void init(){
        setImage();
        setLook();
    }
    /**
     * Following members manipulates size and position
     */

    abstract public void setImage();

    abstract public void adjust();

    abstract public void setStartPos();

    private void setLook(){
        setAdjustViewBounds(true);
        setPadding(0,0,0,0);
        setLayoutParams( new FrameLayout.LayoutParams(size, size) );
        getLayoutParams().height = size;
        getLayoutParams().width = size;
        setBackgroundColor( Color.argb(0, 20, 70, 200 ) );
    }

    public void setPos(float x, float y){
        setX( x);
        setY( y);
    }

    public void setPlayerInterval(PlayerInterval p) {
        this.playerInterval = p;
    }

    protected void move(float toX, float toY){
        setPos(toX, toY);
       // TranslateAnimation ta = new TranslateAnimation( 0, toX , 0, toY );
        //ta.setDuration(500);
        //this.startAnimation(ta);
    }
}
