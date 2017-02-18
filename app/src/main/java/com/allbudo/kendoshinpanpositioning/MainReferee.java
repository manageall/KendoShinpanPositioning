package com.allbudo.kendoshinpanpositioning;

import android.content.Context;
import android.util.Log;
import android.view.View;

import static com.allbudo.kendoshinpanpositioning.ShiaiJo.*;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by Nils on 2017-01-16.
 *
 */

public class MainReferee extends Referee{

    /**
     * Constructor
     * @param context required by super
     */
    public MainReferee(Context context){
        super(context);
        super.init();
    }

    public void setStartPos(){
        xPos = shiaijo.get("xmin") + shiaijo.get("width") / 2;
        yPos = shiaijo.get("ymin") + refereeStartDistanceFromLine;
        setPos( xPos, yPos );
    }

    public void adjust(){
        float[] myCenter = playerInterval.getCenter();
        float myAngle = playerInterval.getAngle() + (float) PI/2; // Radians
        float py = -refereeDistanceFromPlayer * (float) sin( (double) myAngle);
        float px = -refereeDistanceFromPlayer * (float) cos( (double) myAngle);
        float newX = myCenter[0] + px;
        float newY = myCenter[1] + py;
        this.setPos(newX, newY);
    }
    public void setImage(){
        this.setImageResource(R.drawable.shinpan);
    }
}
