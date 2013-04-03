package com.example.iow;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class WhatsOnActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        
        TextView tView = new TextView(this);
        tView.setText("WhatsOn Window");
        setContentView(tView);
    }
}
