package com.nicklase.bilteori.logic;

import java.io.File;

import android.os.Environment;

public class FileWriter {
	private String text;
	private String cmd;
	
	//konstruktør
	public FileWriter(String cmd) {
		this.cmd = cmd;
	}
	
	public void saveData(String text){
		this.text=text;
		switch(cmd){
		case("writeError"):
			writeError();
			break;
		case("writeStatistics"):
			writeStatistics();
			break;
	}
}
	
	/// <summary>
	///   Writes an error to the error log file..
	/// </summary>
	public void writeError(){
		String path="error.txt";
		File file= new File(path);
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
//		File file =// getFileStreamPath(path);
//
//		if (!file.exists()) {
//		   file.createNewFile();
//		}
//
//		FileOutputStream writer = openFileOutput(file.getName(), Context.MODE_PRIVATE);
//
//		for (String string: data){
//		    writer.write(string.getBytes());
//		    writer.flush();
//		}
//
//		writer.close();
	}
	/// <summary>
	///   Writes a exam result to a .txt file.
	/// </summary>
	// not in use yet.
	public void writeStatistics(){
		String path="stats.txt";
		File file= new File(path);
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	/// <summary>
	///   Checks if external storage is available for read and write.
	/// </summary>
	public boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}
	/// <summary>
	///   Checks if external storage is available to at least read 
	/// </summary>
	public boolean isExternalStorageReadable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state) ||
	        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        return true;
	    }
	    return false;
	}
}
