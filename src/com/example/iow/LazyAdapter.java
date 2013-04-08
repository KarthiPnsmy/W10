package com.example.iow;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_item_video, null);

        TextView title = (TextView)vi.findViewById(R.id.title); // title
        TextView vUrl = (TextView)vi.findViewById(R.id.flashUrl); // flashUrl name
        TextView node = (TextView)vi.findViewById(R.id.nid); // node
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        
        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);
        Log.i("song item", "song item = "+song);
        
        // Setting all values in listview
        title.setText(song.get("title"));
        vUrl.setText(song.get("flashUrl"));
        node.setText(song.get("nid"));
        imageLoader.DisplayImage(song.get("thumbnailUrl"), thumb_image);
        
        return vi;
    }
}