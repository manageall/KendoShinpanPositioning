package com.allbudo.kendoshinpanpositioning;

import android.util.Log;
import static java.lang.Math.abs;
import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

/**
 * Created by Nils on 2017-01-23.
 */

public class PlayerInterval {
    private float length;
    public  Player xMax;
    public  Player yMax;
    public  Player xMin;
    public  Player yMin;
    public Player playerRed;
    public Player playerWhite;
    private MainReferee mainReferee;
    public MainPosition mainPosition;
    private LeftReferee leftReferee;
    private RightReferee rightReferee;

    public float getLength(){
        this.update();
        return this.length;
    }

    //This method updates EVERYTHING!
    public void update(){
        calculateLength();
        mainReferee.adjust();
        mainPosition.adjust();
        leftReferee.adjust();
        rightReferee.adjust();
    }

    /*
     * Calculates length of the interval between the players
     * Also identifies whom of the two is top left, bottom right etc
     */
    private void calculateLength(){
        float dx = playerRed.getX() - playerWhite.getX();
        float dy = playerRed.getY() - playerWhite.getY();
        if(dx > 0){ // redX > whiteX i.e. red is to the right of white
            xMax = playerRed;
            xMin = playerWhite;
        } else {
            xMax = playerWhite;
            xMin = playerRed;
        }
        if(dy > 0){ // redY > whiteY i.e. red is below white
            yMax = playerRed;
            yMin = playerWhite;
        } else {
            yMax = playerWhite;
            yMin = playerRed;
        }
        dx = abs(dx);
        dy = abs(dy);
        double a = pow( (double) dx, 2 ) + pow( (double) dy, 2 );
        this.length = (float)sqrt( a );
    }

    public void setPlayer(Player p){
        switch(p.getColor()){
            case "red":
                playerRed = p;
                break;
            case "white":
                playerWhite = p;
                break;
        }
        p.setPlayerInterval(this); // make player "aware" of interval
    }

    public void setMainReferee(MainReferee r){
        mainReferee = r;
        r.setPlayerInterval(this);
    }
    public void setMainPosition(MainPosition p){
        mainPosition = p;
        mainPosition.setPlayerInterval(this);
    }
    public void setLeftReferee(LeftReferee r){
        leftReferee = r;
        r.setPlayerInterval(this);
    }

    public void setRightReferee(RightReferee r){
        rightReferee = r;
        r.setPlayerInterval(this);
    }

    public float[] getCenter(){
        float[] redPos = playerRed.getPos();
        float[] whitePos = playerWhite.getPos();
        float xAvg = (redPos[0] + whitePos[0]) / 2;
        float yAvg = (redPos[1] + whitePos[1]) / 2;
        return new float[]{xAvg, yAvg};
    }


    public float getAngle(){
        // Angle relative to horizon
        // default = 0
        // positive = high left to low right = clockwise turn
        // negative = high right to low left = counter clockwise turn
        // ABS max = 90

        float angle;
        float height = xMax.getY() - xMin.getY();
        float width = xMax.getX() - xMin.getX();

        angle = (float) atan(height/width);

        return angle;
    }

    /**
     * @param i either pos or neg int != 0 defines which side of origo on the interval
     * @param l distance from origo on the interval
     * @return point on the interval on l distance from center
     */
    public float[] getOffsetPoint(int i, float l){
        float[] p = new float[2]; //Array to be returned representing a point on the interval
        float[] origo = getCenter();
        float ox = origo[0];
        float oy = origo[1];
        float a = getAngle();
        float dx = (float) cos(a) * l;
        float dy = (float) sin(a) * l;
        p[0] = ox + dx * i;
        p[1] = oy + dy * i;
        return p;
    }
}