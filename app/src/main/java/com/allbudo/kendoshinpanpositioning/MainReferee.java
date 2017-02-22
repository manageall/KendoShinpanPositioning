package com.allbudo.kendoshinpanpositioning;

import android.content.Context;
import android.util.Log;

import static com.allbudo.kendoshinpanpositioning.ShiaiJo.*;
import static java.lang.Math.*;

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
        float[] mainPos = playerInterval.mainPosition.getPos();
        float dx = abs(myCenter[0]-mainPos[0]);
        float dy = abs(myCenter[1]-mainPos[1]);
        double a = pow( (double) dx, 2 ) + pow( (double) dy, 2 );
        float distanceToPos = (float)sqrt( a );
        //Log.d("ref distance to pos", Float.toString(distanceToPos));
        if(distanceToPos > 300 || true){
            move(mainPos[0], mainPos[1]);
        }else{
            return;
        }

        float myAngle = playerInterval.getAngle() + (float) PI/2; // Radians
        float py = -refereeDistanceFromPlayer * (float) sin( (double) myAngle);
        float px = -refereeDistanceFromPlayer * (float) cos( (double) myAngle);
        float newX = myCenter[0] + px;
        float newY = myCenter[1] + py;
        this.setPos(newX, newY);
        // Log.d("MainRef x", ""+newX);
        // Log.d("MainRef y", ""+newY);
    }
    public void setImage(){
        this.setImageResource(R.drawable.shinpan);
    }

    public void setPlayerInterval(PlayerInterval p){
        super.setPlayerInterval(p);
        this.myPos = playerInterval.mainPosition;
    }
}
