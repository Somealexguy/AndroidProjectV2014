package com.nicklase.bilteori.logic;

import java.io.File;

import android.os.Environment;

//not in use yet
public class FileReader {

	
		
	
		/// <summary>
		///   Reads the result of an exam walkthrough.
		/// </summary>
		// not in use yet.
		public void readStatistics(){
			String path="stats.txt";
			File file= new File(path);
			try {
				
			} catch (Exception e) {
				// TODO: handle exception
			}
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

	