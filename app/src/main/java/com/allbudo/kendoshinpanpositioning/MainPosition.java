package com.allbudo.kendoshinpanpositioning;

import android.util.Log;

import static com.allbudo.kendoshinpanpositioning.ShiaiJo.refereeDistanceFromPlayer;
import static com.allbudo.kendoshinpanpositioning.ShiaiJo.refereeStartDistanceFromLine;
import static com.allbudo.kendoshinpanpositioning.ShiaiJo.shiaijo;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by Nils on 2017-01-16.
 */

public class MainPosition extends Position{

    public MainPosition(){}

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
        this.log();
    }

    public void log(){
        String s = "";
        s += "x = " + xPos + ", y = " + yPos + "\n";
        Log.d( this.getClass().toString(), s );
    }
}
