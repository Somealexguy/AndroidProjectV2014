package com.nicklase.bilteori.gui;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.w3c.dom.Document;

import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import com.nicklase.bilteori.logic.Constant;
import com.nicklase.bilteori.logic.FileWriter;
import com.nicklase.bilteori.logic.GMapV2Direction;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
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
	private static final int GPS_ERRORDIALOG_REQUEST = 0;
	private LatLng fromPosition;
	private LatLng toPosition;
	private ArrayList<TrafficStation> trafficStations = new ArrayList<TrafficStation>();
	private GMapV2Direction mapDirection;
	private GoogleMap map;
	private Location location=null;
	private LocationClient myLocation =null;
	private float[] meterResult= new float[3];
	private FileWriter errorWriter= new FileWriter(Constant.WRITE_ERROR);
	private  boolean leftActivity = false;
	private LocationManager locationManager;
	//	private boolean setUpOk;

	/// <summary>
	///   Method run on create.
	/// </summary>
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		initialize();
	}
	private void initialize() {
		if(checkNetworkConnection()) {
			if(checkGPSService()){
				setUpGoogleMaps();
			}else{
				Toast.makeText(getApplicationContext(), "Sjekk om gps (Google play service) er på", Toast.LENGTH_LONG).show();
			}
		}else{
			Toast.makeText(getApplicationContext(), "Sjekk om du er tilkoblet internett", Toast.LENGTH_LONG).show();
		}
	}
	/// <summary>
	///   Checks if the phone is connected to network.
	/// </summary>
	private boolean checkNetworkConnection(){
		boolean checkIfWifiConnected = false;
		boolean checkIfMobileNetworkConnected = false;
		boolean connection=false;
		//Class that answers queries about the state of network connectivity. It also notifies applications when network connectivity changes.
		ConnectivityManager connectionManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		//Array that contains connection status information about all network types supported by the device.
		NetworkInfo[] networkInformation = connectionManager.getAllNetworkInfo();
		if ( networkInformation != null )
		{
			for (int i = 0; i < networkInformation.length; i++) {
				//if wifi is on then break out
				if (networkInformation[i].getType()== ConnectivityManager.TYPE_WIFI)
					if (networkInformation[i].isConnectedOrConnecting()){
						checkIfWifiConnected = true;
						break;
					}
				//if mobile network is on break out
				if (networkInformation[i].getType() == ConnectivityManager.TYPE_MOBILE)
					if (networkInformation[i].isConnectedOrConnecting()){
						checkIfMobileNetworkConnected = true;
						break;
					}
			}
		}

		if(checkIfMobileNetworkConnected||checkIfWifiConnected){
			connection=true;
		}

		return connection;
	}
	/// <summary>
	///   Checks if GPS is on and up to date.
	/// </summary>
	private boolean checkGPSService() {
		int checkIfGooglePlayIsAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
		if (checkIfGooglePlayIsAvailable == ConnectionResult.SUCCESS) {
			locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
			if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
				if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){	
			        	return true;
				}
			}

		} else if (GooglePlayServicesUtil.isUserRecoverableError(checkIfGooglePlayIsAvailable)) {
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(checkIfGooglePlayIsAvailable, this, GPS_ERRORDIALOG_REQUEST);
			dialog.show();

		} else {
			Toast.makeText(this, "Får ikke koblet til google play service!", Toast.LENGTH_SHORT).show();

		}
		return false;
	}
	/// <summary>
	///   Returns true if checkNetworkConnection() and checkGPSService() returns true .
	/// </summary>
	private boolean checkConnectionStatus(){
		if(checkNetworkConnection() && checkGPSService()){
			return true;
		}else{
			return false;
		}
	}
	@Override
	public void onMapLongClick(LatLng arg0) {
		// TODO Auto-generated method stub

	}
	/// <summary>
	///   Sets up the google map.
	/// </summary>
	private void setUpGoogleMaps(){
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

		map = mapFragment.getMap();
		mapDirection = new GMapV2Direction();
		map.setMyLocationEnabled(true);
		map.getUiSettings().setZoomControlsEnabled(true);
		myLocation = new LocationClient(this, this, this);
		map.setOnMapLongClickListener(this);
		setUp();

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
	/// <summary>
	///   After create method is run, this is run.
	/// </summary>
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		if(checkConnectionStatus()){
			try {
				myLocation.connect();
			} catch (Exception e) {
				// TODO: handle exception
				errorWriter.saveDataToFile(e.toString(), getApplicationContext());
			}
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
	///   When you resume this activity.
	/// </summary>
	@Override
	protected void onResume() {
		super.onResume();
		if(checkConnectionStatus()){
					if(!myLocation.isConnected() && leftActivity){
						try {
							myLocation.connect();	
						} catch (Exception e) {
							// TODO: handle exception
							errorWriter.saveDataToFile(e.toString(), getApplicationContext());
						}
					}
				}else{
					initialize();
				}
	}
	/// <summary>
	///   When you pause this activity.
	/// </summary>
	@Override
	protected void onPause() {
		super.onPause();
		if(checkConnectionStatus()){
			if(myLocation.isConnected()){
				myLocation.disconnect();
				leftActivity=true;
			}
		}
	}
	//<summary>
	//Run when activity is closed.
	//</summary>
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		if(checkConnectionStatus()){
			if(myLocation.isConnected()){
				myLocation.disconnect();
			}
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
	///   Finds the adjacent stations.
	/// </summary>
	private void findAdjacentStations(){
		getDistanceInMeters();
		toPosition=findTheClosestStation().getLatlng();
		if(fromPosition !=null && toPosition !=null){
			//	addPolyLine(fromPosition,toPosition);
			new drawRoute().execute();
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(toPosition, 5));
			// Zoom in, animating the camera.
			map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
		}
	}
	/// <summary>
	///   Finds the distance between you and the traffic station in meters.
	/// </summary>
	private void getDistanceInMeters(){
		location = myLocation.getLastLocation();
		for (TrafficStation station : trafficStations) {
			//A method that returns the distance in meters, and will return the bearing.
			android.location.Location.distanceBetween(location.getLatitude(), location.getLongitude(), station.getLat(), station.getLon(), meterResult);
			station.setDistanceTo(meterResult[0]);
			station.setBearing(meterResult[2]);
		}	
	}
	/// <summary>
	///   Finds the closest traffic station.
	/// </summary>
	private TrafficStation findTheClosestStation(){
		TrafficStation theStation=null;
		float theShortestDistance=0;
		for (TrafficStation station : trafficStations) {
			if(theShortestDistance==0){
				theShortestDistance=station.getDistanceTo();
				theStation=station;
			}
			if(theShortestDistance>station.getDistanceTo()){
				theShortestDistance=station.getDistanceTo();
				theStation=station;
			}
		}
		return theStation;
	}
	/// <summary>
	///  Adds a line on the map.
	/// </summary>
	private void addPolyLine(LatLng fPostion,LatLng tPosition){
		map.addPolyline(new PolylineOptions().geodesic(true).add(fPostion).add(tPosition));
	}

	/// <summary>
	///   The method which invokes addStation.
	/// </summary>
	private void addAllStation(){
		// comment out one station if you want to check which one is closest when you remove one.
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

	/// <summary>
	///   When connected do this.
	/// </summary>
	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		if(checkConnectionStatus()){
			location = myLocation.getLastLocation();
			fromPosition =new LatLng(location.getLatitude(), location.getLongitude());
			findAdjacentStations();
		}
	}
	/// <summary>
	///   If no connection do this.
	/// </summary>
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		Toast.makeText(this, "Connection Failed", Toast.LENGTH_LONG).show();

	}
	/// <summary>
	///   When disconnected do this.
	/// </summary>
	@Override
	public void onDisconnected() {
		Toast.makeText(this, "Disconnected", Toast.LENGTH_LONG).show();
	}
	/// <summary>
	///   Starts the settings activity.
	/// </summary>
	private void changeToSettingsActivity(){
		Intent intent = new Intent(com.nicklase.bilteori.gui.MapActivity.this, com.nicklase.bilteori.gui.SettingsActivity.class);
		startActivity(intent);
	}
	/// <summary>
	///   Starts the exam activity.
	/// </summary>
	private void changeToExamActivity(){
		Intent intent = new Intent(com.nicklase.bilteori.gui.MapActivity.this, com.nicklase.bilteori.gui.ExamOneActivity.class);
		startActivity(intent);
	}
	/// <summary>
	///   Inflates the action bar in the layout.
	/// </summary>
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	/// <summary>
	///   Draws the driving route between the fromposition to the toposition.
	/// </summary>
	private class drawRoute extends AsyncTask<Void, Void, Document> {
		Document doc;
		PolylineOptions rectLine;
		/// <summary>
		///   Code run in background out of UI thread.
		/// </summary>
		@Override 
		protected Document doInBackground(Void... params) {
			doc = mapDirection.getDocument(fromPosition, toPosition, GMapV2Direction.MODE_DRIVING);

			ArrayList<LatLng> directionPoint = mapDirection.getDirection(doc);
			rectLine = new PolylineOptions().width(3).color(Color.BLUE);

			for (int i = 0; i < directionPoint.size(); i++) {
				rectLine.add(directionPoint.get(i));
			}

			return null;
		}
		/// <summary>
		///  When background job is finished.
		/// </summary>
		@Override
		protected void onPostExecute(Document result) {
			map.addPolyline(rectLine);
		}
	}
	/// <summary>
	///   When a menu item is clicked run some code.
	/// </summary>
	@Override
	public boolean onOptionsItemSelected(MenuItem item){

		switch (item.getItemId()){
		case R.id.action_settings:
			changeToSettingsActivity();
			break;

		case R.id.action_examOne:
			changeToExamActivity();
			break;


		}
		return false;

	}

} 