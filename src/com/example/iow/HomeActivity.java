package com.example.iow;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        
        setContentView(R.layout.home);
        /*
        TextView tView = new TextView(this);
        tView.setText("Home Window");
        setContentView(tView);
        */
        
        Button foodBtn = (Button) findViewById(R.id.foodBtn);
        
        foodBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.e("info", "Food Btn clicked test commit");
				
			}
		});
        
        Button accomadationBtn = (Button) findViewById(R.id.accBtn);
        accomadationBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.e("info", "Accomadation Btn clicked1");
			}
		});
        
        Button attractionBtn = (Button) findViewById(R.id.attractionBtn);
        attractionBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.e("info", "Attraction Btn clicked1");
			}
		});
        
        Button shoppingBtn = (Button) findViewById(R.id.shoppingBut);
        shoppingBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.e("info", "Sshopping Btn clicked1");
			}
		});
    }


}
