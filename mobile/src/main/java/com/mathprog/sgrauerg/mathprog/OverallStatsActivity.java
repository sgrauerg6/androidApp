package com.mathprog.sgrauerg.monochromemath;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mathprog.sgrauerg.monochromemath.R;

public class OverallStatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overall_stats);

        ((Button)findViewById(R.id.overallStatsGoBackButton)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        processGoBackButton();
                    }
                }
        );
    }

    public void processGoBackButton()
    {
        Intent goBackButtonIntent = new Intent(this, IntroActivity.class);
        startActivity(goBackButtonIntent);
        finish();
    }
}
