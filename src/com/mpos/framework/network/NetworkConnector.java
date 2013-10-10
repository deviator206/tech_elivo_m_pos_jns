/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * USAP PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mpos.framework.network;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.mpos.framework.Util.Util;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.handler.ActivityHandler;

/**
 * Description- This class is responsible to acquire web service response
 * 
 * 
 */
public class NetworkConnector {

	public static final String TAG = Constants.APP_TAG
			+ NetworkConnector.class.getSimpleName();

	private HttpClient client = null;
	
	
	/**
	 * Get request to Web service.
	 * 
	 * @param defaultURL
	 *            web service URL
	 * @param requeststring
	 *            request string containing request parameters
	 * @param opResult
	 *            to store network error.
	 * @return
	 */
	public String sendGetRequest(ActivityHandler appHandler,
			String defaultURL, String requeststring,
			OperationalResult opResult, boolean isSecured) {

		String urlString = defaultURL + requeststring;
		
		Logger.i(TAG, "FinalUrl:" + urlString);

		InputStream is = null;
		String inputSource = null;
		
	
		HttpGet request = null;
		request = new HttpGet(urlString);

		 client = new DefaultHttpClient();

		HttpResponse httpResponse;

		try {
			httpResponse = client.execute(request);
			HttpEntity entity = httpResponse.getEntity();

			if (entity != null) {
				is = entity.getContent();
				inputSource = Util.convertStreamToString(is);
				// Closing the input stream will trigger connection release
				is.close();
				Logger.d(TAG, "ResponseData:" + inputSource.toString());
			}
		} catch (ClientProtocolException e) {
			opResult.setOperationalCode(OperationalResult.ERROR);
			Logger.w(TAG, e.toString());
		} catch (IOException e) {
			opResult.setOperationalCode(OperationalResult.ERROR);
			Logger.w(TAG, e.toString());
		}

		return inputSource;
	}

	/**
	 * Method- Disconnect the network connection
	 */
	public void disConnectHttpConnection() {
		if (client != null) {
			client.getConnectionManager().shutdown();
		}
	}
}
