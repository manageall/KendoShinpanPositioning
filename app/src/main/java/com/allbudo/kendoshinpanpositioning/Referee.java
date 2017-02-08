package com.allbudo.kendoshinpanpositioning;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import static java.lang.Math.PI;
import static java.lang.Math.cos;

/**
 * Created by Nils on 2017-01-16.
 */

abstract public class Referee extends ImageView{
    private int size = 300;
    protected float xPos; // Curent X-position
    protected float yPos; // Current Y-position
    public PlayerInterval playerInterval;
    private Drawable d;


    /**
     * Constructor
     * @param context required by super
     */
    public Referee(Context context){
        super(context);
        this.init();
    }

    @Override
    public Drawable getDrawable() {
        return super.getDrawable();
    }


    protected void init(){
        this.setImageResource(R.drawable.shinpan);
        this.d = getDrawable();
    }
    /**
     * Following members manipulates size and position
     */

    abstract public void adjust();

    abstract public void setStartPos();

    private void setSize( int width, int height ){
        this.setMaxWidth( width );
        this.setMinimumWidth( width );
        this.setMaxHeight( height );
        this.setMinimumHeight( height );
        this.setLayoutParams( new FrameLayout.LayoutParams( width, height ) );
        this.setBackgroundColor( Color.rgb( 20, 70, 200 ) );
    }

    public void setPos( float[] pos ){
        this.setX( pos[0] );
        this.setY( pos[1] );
    }

    public void setPos(float x, float y){
        this.setX( x );
        this.setY( y );
    }

    public float[] getPos(){
        return new float[]{this.xPos, this.yPos};
    }

    public float[] getPosCentered(){
        return new float[]{this.xPos + size / 2, this.yPos + size / 2};
    }

    public void setPlayerInterval(PlayerInterval p) {
        this.playerInterval = p;
    }
}
