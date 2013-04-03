package com.example.iow;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class GettingHereActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getting_here);
        
        String html = "<html><body>Hello, World!</body></html>";
        String mime = "text/html";
        String encoding = "utf-8";

        WebView myWebView = (WebView)findViewById(R.id.webView1);


		Log.i("Http Response:", "btn clicked");
		// TODO Auto-generated method stub
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpGet httpGet = new HttpGet("http://iowv2.geocastapps.com/iowapp/service/node/42.json");
		
		try{
	        // writing response to log
			HttpResponse response = httpClient.execute(httpGet);
            Log.i("Http Response:0", response.toString());
            if (response.getStatusLine().getStatusCode() == 200)
            {
                HttpEntity entity = response.getEntity();
                String jsonStr = EntityUtils.toString(entity);
                Log.i("Http Response:4", jsonStr.toString());
                
                JSONObject mainObject = new JSONObject(jsonStr);
                String nid = mainObject.getString("nid");
                String body = mainObject.getString("body");
                
                Log.i("Http Response:5", nid.toString());
                Log.i("Http Response:6", body.toString());
                
                myWebView.getSettings().setJavaScriptEnabled(true);
                myWebView.loadDataWithBaseURL(null, body, mime, encoding, null);
                
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
