package com.mathprog.sgrauerg.monochromemath;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.text.method.ScrollingMovementMethod;
import android.graphics.LightingColorFilter;

import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("ON_CREATE", "ON_CREATE");
        Intent i = new Intent(this, IntroActivity.class);
        startActivity(i);
    }
}
