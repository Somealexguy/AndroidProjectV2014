package com.nicklase.bilteori.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.os.Environment;
import android.os.Build.VERSION;

//not in use yet
public class MyFileReader {

	FileWriter errorWriter = new FileWriter("error");
		
	
		/// <summary>
		///   Reads the result of an exam walkthrough.
		/// </summary>
		// not in use yet.
		public String readStatistics(){
			String filename=Constant.STATISTICS_FILENAME;
			File dirPath=null;
			String theText="";
			try {
				if(VERSION.SDK_INT>=18){
					dirPath=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+ "/bilteori/",filename);
				 }else{
						dirPath=new File("\\Download\\bilteori",filename);
					}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				errorWriter.saveDataToFile(e.toString(), null);
			}
			
			try {
				
				if(!(dirPath.exists() && dirPath.isDirectory() && isExternalStorageReadable())){
					FileReader reader= new FileReader(dirPath);
					//Read text from file
					StringBuilder text = new StringBuilder();

					    BufferedReader br = new BufferedReader(reader);
					    String line;
					    while ((line = br.readLine()) != null) {
							text.append(line);
					}
					   theText=text.toString();
					    br.close();
				}
				}catch(Exception e){
					e.printStackTrace();
					errorWriter.saveDataToFile(e.toString(), null);
				}
				
			return theText;
		}
		/// <summary>
		///   Deletes the file.
		/// </summary>
		public void deleteStatistics(){
			String filename=Constant.STATISTICS_FILENAME;
			File dirPath=null;
			try {
				if(VERSION.SDK_INT>=18){
					dirPath=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+ "/bilteori/",filename);
				 }else{
						dirPath=new File("\\Download\\bilteori",filename);
					}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				errorWriter.saveDataToFile(e.toString(), null);
			}
			
			try{
				dirPath.delete();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				errorWriter.saveDataToFile(e.toString(), null);
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

	