package com.example.iow;

import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        TabHost tabHost = getTabHost();
        
        TabSpec tab1 = tabHost.newTabSpec("Home");
        tab1.setIndicator("Home", getResources().getDrawable(R.drawable.icon_home_config));
        tab1.setContent(new Intent(this, HomeActivity.class));
        
        TabSpec tab2 = tabHost.newTabSpec("Getting Here");
        tab2.setIndicator("Getting Here", getResources().getDrawable(R.drawable.icon_getting_config));
        tab2.setContent(new Intent(this, GettingHereActivity.class));
        
        TabSpec tab3 = tabHost.newTabSpec("WhatsOn");
        tab3.setIndicator("WhatsOn", getResources().getDrawable(R.drawable.icon_whatson_config));
        tab3.setContent(new Intent(this, WhatsOnActivity.class));
        
        TabSpec tab4 = tabHost.newTabSpec("Video");
        tab4.setIndicator("Video", getResources().getDrawable(R.drawable.icon_video_config));
        tab4.setContent(new Intent(this, VideoActivity.class));
        
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);
        tabHost.addTab(tab4);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
