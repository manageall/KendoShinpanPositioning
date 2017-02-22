package com.allbudo.kendoshinpanpositioning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import java.util.HashMap;
import java.util.Map;

public class ShiaiJo extends AppCompatActivity {
    public static float assistantRefereeOffset = 200;
    public static float refereeDistanceFromPlayer = 300;
    public static float refereeStartDistanceFromLine = 200;
    public static View shiaijoView;
    public static Map<String, Float> shiaijo = new HashMap<String, Float>();
    private boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shiai_jo);
        setTitle("Hajime!");
    }

    /*
    Needed as some views are not calculated until shown so values cannot be obtained until then
     */
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        /*
        Define screen layout
         */
        RelativeLayout myScreenLayout = (RelativeLayout) findViewById(R.id.activity_shiai_jo);
        shiaijoView = findViewById( R.id.shiaijo_id );

        shiaijo.put("xmin", shiaijoView.getX());
        shiaijo.put("ymin", shiaijoView.getY());
        shiaijo.put("width",(float) shiaijoView.getMeasuredWidth());
        shiaijo.put("height",(float) shiaijoView.getMeasuredHeight());
        shiaijo.put("xmax", shiaijo.get("xmin") + shiaijo.get("width"));
        shiaijo.put("ymax", shiaijo.get("ymin") + shiaijo.get("height"));
        shiaijo.put("cx", shiaijo.get("xmin") + shiaijo.get("width"));
        shiaijo.put("cy", shiaijo.get("ymin") + shiaijo.get("height"));

        //Prevent cast duplication;
        if(started){
            return;
        }

        /*
        Instantiate cast
         */
        Player playerRed   = new Player( this, "red" );
        Player playerWhite = new Player( this, "white" );
        MainReferee mainReferee     = new MainReferee( this ); // Referee
        MainPosition mainPosition   = new MainPosition();
        LeftReferee leftReferee     = new LeftReferee( this ); // Referee
        RightReferee rightReferee   = new RightReferee( this ); // Referee

        /*
        Add cast to Screen layout
         */
        myScreenLayout.addView( playerRed );
        myScreenLayout.addView( playerWhite );
        myScreenLayout.addView( mainReferee );
        myScreenLayout.addView( leftReferee );
        myScreenLayout.addView( rightReferee );

        /*
        Introduce playerinterval and relationships
         */
        PlayerInterval playerInterval = new PlayerInterval();
        playerInterval.setPlayer(playerRed);
        playerInterval.setPlayer(playerWhite);
        playerInterval.setMainReferee(mainReferee);
        playerInterval.setMainPosition(mainPosition);
        playerInterval.setLeftReferee(leftReferee);
        playerInterval.setRightReferee(rightReferee);

        /*
        Set bounds to players based on shiaijoView size/pos
         */
        playerRed.setBounds(shiaijoView);
        playerWhite.setBounds(shiaijoView);

        /*
        Set starting positions for cast
        */
        playerRed.setStartPos(shiaijoView);
        playerWhite.setStartPos(shiaijoView);
        mainReferee.setStartPos();
        mainPosition.setStartPos();
        leftReferee.setStartPos();
        rightReferee.setStartPos();
        playerInterval.update();

        started = true;
    }
}