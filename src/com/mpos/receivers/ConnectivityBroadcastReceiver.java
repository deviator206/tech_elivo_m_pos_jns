package com.mpos.receivers;

import java.util.Iterator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.mpos.application.MPOSApplication;
import com.mpos.framework.Util.UserSingleton;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.Logger;
import com.mpos.framework.inf.NetworkChangeListener;

public class ConnectivityBroadcastReceiver extends BroadcastReceiver {

	private static final String TAG = Constants.APP_TAG
			+ ConnectivityBroadcastReceiver.class.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean isConnected = activeNetInfo != null
				&& activeNetInfo.isConnectedOrConnecting();
		final android.net.NetworkInfo wifi = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		final android.net.NetworkInfo mobile = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		//if (wifi != null && mobile != null) {
			if (wifi.isAvailable() || mobile.isAvailable()) {
				Logger.v(TAG, "connecte" + isConnected);
				UserSingleton.getInstance(context).isNetworkAvailable = true;
			} else {
				Logger.v(TAG, "not connecte" + isConnected);
				UserSingleton.getInstance(context).isNetworkAvailable = false;
			}
		//}

		if (MPOSApplication.mImplementedNetworkLisContexts != null
				&& MPOSApplication.mImplementedNetworkLisContexts.size() > 0) {

			Iterator<String> it = MPOSApplication.mImplementedNetworkLisContexts
					.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				NetworkChangeListener listener = (NetworkChangeListener) MPOSApplication.mImplementedNetworkLisContexts
						.get(key);
				listener.networkChangeListener(UserSingleton
						.getInstance(context).isNetworkAvailable);
			}
		}

	}
}
