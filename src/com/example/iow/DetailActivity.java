package com.example.iow;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.iow.helper.AlertDialogManager;
import com.example.iow.helper.ConnectionDetector;
import com.example.iow.helper.JSONParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends Activity{
	
	TextView navTitle; 
	TextView subTitle; 
	ImageView itmImage; 
	TextView descText; 
	public String titleTxt = "";
	public String body = "";
	public String imageUrl = "";
	public String phoneNo = "";
	public String emailText = "";
	public String mLat = "";
	public String mLong = "";
	private String URL_WHATSON_DETAIL = "http://iowv2.geocastapps.com/iowapp/service/node/";

	AlertDialogManager alert = new AlertDialogManager();

	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        TextView tView = new TextView(this);
        tView.setText("Detail Window");
       // setContentView(tView);
        
    	navTitle = (TextView) findViewById(R.id.navTitle); 
    	navTitle.setText("Detail Window1");
    	subTitle = (TextView) findViewById(R.id.subTitle); 
    	itmImage = (ImageView) findViewById(R.id.itmImage); 
    	descText = (TextView) findViewById(R.id.descText); 
    	
        Intent i = getIntent();
        String nodeId = i.getStringExtra("nodeId");
        Log.i("node ", "nodeId = "+nodeId);
        URL_WHATSON_DETAIL = URL_WHATSON_DETAIL + nodeId + ".json";
        Log.i("node ", "URL_WHATSON_DETAIL = "+URL_WHATSON_DETAIL);
        
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		 
        // Check for internet connection
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(DetailActivity.this, "Internet Connection Error", "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }
        
		// Loading XHR in Background Thread
		new LoadDetail().execute();
    }

	class LoadDetail extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(DetailActivity.this);
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
			Log.d("URL_GETTING_HERE: ", "> " + URL_WHATSON_DETAIL);
			String jsonStr = jsonParser.makeHttpRequest(URL_WHATSON_DETAIL, "GET", params);

			// Check your log cat for JSON response
			Log.d("JSON: ", "> " + jsonStr);

			try {				
				if (jsonStr != null) {
	                Log.i("Http Response:4", jsonStr.toString());
	                JSONObject mainObject = new JSONObject(jsonStr);
	                String nid = mainObject.getString("nid");
	                titleTxt = mainObject.getString("title");
	                body = mainObject.getString("body");
	                
	                //fetch phone no
	                String field_call = mainObject.getString("field_call");
	                JSONArray callArr = new JSONArray(field_call);
	                JSONObject callData = new JSONObject(callArr.get(0).toString());
	                phoneNo = callData.get("value").toString();
	                
	                //fetch image url
	                String field_item_img = mainObject.getString("field_item_img");
	                JSONArray imgArr = new JSONArray(field_item_img);
	                JSONObject imageData = new JSONObject(imgArr.get(0).toString());
	                imageUrl = imageData.get("filepath").toString();

	                //fetch email 
	                String field_email = mainObject.getString("field_email");
	                JSONArray emailArr = new JSONArray(field_email);
	                JSONObject emailData = new JSONObject(emailArr.get(0).toString());
	                emailText = emailData.get("value").toString();
	                
	                //fetch latitude
	                String field_latitude = mainObject.getString("field_latitude");
	                JSONArray latArr = new JSONArray(field_latitude);
	                JSONObject latData = new JSONObject(latArr.get(0).toString());
	                mLat = latData.get("value").toString();
	                
	                //fetch longitude
	                String field_longitude = mainObject.getString("field_longitude");
	                JSONArray longArr = new JSONArray(field_longitude);
	                JSONObject longData = new JSONObject(longArr.get(0).toString());
	                mLong = longData.get("value").toString();
	                
	                TextView navTitle1 = (TextView) findViewById(R.id.navTitle); 
	                /*

	                */
	                Log.i("Http Response:5", nid.toString());
	                Log.i("Http Response:7", titleTxt.toString());
	                Log.i("Http Response:6", body.toString());
	                Log.i("Http Response:8", field_call.toString()); 
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
	                Log.i("Http Response:9", phoneNo.toString());
	                Log.i("Http Response:9", imageUrl.toString());
	                Log.i("Http Response:9", emailText.toString()); 
	                Log.i("Http Response:9", mLat.toString()); 
	                Log.i("Http Response:9", mLong.toString()); 
	                
	                subTitle.setText(titleTxt);
	                if(titleTxt.length() > 17){
	                	String tmpStr = titleTxt.substring(0, 15);
	                	tmpStr = tmpStr + "...";
	                	navTitle.setText(tmpStr);
	                }else{
	                	navTitle.setText(titleTxt);
	                }
	            	descText.setText(body); 
	            	
	            	//set image
	            	/*
            		Log.i("imgSrc URL", "http://iowv2.geocastapps.com/iowapp/"+imageUrl);
            		Bitmap bimage=  getBitmapFromURL("http://iowv2.geocastapps.com/iowapp/"+imageUrl);
            		itmImage.setImageBitmap(bimage);
	            	*/
	            	try {
	            		Log.i("imgSrc URL", "http://iowv2.geocastapps.com/iowapp/"+imageUrl);
	            	    URL thumb_u = new URL("http://iowv2.geocastapps.com/iowapp/"+imageUrl);
	            	    Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
	            	    itmImage.setImageDrawable(thumb_d);
	            	}
	            	catch (Exception e) {
	            	    // handle it
	            		Log.i("imgSrc error", e.toString());
	            	}
	            	
				}
			});
		}

	}

	public static Bitmap getBitmapFromURL(String src) {
	        try {
	            Log.e("src",src);
	            URL url = new URL(src);
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setDoInput(true);
	            connection.connect();
	            InputStream input = connection.getInputStream();
	            Bitmap myBitmap = BitmapFactory.decodeStream(input);
	            Log.e("Bitmap","returned");
	            return myBitmap;
	        } catch (IOException e) {
	            e.printStackTrace();
	            Log.e("Exception",e.getMessage());
	            return null;
	        }
	}
	 
	public void closeWin(View v) {
		finish();
	}

	public void showMessage() {
		Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
	}
	
	public void doCall(View v) {
		Log.i("icon", "doCall cclicked");
		if(phoneNo != ""){
            try {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+phoneNo.toString()));
                startActivity(callIntent);
            } catch (Exception e) {
                Log.i("Calling a Phone Number", e.toString());
            }
		}else{
			showMessage();
		}
	}
	
	public void goToMap(View v) {
		Log.i("icon", "goToMap cclicked");
		if(mLat != "" && mLong != ""){
			//startActivity(new Intent(this, MapDetail.class));
			/*
			Intent i = new Intent(getApplicationContext(), WhatsOnListActivity.class);
			i.putExtra("selectedMonthTitle", "jan");
			i.putExtra("selectedMonthId", "01");
			startActivity(i);
			*/
			Intent i = new Intent(getApplicationContext(), MapDetail.class);
			i.putExtra("titleTxt", titleTxt);
			i.putExtra("mLat", mLat);
			i.putExtra("mLong", mLong);
			startActivity(i);
		}else{
			showMessage();
		}
	}
	
	public void doEmail(View v) {
		Log.i("icon", "doEmail cclicked");
		if(emailText != ""){
			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {emailText});
			emailIntent.setType("text/plain");
			startActivity(Intent.createChooser(emailIntent, "Send a mail ..."));
		}else{
			showMessage();
		}
	}
}
