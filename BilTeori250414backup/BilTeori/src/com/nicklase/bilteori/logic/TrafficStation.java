package com.nicklase.bilteori.logic;

import com.google.android.gms.maps.model.LatLng;

public class TrafficStation {
	private String stationName;
	private LatLng latlng;
	private double lat;
	private double lon;
	private String info;
	private float distanceTo;
	private float bearing;
	/// <summary>
	///  Constructor fir TrafficStation class.
	/// </summary>
	public TrafficStation(String stationName,LatLng latlng,
			String info) {
		super();
		this.lat=latlng.latitude;
		this.lon=latlng.longitude;
		this.latlng=latlng;
		this.stationName = stationName;
		this.info = info;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public LatLng getLatlng() {
		return latlng;
	}
	public void setLatlng(LatLng latlng) {
		this.latlng = latlng;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public float getDistanceTo() {
		return distanceTo;
	}
	public void setDistanceTo(float distanceTo) {
		this.distanceTo = distanceTo;
	}

	public float getBearing() {
		return bearing;
	}
	public void setBearing(float bearing) {
		this.bearing = bearing;
	}
	
	
}
