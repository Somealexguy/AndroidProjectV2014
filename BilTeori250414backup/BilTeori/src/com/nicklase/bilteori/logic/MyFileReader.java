package com.nicklase.bilteori.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;
import android.os.Build.VERSION;
import android.widget.Toast;

//not in use yet
public class MyFileReader {
	private Context context;

	public MyFileReader(Context context) {
		this.context = context;
	}

	private FileWriter errorWriter = new FileWriter("error");

	// / <summary>
	// / Reads the result of an exam walkthrough.
	// / </summary>
	// not in use yet.
	public String readStatistics() {
		String filename = Constant.STATISTICS_FILENAME;
		File dirPath = null;
		String theText = "";
		try {
			if (checkIfExternalStorageReadable()) {
				dirPath = new File(Environment
						.getExternalStoragePublicDirectory(
								Environment.DIRECTORY_DOWNLOADS).getPath()
								+ File.separator + "bilteori" + File.separator,
								filename);
			} else {
				dirPath = new File(context.getFilesDir().getAbsolutePath()
						+ File.separator + "bilteori" + File.separator,
						filename);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			errorWriter.saveDataToFile(e.toString(), context);
		}

		try {

			if (!(dirPath.exists() && dirPath.isDirectory())) {
				FileReader reader = new FileReader(dirPath);
				// Read text from file
				StringBuilder text = new StringBuilder();

				BufferedReader br = new BufferedReader(reader);
				String line;
				while ((line = br.readLine()) != null) {
					text.append(line);
				}
				theText = text.toString();
				br.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorWriter.saveDataToFile(e.toString(), context);
		}

		return theText;
	}

	// / <summary>
	// / Deletes the file.
	// / </summary>
	public String deleteStatistics() {
		String filename = Constant.STATISTICS_FILENAME;
		String status = "";
		File dirPath = null;
		try {
			if (checkIfExternalStorageReadable()) {
				dirPath = new File(Environment
						.getExternalStoragePublicDirectory(
								Environment.DIRECTORY_DOWNLOADS).getPath()
								+ File.separator + "bilteori" + File.separator,
								filename);
			} else {
				dirPath = new File(context.getFilesDir().getAbsolutePath()
						+ File.separator + "bilteori" + File.separator,
						filename);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			errorWriter.saveDataToFile(e.toString(), context);
		}

		try {
			dirPath.delete();
			status = "ok";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			errorWriter.saveDataToFile(e.toString(), context);
		}
		return status;
	}

	// / <summary>
	// / Checks if external storage is available to at least read
	// / </summary>
	private boolean checkIfExternalStorageReadable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)
				|| Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return true;
		}
		return false;
	}
}
