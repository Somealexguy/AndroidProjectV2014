package com.nicklase.bilteori.logic;

public class Result {
	private static int id=0;
	private String points;
	private String time;
	/// <summary>
	///   Constructor for the result class.
	/// </summary>
	public Result(String points, String time){
		this.points=points;
		this.time=time;
		id++;
	}

	public int getId() {
		return id;
	}

	public String getPoints() {
		return points;
	}
	public String getTime() {
		return time;
	}
}
