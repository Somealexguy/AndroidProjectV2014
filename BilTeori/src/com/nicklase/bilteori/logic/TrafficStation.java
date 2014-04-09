package com.nicklase.bilteori.logic;

import com.google.android.gms.maps.model.LatLng;

public class TrafficStation {
	private String stationName;
	private LatLng latlng;
	private String info;
	/// <summary>
	///  Constructor fir TrafficStation class.
	/// </summary>
	public TrafficStation(String stationName,LatLng latlng,
			String info) {
		super();
		this.latlng=latlng;
		this.stationName = stationName;
		this.info = info;
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
	
	
}
