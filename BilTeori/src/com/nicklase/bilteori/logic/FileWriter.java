package com.nicklase.bilteori.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileOutputStream;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class FileWriter {
	private final String cmd;
	
	//konstruktør
	public FileWriter(String cmd) {
		this.cmd = cmd;
	}
	
	public void saveDataToFile(String text, Context context){
		switch(cmd){
		case("writeError"):
			writeError(text, context );
			break;
		case("writeStatistics"):
			writeStatistics(text, context);
			break;
	}
}
	
	/// <summary>
	///   Writes an error to the error log file..
	/// </summary>
	public void writeError(String text, Context context){
		String filename="error.txt";
		String path=null;
		if(isExternalStorageWritable()){
			path="/sdcard/";
		}
		
		String dirPath=path+filename;
		
		File file= new File(dirPath);
		
		FileOutputStream writer = null;

		try {
			if (!file.exists()) {
				   file.createNewFile();
				   writer=context.openFileOutput(file.getName(), Context.MODE_PRIVATE);
				}else{
					writer=context.openFileOutput(file.getName(), Context.MODE_APPEND);
				}
				
			writer.write(text.getBytes());
			writer.flush();
			 writer.close();
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
		
		Log.w("myApp", "Du skrev error til fil.");
		
		    
		   
}
	/// <summary>
	///   Writes a exam result to a .txt file.
	/// </summary>
	// not in use yet.
	public void writeStatistics(String text, Context context){
		String filename="stat.txt";
		String path=null;
		if(isExternalStorageWritable()){
			path="/sdcard/";
		}
		
		String dirPath=path+filename;
		
		File file= new File(dirPath);
		
		FileOutputStream writer = null;

		try {
			if (!file.exists()) {
				   file.createNewFile();
				   writer=context.openFileOutput(file.getName(), Context.MODE_PRIVATE);
				}else{
					writer=context.openFileOutput(file.getName(), Context.MODE_APPEND);
				}
				
			writer.write(text.getBytes());
			writer.flush();
			 writer.close();
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
		
		Log.w("myApp", "Du skrev stat til fil.");	    
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
