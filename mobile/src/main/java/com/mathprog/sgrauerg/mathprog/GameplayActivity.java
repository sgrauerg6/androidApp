package com.mathprog.sgrauerg.mathprog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GameplayActivity extends AppCompatActivity {

    private GameOptions currGameOptions = new GameOptions();
    private MathProblem currMathProb = new MathProblem();
    private GameStatus currGameState = new GameStatus();
    private GameTimer gameTimer = new GameTimer(currGameOptions);
    private boolean gameOver = false;
    private Thread timerThread = null;
    private boolean darkTheme = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("ON_CREATE", "ON_CREATE");
        setContentView(R.layout.activity_gameplay);
        startGame();
        TextView tv = (TextView) findViewById(R.id.results);
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.v("ON_START", "ON_START");
        setContentView(R.layout.activity_gameplay);
        startGame();
        TextView tv = (TextView) findViewById(R.id.results);
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.v("ON_PAUSE", "ON_PAUSE");
        if ((timerThread != null) && (!gameOver)) {
            timerThread.interrupt();
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.v("ON_STOP", "ON_STOP");

        if ((timerThread != null) && (!gameOver)) {
            timerThread.interrupt();
        }

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.v("ON_RESUME", "ON_RESUME");

        setContentView(R.layout.activity_gameplay);
        startGame();

        if ((timerThread != null) && (timerThread.getState() == Thread.State.TERMINATED)) {
            timer();
        }
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        Log.v("ON_RESTART", "ON_RESTART");

        setContentView(R.layout.activity_gameplay);
        startGame();

        if ((timerThread != null) && (timerThread.getState() == Thread.State.TERMINATED)) {
            timer();
        }
    }

    public void startGame()
    {
        gameOver = false;

        currGameState.startGame();
        gameTimer.startTimer();
        currMathProb.genNewProblem(currGameOptions);
        timer();

        ((TextView)findViewById(R.id.leftSideProb)).setText(currMathProb.getLeftSideStringRep());
        ((TextView)findViewById(R.id.probOperator)).setText(currMathProb.getOpStringRep());
        ((TextView)findViewById(R.id.rightSideProb)).setText(currMathProb.getRightSideStringRep());

        List<Integer> possAnswers = currMathProb.genPossAnswers(4);
        ((Button)findViewById(R.id.button)).setText(Integer.toString(possAnswers.get(0)));
        ((Button)findViewById(R.id.button2)).setText(Integer.toString(possAnswers.get(1)));
        ((Button)findViewById(R.id.button3)).setText(Integer.toString(possAnswers.get(2)));
        ((Button)findViewById(R.id.button4)).setText(Integer.toString(possAnswers.get(3)));

        ((Button)findViewById(R.id.button)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        processAnswerSubmission(v);
                    }
                });
        ((Button)findViewById(R.id.button2)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        processAnswerSubmission(v);
                    }
                });
        ((Button)findViewById(R.id.button3)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        processAnswerSubmission(v);
                    }
                });
        ((Button)findViewById(R.id.button4)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        processAnswerSubmission(v);
                    }
                });

        ((TextView)findViewById(R.id.numRightTotal)).setText("Answers Correct/Total:    " +
                Integer.toString(currGameState.getNumCorrect()) + " / " +
                Integer.toString(currGameState.getNumCorrect() + currGameState.getNumWrong()));
        String currScoreText = String.format("%.1f", currGameState.getCurrScore());
        ((TextView) findViewById(R.id.score)).setText("Score: " + currScoreText);

        ((TextView) findViewById(R.id.prevAnswerResult)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.prevAnswerResult)).setText("Previous Result: None Yet");
        ((TextView) findViewById(R.id.results)).setText("Results:\n" + currGameState.getAllResultsString());

        currGameState.startQuestion();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void processAnswerSubmission(View v)
    {
        Calendar c = Calendar.getInstance();
        long timeAtPress = c.getTimeInMillis();
        int numOnButton = Integer.parseInt(((Button) v).getText().toString());
        boolean answerCorrect = false;
        currGameState.processResult(currMathProb, numOnButton, timeAtPress);
        if (numOnButton == currMathProb.correctAnswer())
        {
            answerCorrect = true;

        }
        else {
            answerCorrect = false;
        }

        ((TextView)findViewById(R.id.numRightTotal)).setText("Answers Correct/Total:    " + Integer.toString(currGameState.getNumCorrect())
                + " / " + Integer.toString(currGameState.getNumCorrect() + currGameState.getNumWrong()));
        if (answerCorrect)
        {
            ((TextView) findViewById(R.id.prevAnswerResult)).setText("Prev: CORRECT (" +
                    currMathProb.getLeftSideStringRep() + currMathProb.getOpStringRep() +
                    currMathProb.getRightSideStringRep() + "=" + numOnButton + ")");
        }
        else
        {
            ((TextView) findViewById(R.id.prevAnswerResult)).setText("Prev: WRONG (" +
                    currMathProb.getLeftSideStringRep() + currMathProb.getOpStringRep() +
                    currMathProb.getRightSideStringRep() + "=" + currMathProb.correctAnswer() +
                    " NOT " + numOnButton + ")");
        }
        String currScoreText = String.format("%.1f", currGameState.getCurrScore());
        ((TextView) findViewById(R.id.score)).setText("Score: " + currScoreText);
        ((TextView) findViewById(R.id.results)).setText("Results:\n" + currGameState.getAllResultsString());

        TextView tv = (TextView) findViewById(R.id.results);
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());
        setupNextProblem();
    }

    public void setupNextProblem()
    {
        currMathProb.genNewProblem(currGameOptions);
        ((TextView)findViewById(R.id.leftSideProb)).setText(currMathProb.getLeftSideStringRep());
        ((TextView)findViewById(R.id.probOperator)).setText(currMathProb.getOpStringRep());
        ((TextView)findViewById(R.id.rightSideProb)).setText(currMathProb.getRightSideStringRep());

        List<Integer> possAnswers = currMathProb.genPossAnswers(4);
        ((Button)findViewById(R.id.button)).setText(Integer.toString(possAnswers.get(0)));
        ((Button)findViewById(R.id.button2)).setText(Integer.toString(possAnswers.get(1)));
        ((Button)findViewById(R.id.button3)).setText(Integer.toString(possAnswers.get(2)));
        ((Button)findViewById(R.id.button4)).setText(Integer.toString(possAnswers.get(3)));

        currGameState.startQuestion();
    }

    public void timer()
    {
        timerThread = new Thread(){
            public void run() {
                try
                {
                    float secsLeft = -1;
                    do {
                        Thread.sleep(100);
                        secsLeft = gameTimer.timeLeftInSecs();
                        final float secsLeftFinal = secsLeft;
                        final String secsLeftPrint = String.format("%.1f", secsLeft);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run()
                            {
                                float proportionDone = 1.0f - (secsLeftFinal / ((float)GameOptions.GAME_TIME));

                                if ((proportionDone < .5) && (darkTheme))
                                {
                                    switchToLightTheme();
                                }
                                if ((proportionDone > .5) && (!darkTheme))
                                {
                                    switchToDarkTheme();
                                }
                                int colorLevel = Math.min((int)Math.floor(255 - (proportionDone * 255)), 255);
                                ((LinearLayout)findViewById(R.id.linearLayout)).
                                        setBackgroundColor(Color.rgb(colorLevel, colorLevel, colorLevel));
                                ((RelativeLayout)findViewById(R.id.outerLayout)).
                                        setBackgroundColor(Color.rgb(colorLevel, colorLevel, colorLevel));
                                ((TextView)findViewById(R.id.timeLeft)).setText("Time Left: " + secsLeftPrint);
                            }
                        });
                        if (secsLeft <= 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    endGame();
                                    ((TextView)findViewById(R.id.timeLeft)).setText("Time Left: 0.0");
                                }
                            });
                        }
                    } while (secsLeft > 0);
                }
                catch (InterruptedException e)
                {
                    Log.v("EXCEPTION", "ThreadSleepError");
                    return;
                }

            } };

        timerThread.start();
    }

    public void switchToDarkTheme() {
        darkTheme = true;
        ((TextView)findViewById(R.id.score)).setTextColor(Color.WHITE);
        ((TextView)findViewById(R.id.timeLeft)).setTextColor(Color.WHITE);
        ((TextView)findViewById(R.id.numRightTotal)).setTextColor(Color.WHITE);
        ((TextView)findViewById(R.id.prevAnswerResult)).setTextColor(Color.WHITE);
        ((TextView)findViewById(R.id.leftSideProb)).setTextColor(Color.WHITE);
        ((TextView)findViewById(R.id.probOperator)).setTextColor(Color.WHITE);
        ((TextView)findViewById(R.id.rightSideProb)).setTextColor(Color.WHITE);
        ((TextView)findViewById(R.id.equalProb)).setTextColor(Color.WHITE);
        ((TextView)findViewById(R.id.results)).setTextColor(Color.WHITE);

        ((Button)findViewById(R.id.button)).getBackground().setColorFilter(new LightingColorFilter(0x000000, 0x000000));
        ((Button)findViewById(R.id.button)).setTextColor(Color.WHITE);
        ((Button)findViewById(R.id.button2)).getBackground().setColorFilter(new LightingColorFilter(0x000000, 0x000000));
        ((Button)findViewById(R.id.button2)).setTextColor(Color.WHITE);
        ((Button)findViewById(R.id.button3)).getBackground().setColorFilter(new LightingColorFilter(0x000000, 0x000000));
        ((Button)findViewById(R.id.button3)).setTextColor(Color.WHITE);
        ((Button)findViewById(R.id.button4)).getBackground().setColorFilter(new LightingColorFilter(0x000000, 0x000000));
        ((Button)findViewById(R.id.button4)).setTextColor(Color.WHITE);
    }

    public void switchToLightTheme() {
        darkTheme = false;
        ((TextView)findViewById(R.id.score)).setTextColor(Color.BLACK);
        ((TextView)findViewById(R.id.timeLeft)).setTextColor(Color.BLACK);
        ((TextView)findViewById(R.id.numRightTotal)).setTextColor(Color.BLACK);
        ((TextView)findViewById(R.id.prevAnswerResult)).setTextColor(Color.BLACK);
        ((TextView)findViewById(R.id.leftSideProb)).setTextColor(Color.BLACK);
        ((TextView)findViewById(R.id.probOperator)).setTextColor(Color.BLACK);
        ((TextView)findViewById(R.id.rightSideProb)).setTextColor(Color.BLACK);
        ((TextView)findViewById(R.id.equalProb)).setTextColor(Color.BLACK);
        ((TextView)findViewById(R.id.results)).setTextColor(Color.BLACK);

        ((Button)findViewById(R.id.button)).getBackground().setColorFilter(new LightingColorFilter(0x000000, 0xaaaaaa));
        ((Button)findViewById(R.id.button)).setTextColor(Color.BLACK);
        ((Button)findViewById(R.id.button2)).getBackground().setColorFilter(new LightingColorFilter(0x000000, 0xaaaaaa));
        ((Button)findViewById(R.id.button2)).setTextColor(Color.BLACK);
        ((Button)findViewById(R.id.button3)).getBackground().setColorFilter(new LightingColorFilter(0x000000, 0xaaaaaa));
        ((Button)findViewById(R.id.button3)).setTextColor(Color.BLACK);
        ((Button)findViewById(R.id.button4)).getBackground().setColorFilter(new LightingColorFilter(0x000000, 0xaaaaaa));
        ((Button)findViewById(R.id.button4)).setTextColor(Color.BLACK);
    }

    public void endGame()
    {
        gameOver = true;
        Intent gameEndActivity = new Intent(this, GameEnding.class);
        gameEndActivity.putExtra("SCORE", currGameState.getCurrScore());
        gameEndActivity.putExtra("GAME_RESULTS", "Results:\n" + currGameState.getAllResultsString());
        startActivity(gameEndActivity);
        finish();
    }
}
