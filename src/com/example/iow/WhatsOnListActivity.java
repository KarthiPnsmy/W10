package com.example.iow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.iow.R;
import com.example.iow.helper.JSONParser;
import com.example.iow.helper.AlertDialogManager;
import com.example.iow.helper.ConnectionDetector;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class WhatsOnListActivity extends ListActivity {
	
	private String URL_WHATSON_LISTING = "http://iowv2.geocastapps.com/iowapp/service/node.json?parameters[type]=whats_on&parameters[month]=";
   
	AlertDialogManager alert = new AlertDialogManager();
	
	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();

	ArrayList<HashMap<String, String>> watsonList;

	// albums JSONArray
	JSONArray albums = null;
	
	// ALL JSON node names
	private static final String TAG_ID = "nid";
	private static final String TAG_TITLE = "title";
	private static final String TAG_URI = "uri";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
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
        navTitle.setText("What's on? - "+mTitle);
        URL_WHATSON_LISTING = URL_WHATSON_LISTING + mId;
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		 
        // Check for internet connection
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(WhatsOnListActivity.this, "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }
        

		// Hashmap for ListView
        watsonList = new ArrayList<HashMap<String, String>>();

		// Loading Albums JSON in Background Thread
		new LoadWhatson().execute();
		
		// get listview
		ListView lv = getListView();
    }
	
	

	/**
	 * Background Async Task to Load all Albums by making http request
	 * */
	class LoadWhatson extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(WhatsOnListActivity.this);
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
			Log.d("Albums URL_WHATSON_LISTING: ", "> " + URL_WHATSON_LISTING);
			String json = jsonParser.makeHttpRequest(URL_WHATSON_LISTING, "GET", params);

			// Check your log cat for JSON reponse
			Log.d("Albums JSON: ", "> " + json);

			try {				
				albums = new JSONArray(json);
				Log.d("Albums length: ", "> " + albums.length());
				if (albums != null) {
					// looping through All albums
					for (int i = 0; i < albums.length(); i++) {
						JSONObject c = albums.getJSONObject(i);
						Log.d("Albums inside: ", "> " + i);
						// Storing each json item values in variable
						String nid = c.getString(TAG_ID);
						String title = c.getString(TAG_TITLE);
						String nuri = c.getString(TAG_URI);

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_ID, nid);
						map.put(TAG_TITLE, title);
						map.put(TAG_URI, nuri);
						map.put("imgThump", Integer.toString(R.drawable.arrow_white));

						// adding HashList to ArrayList
						watsonList.add(map);
					}
				}else{
					Log.d("Albums: ", "null");
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
					/**
					 * Updating parsed JSON data into ListView
					 * */
					Log.d("Albums onPostExecute: ", "> ");
					Log.d("Albums watsonList: ", "> "+watsonList.toString());

					if(watsonList.size() == 0){
						Toast.makeText(getBaseContext(), "No data found", Toast.LENGTH_SHORT).show();
						finish();
					}
					
					ListAdapter adapter = new SimpleAdapter(
							WhatsOnListActivity.this, watsonList,
							R.layout.list_item_watson, new String[] { TAG_ID, TAG_TITLE, "imgThump" }, new int[] {R.id.nid, R.id.titleTxt, R.id.arrow });
					
					// updating listview
					setListAdapter(adapter);
				}
			});

		}

	}
}
