package com.mathprog.sgrauerg.monochromemath;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mathprog.sgrauerg.monochromemath.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class GameEnding extends AppCompatActivity {

    private float gameScore;
    private int userName;
    InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_ending);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9736755972412076/9847937546");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                continueToHighScores();
            }
        });

        requestNewInterstitial();

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        ((Button)findViewById(R.id.continueButton)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        processContinueButton();
                    }
                }
        );
        Bundle extras = getIntent().getExtras();
        gameScore = extras.getFloat("SCORE");
        String gameResults = extras.getString("GAME_RESULTS");

        TextView tv = (TextView) findViewById(R.id.gameEndingResults);
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());
        tv.setText(gameResults);

        NumberFormat formatter = new DecimalFormat("#0.00");

        TextView tvScore = (TextView) findViewById(R.id.gameScore);
        tvScore.setText("SCORE: " + formatter.format(gameScore));
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }

    public void continueToHighScores()
    {
        EditText userNameEntry = (EditText)findViewById(R.id.nameEntry);
        String userName = userNameEntry.getText().toString();
        Intent continueToHighScores = new Intent(this, HighscoreListActivity.class);
        continueToHighScores.putExtra("SCORE", gameScore);
        continueToHighScores.putExtra("USER_NAME", userName);
        startActivity(continueToHighScores);
        finish();
    }

    public void processContinueButton()
    {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
        else
        {
            continueToHighScores();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }
}
