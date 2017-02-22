package com.allbudo.kendoshinpanpositioning;

/**
 * Created by Nils on 2017-01-16.
 */

abstract public class Position{
    public float xPos; // Curent X-position
    public float yPos; // Current Y-position
    public PlayerInterval playerInterval;

    public Position(){
    }

    abstract public void adjust();

    abstract public void setStartPos();

    public void setPos( float x, float y ){
        xPos = x;
        yPos = y;
    }

    public void setPos( float[] pos ){
        setPos (pos[0], pos[1]);
    }

    public float[] getPos(){
        return new float[]{ xPos, yPos };
    }

    public void setPlayerInterval(PlayerInterval p) {
        this.playerInterval = p;
    }
}
