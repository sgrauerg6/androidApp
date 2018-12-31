package com.mathprog.sgrauerg.monochromemath;

import android.util.Log;
import android.widget.TextView;

import java.lang.Runnable;
import java.lang.InterruptedException;
import java.util.Calendar;

/**
 * Created by sgrauerg on 11/6/15.
 */
public class GameTimer {

    long startTime;
    private GameOptions currGameOptions;

    public GameTimer(GameOptions inGameOptions)
    {
        currGameOptions = inGameOptions;
    }

    public void startTimer()
    {
        Calendar c = Calendar.getInstance();
        startTime = c.getTimeInMillis();
    }

    public float timeLeftInSecs()
    {
        Calendar c = Calendar.getInstance();
        long currTime = c.getTimeInMillis();
        long millisecondsLeft = (currGameOptions.gameTime*1000) - (currTime - startTime);
        float secondsLeft = millisecondsLeft / 1000.0f;
        return secondsLeft;
    }



}
