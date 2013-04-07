package com.example.iow;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.iow.R;
import com.example.iow.helper.JSONParser;
import com.example.iow.helper.AlertDialogManager;
import com.example.iow.helper.ConnectionDetector;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;


public class GettingHereActivity extends Activity
{
	private String URL_GETTING_HERE = "http://iowv2.geocastapps.com/iowapp/service/node/42.json";
	   
	AlertDialogManager alert = new AlertDialogManager();

	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();
	WebView myWebView;
    String html = "<html><body>Hello, World!</body></html>";
    String mime = "text/html";
    String encoding = "utf-8";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getting_here);

        myWebView = (WebView)findViewById(R.id.webView1);

        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		 
        // Check for internet connection
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            //alert.showAlertDialog(GettingHereActivity.this, "Internet Connection Error", "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }
        
		// Loading XHR in Background Thread
		new LoadGettingHere().execute();
    }
    
	class LoadGettingHere extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(GettingHereActivity.this);
			pDialog.setMessage("Loading ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting Albums JSON
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			// getting JSON string from URL
			Log.d("URL_GETTING_HERE: ", "> " + URL_GETTING_HERE);
			String jsonStr = jsonParser.makeHttpRequest(URL_GETTING_HERE, "GET", params);

			// Check your log cat for JSON response
			Log.d("JSON: ", "> " + jsonStr);

			try {				
				if (jsonStr != null) {
	                Log.i("Http Response:4", jsonStr.toString());
	                JSONObject mainObject = new JSONObject(jsonStr);
	                String nid = mainObject.getString("nid");
	                String body = mainObject.getString("body");
	                Log.i("Http Response:5", nid.toString());
	                Log.i("Http Response:6", body.toString());  
	                myWebView.getSettings().setJavaScriptEnabled(true);
	                myWebView.loadDataWithBaseURL(null, body, mime, encoding, null);
				}else{
					Log.d("gettingObj : ", "null");
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all albums
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {

				}
			});

		}

	}
}
