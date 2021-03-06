package com.nicklase.bilteori.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileOutputStream;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Environment;
import android.util.Log;

public class FileWriter {
	private final String cmd;

	/// <summary>
	///   Constructor decides if its an statistics writer or error writer.
	/// </summary>
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
	private void writeError(String text, Context context){
		String filename="error.txt";
		File dirPath =null;

		if(checkIfExternalStorageWritable()){
			dirPath=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+ File.separator+"bilteori"+File.separator);
		}else{
			dirPath=new File(context.getFilesDir().getAbsolutePath()+ File.separator +"bilteori"+File.separator);
		}
		if (!(dirPath.exists() && dirPath.isDirectory())) {
			dirPath.mkdirs();
		}
		File file= new File(dirPath,filename);

		FileOutputStream writer = null;

		try {
			if (!file.exists()) {
				file.createNewFile();
				writer= new FileOutputStream(file);// fikk ikke denne til � fungerer context.openFileOutput(file.getName(), Context.MODE_PRIVATE);
			}else{
				writer=new FileOutputStream(file,true); //  fikk ikke denne til � fungerer  context.openFileOutput(filename, Context.MODE_APPEND);
			}

			writer.write(text.getBytes());
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.e("myApp",e.toString());
		}catch(IOException e){
			e.printStackTrace();
			Log.e("myApp",e.toString());
		}

		Log.w("myApp", "Du skrev error til fil.");	    

	}
	/// <summary>
	///   Writes a exam result to a .txt file.
	/// </summary>
	// not in use yet.
	private void writeStatistics(String text, Context context){
		String filename =Constant.STATISTICS_FILENAME;

		File dirPath =null;
		if(checkIfExternalStorageWritable()){
			dirPath=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+ File.separator+"bilteori"+File.separator);
		}else{
			dirPath=new File(context.getFilesDir().getAbsolutePath()+ File.separator+"bilteori"+File.separator);
		}
		if (!(dirPath.exists() && dirPath.isDirectory())) {
			dirPath.mkdirs();
		}
		File file= new File(dirPath,filename);

		FileOutputStream writer = null;

		try {
			if (!file.exists()) {
				file.createNewFile();
				writer= new FileOutputStream(file);
			}else{
				writer=new FileOutputStream(file,true);
			}

			writer.write(text.getBytes());
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.e("myApp",e.toString());
		}catch(IOException e){
			e.printStackTrace();
			Log.e("myApp",e.toString());
		}
		Log.w("myApp", "Du skrev stat til fil.");

	}
	/// <summary>
	///   Checks if external storage is available for read and write.
	/// </summary>
	private boolean checkIfExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}
}
