package com.nicklase.bilteori.gui;
import java.util.ArrayList;

import org.w3c.dom.Document;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.AdapterView.OnItemSelectedListener;

import com.nicklase.bilteori.logic.Constant;
import com.nicklase.bilteori.logic.GMapV2Direction;
import com.google.android.gms.location.LocationClient;
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
import com.google.android.gms.maps.model.PolylineOptions;
import com.nicklase.bilteori.R;
import com.nicklase.bilteori.logic.TrafficStation;

public class MapActivity extends FragmentActivity  implements OnMapLongClickListener{
	private LatLng myPosition = new LatLng(0,0);
	private ArrayList<TrafficStation> trafficStations = new ArrayList<TrafficStation>();
	private GMapV2Direction mapDirection;
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
	map.setMyLocationEnabled(true);
	addAllStation();
	if(!trafficStations.isEmpty()){
		setUpMarks();
	}
}
@Override
protected void onPostCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onPostCreate(savedInstanceState);
	findAdjacentStations();
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
private void findAdjacentStations(){
//	LatLng myLoc = new LatLng(map.getMyLocation().getLatitude(),map.getMyLocation().getLongitude());
//	
//	map.addPolyline(new PolylineOptions().geodesic(true).add(trafficStations.get(0).getLatlng()).add(myLoc));
	//new drawRoute().execute();
}
private class drawRoute extends AsyncTask<Void, Void, Document> {
	Document doc;
	PolylineOptions rectLine;

	@Override 
	protected Document doInBackground(Void... params) {
		doc = mapDirection.getDocument(myPosition, Constant.HAFSLUND, GMapV2Direction.MODE_DRIVING);

		ArrayList<LatLng> directionPoint = mapDirection.getDirection(doc);
		rectLine = new PolylineOptions().width(3).color(Color.BLUE);

		for (int i = 0; i < directionPoint.size(); i++) {
			rectLine.add(directionPoint.get(i));
		}

		return null;
	}

	@Override
	protected void onPostExecute(Document result) {
		map.addPolyline(rectLine);
	}
}
/// <summary>
///   The method which invokes addStation.
/// </summary>
private void addAllStation(){
	addStation("Hafslund trafikkstasjon",Constant.HAFSLUND,"Teoriprøver drop-in"+"\n"+"Mandag: 09:00–13:00 Tirsdag-fredag: 08:00–13:00");
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
} 