package com.mathprog.sgrauerg.mathprog;

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

public class GameEnding extends AppCompatActivity {

    private float gameScore;
    private int userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_ending);

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

        TextView tvScore = (TextView) findViewById(R.id.gameScore);
        tvScore.setText("SCORE: " + gameScore);
    }

    public void processContinueButton()
    {
        EditText userNameEntry = (EditText)findViewById(R.id.nameEntry);
        String userName = userNameEntry.getText().toString();
        Intent continueToHighScores = new Intent(this, HighscoreListActivity.class);
        continueToHighScores.putExtra("SCORE", gameScore);
        continueToHighScores.putExtra("USER_NAME", userName);
        startActivity(continueToHighScores);
        finish();
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
