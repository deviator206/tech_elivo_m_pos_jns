/*
 * Copyright (C) 2013  All Rights Reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mpos.application;

import java.util.HashMap;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.mpos.BuildConfig;
import com.mpos.framework.common.Config;
import com.mpos.framework.common.Logger;
import com.mpos.framework.inf.NetworkChangeListener;

/**
 * Description-
 * 
 */
public class MPOSApplication extends Application {

	public static Context stContext;
	public static final String TAG = MPOSApplication.class.getSimpleName();
	public static final int STRICT_MODE_API_LIMIT = 8;
	public static final String WIFI_MAC_ADDRESS = "MAC_ADDRE";
	public static final String SESSTION_TIME_OUT = "SESSTION_TIME_OUT";
	public static final String ORIENTATION = "ORIENTATION";
	public static final String BILL_POSITION = "BILL_POSITION";
	public static boolean isProcessingForMasterDb = false;
	public static HashMap<String, NetworkChangeListener> mImplementedNetworkLisContexts = null;
	public static BaseActivity baseActivity;
	public static Waiter waiter;
	public static Boolean bHeldTxnAvailable = false;
	

	@Override
	public void onCreate() {
		super.onCreate();
		Logger.setLogging(BuildConfig.DEBUG);
		Logger.setLoggerFileName(TAG);
		Logger.deleteOldFile();

		stContext = this.getApplicationContext();

		mImplementedNetworkLisContexts = new HashMap<String, NetworkChangeListener>();

		int SDK_INT = android.os.Build.VERSION.SDK_INT;
		if (SDK_INT > STRICT_MODE_API_LIMIT) {
			enableStrictMode();
		}

		WifiManager wifiManager = (WifiManager) this
				.getSystemService(Context.WIFI_SERVICE);

		String wifiAddress = "";
		if (wifiManager.isWifiEnabled()) {
			WifiInfo info = wifiManager.getConnectionInfo();
			wifiAddress = info.getMacAddress();
		} else {
			wifiManager.setWifiEnabled(true);
			WifiInfo info = wifiManager.getConnectionInfo();
			wifiAddress = info.getMacAddress();
			wifiManager.setWifiEnabled(false);
		}

		if (wifiAddress != null)
			wifiAddress = wifiAddress.replace(":", "");
		Logger.d(TAG, "WiFi ID: " + wifiAddress);

		// Store in shared pref
		SharedPreferences prefs = this.getSharedPreferences("com.mpos",
				Context.MODE_PRIVATE);
		Editor prefEditor = prefs.edit();
		//prefEditor.putString(WIFI_MAC_ADDRESS, "BC4760FEA232");
		prefEditor.putString(WIFI_MAC_ADDRESS, wifiAddress);
		Config.SESSION_TIMEOUT = prefs.getInt(SESSTION_TIME_OUT, 2);

		waiter = new Waiter(Config.SESSION_TIMEOUT * 60 * 1000); // 1 mins
		waiter.start();
		prefEditor.commit();
	}

	/**
	 * Method- StrictMode is used to catch accidental disk or network access on
	 * the application's main thread
	 */
	private void enableStrictMode() {
		// check whether in developer/debug mode
		/*
		 * if (Constants.DEVELOPER_MODE) { StrictMode.setThreadPolicy(new
		 * StrictMode.ThreadPolicy.Builder()
		 * .detectDiskReads().detectDiskWrites().detectNetwork() // or //
		 * .detectAll() // for // all // detectable // problems
		 * .penaltyLog().build()); StrictMode.setVmPolicy(new
		 * StrictMode.VmPolicy.Builder()
		 * .detectLeakedSqlLiteObjects().detectAll().penaltyLog()
		 * .penaltyDeath().build()); }
		 */
	}

	public static Context getContext() {
		return stContext;
	}

	public void addTONetworkListener(String key,
			NetworkChangeListener changeListener) {
		mImplementedNetworkLisContexts.put(key, changeListener);
	}

	public HashMap<String, NetworkChangeListener> getListeningClasses() {
		return mImplementedNetworkLisContexts;
	}

	public void touch() {
		waiter.touch();
	}

}
