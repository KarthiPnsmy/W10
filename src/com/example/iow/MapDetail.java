package com.example.iow;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapDetail extends MapActivity{

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.map);
	    
	    Intent i = getIntent();
	    String titleTxt = i.getStringExtra("titleTxt");
	    String mLat = i.getStringExtra("mLat");
	    String mLong = i.getStringExtra("mLong");
	    
	    Log.i("Map Title", titleTxt);
	    MapView mapView = (MapView) findViewById(R.id.mapview);
	    mapView.setBuiltInZoomControls(true);    
	    
	    MapController mc = mapView.getController();
	    double lat = Double.parseDouble(mLat); // latitude
	    double lon = Double.parseDouble(mLong); // longitude
	    GeoPoint geoPoint = new GeoPoint((int)(lat * 1E6), (int)(lon * 1E6));
	    mc.animateTo(geoPoint);
	    mc.setZoom(16);
	    mapView.invalidate();
	    
	    List<Overlay> mapOverlays = mapView.getOverlays();
	    Drawable drawable = this.getResources().getDrawable(R.drawable.mappointer);
	    AddItemizedOverlay itemizedOverlay =  new AddItemizedOverlay(drawable, this);
	     
	    OverlayItem overlayitem = new OverlayItem(geoPoint, "Map", titleTxt);
	     
	    itemizedOverlay.addOverlay(overlayitem);
	    mapOverlays.add(itemizedOverlay);
	}
	
	public void closeWin(View v){
		finish();
	}
}
