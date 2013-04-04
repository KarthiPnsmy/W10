package com.example.iow;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

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
        
        if(mId.length() == 1){
        	mId = "0" + mId;
        }
        Log.i("str", mId);
        navTitle.setText("What's on? - "+mTitle+" - "+mId);
        
        
		Log.i("Http Response:", "initiated");
		// TODO Auto-generated method stub
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpGet httpGet = new HttpGet("http://iowv2.geocastapps.com/iowapp/service/node.json?parameters[type]=whats_on&parameters[month]=01");
		
		try{
	        // writing response to log
			HttpResponse response = httpClient.execute(httpGet);
            Log.i("Http Response:0", response.toString());
            Log.i("Http Response:1", response.getStatusLine().getStatusCode()+"");
            if (response.getStatusLine().getStatusCode() == 200)
            {
                HttpEntity entity = response.getEntity();
                String jsonStr = EntityUtils.toString(entity);
                Log.i("Http Response:4", jsonStr.toString());
                TextView tv = (TextView) findViewById(R.id.textView1);
                tv.setText(jsonStr);
                /*
                JSONObject mainObject = new JSONObject(jsonStr);
                String nid = mainObject.getString("nid");
                String body = mainObject.getString("body");
                
                Log.i("Http Response:5", nid.toString());
                Log.i("Http Response:6", body.toString());
				*/
            }else{
            	Log.i("Http Response:3", "inside else");
            }
            
        } catch (Exception e) {
        	Log.i("Http Response:", "Exception 2");
        	Log.i("Http Response:1", e.toString());
            // writing exception to log
            e.printStackTrace();
        }
    }
}
