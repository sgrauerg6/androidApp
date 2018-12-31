package com.mathprog.sgrauerg.monochromemath;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.mathprog.sgrauerg.monochromemath.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class IntroActivity extends AppCompatActivity {

    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.activity_intro);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        ((Button)findViewById(R.id.playButton)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        playButtonAction();
                    }
                });

        ((Button)findViewById(R.id.insttructionsButton)).setOnClickListener(
                new View.OnClickListener()
        {
                @Override
                public void onClick(View v)
                {
                    instructionsButtonAction();
                }
        }
        );

       /* ((Button)findViewById(R.id.statsButton)).setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        statsButtonAction();
                    }
                }
        );*/

        ((Button)findViewById(R.id.highScoreButton)).setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        highScoreButtonAction();
                    }
                }
        );
    }

    public void playButtonAction()
    {
        Intent playGameIntent = new Intent(this, GameplayActivity.class);
        startActivity(playGameIntent);
    }

    public void instructionsButtonAction()
    {
        Intent instructionsIntent = new Intent(this, InstructionsActivity.class);
        startActivity(instructionsIntent);
    }

    public void statsButtonAction()
    {
        Intent overallStatsIntent = new Intent(this, OverallStatsActivity.class);
        startActivity(overallStatsIntent);
    }

    public void highScoreButtonAction()
    {
        Intent highScoreIntent = new Intent(this, HighscoreListActivity.class);
        startActivity(highScoreIntent);
    }
}
