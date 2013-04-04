package com.example.iow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class WhatsOnListActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.whatson_list);
        
        Button backBtButton = (Button) findViewById(R.id.backBtn);
        backBtButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
        TextView navTitle = (TextView) findViewById(R.id.navTitle);
        Intent i = getIntent();
        String mTitle = i.getStringExtra("selectedMonthTitle");
        String mId = i.getStringExtra("selectedMonthId");
        navTitle.setText("What's on? - "+mTitle+" - "+mId);
    }
}
