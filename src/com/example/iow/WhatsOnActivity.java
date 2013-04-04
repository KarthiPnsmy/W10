package com.example.iow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class WhatsOnActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.whatson);
        String[] monthList = getResources().getStringArray(R.array.month_list);
        		
        ListView lv = (ListView) findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.label, monthList);
        lv.setAdapter(adapter);
        
        lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				String mnthTxt = ((TextView) view.findViewById(R.id.label)).getText().toString();
				Log.i("mnTxt", mnthTxt);
				Log.i("mnTxt", "position = "+position);
				Log.i("mnTxt", "id = "+id);
				
				Intent i = new Intent(getApplicationContext(), WhatsOnListActivity.class);
				i.putExtra("selectedMonthTitle", mnthTxt);
				i.putExtra("selectedMonthId", id+"");
				startActivity(i);
				/*
				view.findViewById(R.id.label).
				Log.i("mnTxt", "parent "+parent.toString());
				Log.i("mnTxt", "parent "+parent);
				Log.i("mnTxt", "position1 "+view.findViewById(R.id.label).toString());
				Log.i("mnTxt", "position "+position);
				Log.i("mnTxt", "id "+id);
				*/
			}
		});
    }
}
