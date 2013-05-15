package com.example.iow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;

import com.example.iow.LazyAdapter;
import com.example.iow.R;
import com.example.iow.helper.AlertDialogManager;
import com.example.iow.helper.ConnectionDetector;
import com.example.iow.helper.JSONParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class VideoActivity extends Activity{
	
	private String URL_VIDEO_LISTING = "http://iowv2.geocastapps.com/iowapp/service/node.json?parameters[type]=youtube_video";
	AlertDialogManager alert = new AlertDialogManager();
	
	// Progress Dialog
	public ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();

	//ArrayList<HashMap<String, String>> videoList;
	ArrayList<HashMap<String, String>> videoList = new ArrayList<HashMap<String, String>>();

	// albums JSONArray
	JSONArray videoArr = null;

	// ALL JSON node names
	private static final String TAG_ID = "nid";
	private static final String TAG_TITLE = "title";
	private static final String TAG_THUMB_URI = "thumbnail";
	private static final String TAG_VIDEO_URI = "url";

	ListView lv;
    LazyAdapter adapter;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		TextView tView = new TextView(this);
		tView.setText("Video Window");
		//setContentView(tView);
		setContentView(R.layout.video_list);
		lv = (ListView) findViewById(R.id.vlist);
		Log.d("lv", "lv = "+lv);
        
		// Loading Video JSON in Background Thread
		new LoadVideos(this).execute();
	}

	public void showUI() {
	      Toast
	        .makeText(VideoActivity.this, this.toString(), Toast.LENGTH_LONG)
	        .show();
	      Log.d("adptr", "b4 videoList = "+videoList.toString());
	      LazyAdapter tadapter = new LazyAdapter(VideoActivity.this, videoList);  
	      Log.d("adptr", "tadapter = "+tadapter.toString());
	      Log.d("adptr", "lv = "+lv.toString());
	      lv.setAdapter(tadapter);
	}
	
	/**
	 * Background Async Task to Load all Albums by making http request
	 * */
	class LoadVideos extends AsyncTask<String, String, String> {

	   public VideoActivity myactivity;

	   public LoadVideos(VideoActivity a)
	    {
		   myactivity = a;
	    }
		    
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(VideoActivity.this);
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
			
			Log.i("Http Response:", "btn clicked");
			// TODO Auto-generated method stub
			HttpClient httpClient = new DefaultHttpClient();
			
			HttpGet httpGet = new HttpGet("http://iowv2.geocastapps.com/iowapp/service/node.json?parameters[type]=youtube_video");
			
			try{
		        // writing response to log
				HttpResponse response = httpClient.execute(httpGet);
	            Log.i("Http Response:0", response.toString());
	            if (response.getStatusLine().getStatusCode() == 200)
	            {
	                HttpEntity entity = response.getEntity();
	                String jsonStr = EntityUtils.toString(entity);
	                //Log.i("Http Response:4", jsonStr.toString());
	                
	    			try {				
	    				videoArr = new JSONArray(jsonStr);
	    				Log.d("Video length: ", "> " + videoArr.length());
	    				if (videoArr != null) {
	    					// looping through All albums
	    					for (int i = 0; i < videoArr.length(); i++) {
	    						JSONObject c = videoArr.getJSONObject(i);
	    						Log.d("Video inside: ", "> " + i);
	    						String nid = c.getString(TAG_ID);
	    						String title = c.getString(TAG_TITLE);
	    						Log.d("Video inside: ", "nid " + nid);
	    						Log.d("Video inside: ", "title " + title);
	    						
	    						List<NameValuePair> sub_params = new ArrayList<NameValuePair>();
	    						// getting JSON string from URL
	    						Log.d("video sub list : ", "> " + "http://iowv2.geocastapps.com/iowapp/service/node/"+nid+".json");
	    						String subjson = jsonParser.makeHttpRequest( "http://iowv2.geocastapps.com/iowapp/service/node/"+nid+".json", "GET", sub_params);

	    						// Check your log cat for JSON reponse
	    						//Log.d("subjson: ", "> " + subjson);
	    						//fetch nid and title
	    		                JSONObject mainObject = new JSONObject(subjson);
	    		                String sNid = mainObject.getString("nid");
	    		                String sTitle = mainObject.getString("title");
	    		                Log.i("Http Response:5", "nid = " + sNid.toString());
	    		                Log.i("Http Response:6", "sTitle = " + sTitle.toString());
	    		                
	    		                String field_video_url = mainObject.getString("field_video_url");
	    		                JSONArray urlArr = new JSONArray(field_video_url);
	    		                JSONObject urlData = new JSONObject(urlArr.get(0).toString());

                                //fetch duration
                                String durationStr = urlData.get("data").toString();
                                JSONObject durationObj = new JSONObject(durationStr.toString());
                                String duration = durationObj.get("duration").toString();;
                                Log.i("Http Response:13", "duration in seconds = " + duration.toString());
                                Integer vMin = (Integer)Math.round((Integer.parseInt(duration)/60));
                                Integer vSec =  (Integer.parseInt(duration)%60);
                               
                                String videoDuration;
                                if(vSec<10){
                                    videoDuration = vMin.toString()+":0"+ vSec.toString();
                                }else{
                                    videoDuration = vMin.toString()+":"+vSec.toString();
                                }
                                Log.i("Http Response:13a", "duration in seconds videoDuration = "+ videoDuration);
	    		                
	    		                //fetch thumbnailUrl
	    		                String dataStr = urlData.get("data").toString();
	    		                JSONObject dataObj = new JSONObject(dataStr.toString());
	    		                String thumbnailStr = dataObj.get("thumbnail").toString();
	    		                JSONObject thumbnailObj = new JSONObject(thumbnailStr.toString());
	    		                String thumbnailUrl = thumbnailObj.get("url").toString();
	    		                Log.i("Http Response:10", "thumbnailUrl = " + thumbnailUrl.toString());
	    		               
	    		                //fetch flashUrl
	    		                String flashStr = dataObj.get("flash").toString();
	    		                JSONObject flashObj = new JSONObject(flashStr.toString());
	    		                String flashUrl = flashObj.get("url").toString();
	    		                Log.i("Http Response:12", "flashUrl = " + flashUrl.toString()); 
	    		                
	    		    			// creating new HashMap
	    		    			HashMap<String, String> map = new HashMap<String, String>();
	    		    			// adding each child node to HashMap key => value
	    		    			map.put("nid", sNid);
	    		    			map.put("title", sTitle);
	    		    			map.put("thumbnailUrl", thumbnailUrl);
	    		    			map.put("flashUrl", flashUrl);
	    		    			map.put("videoDuration", videoDuration);

	    		    			// adding HashList to ArrayList
	    		    			videoList.add(map);
	    					}
	    				}else{
	    					Log.d("videoArr: ", "null");
	    				}

	    			} catch (JSONException e) {
	    				e.printStackTrace();
	    			}
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
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all albums
			  pDialog.dismiss();
		      Toast.makeText(VideoActivity.this, "Done! = "+videoList.toString(), Toast.LENGTH_LONG).show();
		      myactivity.showUI();
		}

	}
   
}
