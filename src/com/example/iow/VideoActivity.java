package com.example.iow;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class VideoActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		TextView tView = new TextView(this);
		tView.setText("Video Window");
	}
}
