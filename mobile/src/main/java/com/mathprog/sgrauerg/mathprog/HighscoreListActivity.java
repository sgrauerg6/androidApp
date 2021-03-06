package com.mathprog.sgrauerg.monochromemath;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mathprog.sgrauerg.monochromemath.R;


public class HighscoreListActivity extends AppCompatActivity {

    private HighScores localHighScoreList;

    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.activity_highscore_list);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        localHighScoreList = HighScores.loadHighScoresFromFile(getBaseContext().getFilesDir().toString() + "HIGHSCORZ");
        if (localHighScoreList == null)
        {
            localHighScoreList = new HighScores();
        }

        Bundle extras = getIntent().getExtras();

        if (extras != null)
        {
            String userName = extras.getString("USER_NAME");
            float score = extras.getFloat("SCORE");
            localHighScoreList.addHighScore(userName, score);
            HighScores.saveHighScoresToFile(localHighScoreList, getBaseContext().getFilesDir().toString() + "HIGHSCORZ");
        }

        ((Button)findViewById(R.id.playAgainButton)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        processPlayAgainButtonClick();
                    }
                }
        );

       /* ((Button)findViewById(R.id.currentStatsButton)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        processOverallStatsButtonClick();
                    }
                }
        );*/

        ((Button)findViewById(R.id.exitButton)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        processExitButtonClick();
                    }
                }
        );

        if (localHighScoreList != null) {
            String highScoreNamesString = localHighScoreList.getHighScoresNamesOnlyString(10);
            String highScoreScoresString = localHighScoreList.getHighScoresScoresOnlyString(10);
            ((TextView) findViewById(R.id.highScoreNames)).setText(highScoreNamesString);
            ((TextView) findViewById(R.id.highScoreScores)).setText(highScoreScoresString);
        }
    }

    public void processPlayAgainButtonClick()
    {
        Intent i = new Intent(this, GameplayActivity.class);
        startActivity(i);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void processExitButtonClick()
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);;
    }

    public void processOverallStatsButtonClick()
    {
        Intent i = new Intent(this, OverallStatsActivity.class);
        startActivity(i);
    }
}
