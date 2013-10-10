/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * USAP PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mpos.framework.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mpos.framework.common.Logger;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemClock;
import android.telephony.TelephonyManager;

/**
 * Description- This is singleton class. which is responsible to provide all
 * utility methods.
 * 
 * @author vinayakh
 * 
 */
public class Util {

	public static final String TAG = "Util";

	private static Util mUtil;
	public static final String szNewLineCharacter = "\n";

	private Util() {

	}

	public static Util getInstance() {
		if (mUtil == null) {
			synchronized (Util.class) {
				if (mUtil == null) {
					mUtil = new Util();
				}
			}
		}

		return mUtil;
	}

	public static String encodeUrl(String data) {
		data = data.replace(" ", "%20");
		data = data.replace("\"", "%22");
		data = data.replace("{", "%7B");
		data = data.replace("}", "%7D");
		return data;
	}

	public static boolean emailValidation(final String username, Context context) {
		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(username);
		boolean matchFound = matcher.matches();

		if (matchFound) {
			return true;
		} else {
			return false;
		}
	}

	public static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static String getIMEICode(Context context) {
		String imei;
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		imei = tm.getDeviceId();
		return imei;
	}

	/**
	 * This method return mobile running time since device reboot in minutes.
	 * 
	 * @return
	 */
	public String getRunningTime() {
		return String.valueOf((SystemClock.elapsedRealtime()) / (1000 * 60)); // time
																				// in
																				// minutes.
	}

	/**
	 * This function copies input stream data to output stream data.
	 * 
	 * @param is
	 *            - input sream
	 * @param os
	 *            - uptout stream
	 */
	public static void copyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	public static boolean isEmailValid(String email) {
		boolean isValid = false;
		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	public static boolean isValidUsername(String username) {
		boolean isValid = false;
		String expression = "^(?=.*[a-z])\\w{1,100}\\s*$";
		CharSequence inputStr = username;
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	/**
	 * This function checks Network availability.
	 * 
	 * @param context
	 *            Application context
	 * @return
	 */
	public static boolean isNetworkConnectionAvailable(Context context) {
		boolean connected = false;
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo mobile_info = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifi_info = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if ((mobile_info != null && mobile_info.getState() == NetworkInfo.State.CONNECTED)
				|| wifi_info != null
				&& wifi_info.getState() == NetworkInfo.State.CONNECTED) {
			connected = true;
		} else {
			connected = false;
		}
		return connected;
	}

	public static void writeToSdCard(String path, String message) {
		try {
			File myFile = new File(path);
			if(!myFile.exists()){
				Logger.d(TAG, "File Created");
				myFile.createNewFile();
			}
			FileOutputStream fOut = new FileOutputStream(myFile);
			OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
			myOutWriter.append(message);
			myOutWriter.close();
			fOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}