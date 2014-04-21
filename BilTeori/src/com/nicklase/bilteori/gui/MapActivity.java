package com.nicklase.bilteori.gui;
import java.util.ArrayList;

import org.w3c.dom.Document;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import com.nicklase.bilteori.logic.Constant;
import com.nicklase.bilteori.logic.GMapV2Direction;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.LocationSource.OnLocationChangedListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.nicklase.bilteori.R;
import com.nicklase.bilteori.logic.TrafficStation;

public class MapActivity extends FragmentActivity  implements OnMapLongClickListener, ConnectionCallbacks, OnConnectionFailedListener{
	private LatLng fromPosition;
	private LatLng toPosition =Constant.HAFSLUND;;
	private Document document;
	private ArrayList<TrafficStation> trafficStations = new ArrayList<TrafficStation>();
	private GMapV2Direction mapDirection;
	private GoogleMap map;
	private LocationClient myLocation =null;
  
/// <summary>
///   Method run on create.
/// </summary>
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_map);
    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
	map = mapFragment.getMap();
	map.setMyLocationEnabled(true);
	map.getUiSettings().setZoomControlsEnabled(true);
	myLocation = new LocationClient(this, this, this);
	setUp();
	if (savedInstanceState == null) {
		 map.moveCamera(CameraUpdateFactory.newLatLngZoom(Constant.HAFSLUND, 5));
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
	addAllStation();
	if(!trafficStations.isEmpty()){
		setUpMarks();
	}
}
@Override
protected void onPostCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onPostCreate(savedInstanceState);
	myLocation.connect();
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
@Override
protected void onResume() {
	super.onResume();

	myLocation.connect();
}

@Override
protected void onPause() {
	super.onPause();

	myLocation.disconnect();
}
/// <summary>
///   Adds one station to the list of trafficStations.
/// </summary>
private void addStation(String stationName, LatLng latlng,String info){
	TrafficStation t = new TrafficStation(stationName,latlng, info);
	trafficStations.add(t);
}
private void findAdjacentStations(){
	
	
	addPolyLine(fromPosition,toPosition);
}
private void addPolyLine(LatLng fPostion,LatLng tPosition){
	map.addPolyline(new PolylineOptions().geodesic(true).add(fPostion).add(tPosition));
}

/// <summary>
///   The method which invokes addStation.
/// </summary>
private void addAllStation(){
	addStation("Hafslund trafikkstasjon",Constant.HAFSLUND,Html.fromHtml("<p><b>Teoriprøver drop-in</b></p> <br /> Mandag: 09:00–13:00 Tirsdag-fredag: 08:00–13:00").toString());
	addStation("Mysen trafikkstasjon",Constant.MYSEN,"Teoriprøver drop-in "+ "\n" +"Tirsdag, onsdag og  torsdag 08:00-13:00");
	addStation("Drøbak trafikkstasjon",Constant.DROBAK,"Teoriprøver drop in Mandag: 0900–1300 Tirsdag–fredag: 0800–1300");
	addStation("Kongsvinger trafikkstasjon",Constant.KONGSVINGER,"Teoriprøver drop in Mandag: 09:00–13:00 Tirsdag–fredag: 08:00–13:00");
	addStation("Jessheim trafikkstasjon",Constant.JESSHEIM,"Teoriprøver drop in ukjent.");
}
/// <summary>
///   Adds one mark to the map.
/// </summary>
private void addMark(LatLng position,String title,String text){
	this.map.addMarker(new MarkerOptions().position(position).title(title)
			.snippet(text).alpha(0.7f));
}


@Override
public void onConnected(Bundle arg0) {
	// TODO Auto-generated method stub
	
	Location loc = myLocation.getLastLocation();
	fromPosition =new LatLng(loc.getLatitude(), loc.getLongitude());
	findAdjacentStations();
}

@Override
public void onConnectionFailed(ConnectionResult arg0) {
	Toast.makeText(this, "Connection Failed", Toast.LENGTH_LONG).show();
	
}
@Override
public void onDisconnected() {
	Toast.makeText(this, "Disconnected", Toast.LENGTH_LONG).show();
}
private void settings(){
	 Intent intent = new Intent(com.nicklase.bilteori.gui.MapActivity.this, com.nicklase.bilteori.gui.SettingsActivity.class);
	 startActivity(intent);
}

private void startTeori(){
	 Intent intent = new Intent(com.nicklase.bilteori.gui.MapActivity.this, com.nicklase.bilteori.gui.ExamOneActivity.class);
	 startActivity(intent);
}

//Fyller actionbar i layout
@Override
public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.main, menu);
	return true;
}


//Hva som skjer når du velger settings på actionbar
@Override
public boolean onOptionsItemSelected(MenuItem item){
	
	switch (item.getItemId()){
	case R.id.action_settings:
		settings();
		break;
		
	case R.id.action_examOne:
		startTeori();
		break;


	}
	return false;
	//super.onOptionsItemSelected(item);
	
}

} 