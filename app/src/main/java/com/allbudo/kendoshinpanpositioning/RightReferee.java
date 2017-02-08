package com.allbudo.kendoshinpanpositioning;

import android.content.Context;
import android.view.View;

import static com.allbudo.kendoshinpanpositioning.ShiaiJo.*;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by Nils on 2017-01-16.
 */

public class RightReferee extends Referee{
    float startX = 700;
    float startY = 1000;

    public RightReferee(Context context){
        super(context);
        super.init();
    }

    public void setStartPos(){
        xPos = shiaijo.get("xmax") - 200;
        yPos = shiaijo.get("ymax") - refereeStartDistanceFromLine;
        setPos( xPos, yPos );
    }

    public void adjust(){
        float[] p = playerInterval.getOffsetPoint(1, assistantRefereeOffset); // p = point on interval i*offset from Intervals center
        float a = playerInterval.getAngle() + (float) PI / 2; // a = angle of interval in Radians
        float dy = refereeDistanceFromPlayer * (float) sin( (double) a );
        float dx = refereeDistanceFromPlayer * (float) cos( (double) a );
        float newX = p[0] + dx;
        float newY = p[1] + dy;
        this.setPos( newX, newY );
    }
}