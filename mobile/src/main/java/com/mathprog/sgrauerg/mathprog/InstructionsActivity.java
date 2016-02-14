package com.mathprog.sgrauerg.mathprog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InstructionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        ((Button)findViewById(R.id.instructionsGoBackButton)).setOnClickListener(
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
