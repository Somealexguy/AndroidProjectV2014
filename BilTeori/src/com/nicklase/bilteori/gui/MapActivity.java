package com.nicklase.bilteori.gui;
import java.util.ArrayList;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.AdapterView.OnItemSelectedListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nicklase.bilteori.R;
import com.nicklase.bilteori.logic.TrafficStation;

public class MapActivity extends FragmentActivity  implements OnMapLongClickListener{
	private final LatLng HAFSLUND = new LatLng(59.262251, 11.126018);
	private final LatLng MYSEN = new LatLng(59.5553943, 11.3651344);
	private Location myPosition = null;
	private ArrayList<TrafficStation> trafficStations = new ArrayList<TrafficStation>();
	
  private GoogleMap map;
  
/// <summary>
///   Method run on create.
/// </summary>
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_map);
    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
	map = mapFragment.getMap();
	//myPosition= map.getMyLocation();
	
	setUp();
	if (savedInstanceState == null) {
		 map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAFSLUND, 5));
	}

    // Zoom in, animating the camera.
    map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    map.setOnMapLongClickListener(this);
  }

@Override
public void onMapLongClick(LatLng arg0) {
	// TODO Auto-generated method stub
	
}
/// <summary>
///   Sets up the required traffic station information.
/// </summary>
private void setUp(){
	map.setMyLocationEnabled(true);
	addAllStation();
	if(!trafficStations.isEmpty()){
		setUpMarks();
	}
}
/// <summary>
///   Adds the marks for each traffic station.
/// </summary>
//decided to call it setUpMarks because addMark() was taken.
private void setUpMarks(){
	for (TrafficStation station : trafficStations) {
		addMark(station.getLatlng(),station.getStationName(),station.getInfo());
	}
}
/// <summary>
///   Adds one station to the list of trafficStations.
/// </summary>
private void addStation(String stationName, LatLng latlng,String info){
	TrafficStation t = new TrafficStation(stationName,latlng, info);
	trafficStations.add(t);
}
/// <summary>
///   The method which invokes addStation.
/// </summary>
private void addAllStation(){
	addStation("Hafslund trafikkstasjon",HAFSLUND,"Teoriprøver drop-in"+"\n"+"Mandag: 09:00–13:00 Tirsdag-fredag: 08:00–13:00");
	addStation("Mysen trafikkstasjon",MYSEN,"Teoriprøver drop-in "+ "\n" +"Tirsdag, onsdag og  torsdag 08:00-13:00");
}
/// <summary>
///   Adds one mark to the map.
/// </summary>
private void addMark(LatLng position,String title,String text){
	this.map.addMarker(new MarkerOptions().position(position).title(title)
			.snippet(text).alpha(0.7f));
}
} 