package com.example.iow;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class GettingHereActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        
        TextView tView = new TextView(this);
        tView.setText("Getting Here Window");
        setContentView(tView);
    }
}
