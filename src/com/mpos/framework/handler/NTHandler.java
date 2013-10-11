/*
 * Copyright (C) 2013  All Rights Reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mpos.framework.handler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;

import android.util.Log;

import com.mpos.application.MPOSApplication;
import com.mpos.framework.Util.Util;
import com.mpos.framework.common.Config;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.NTOperationalParameter;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.inf.HandlerInf;
import com.mpos.framework.network.NetworkConnector;

/**
 * Description-
 * 

 */
public abstract class NTHandler extends ActivityHandler{

	public static final String TAG = Constants.APP_TAG
			+ NTHandler.class.getSimpleName();

	protected NetworkConnector networkConnector = null;
	protected String is;
	protected String mUrl = null;
	protected StringBuffer msbRequest = new StringBuffer();
	protected NTHandler mNTParser = null;
	protected List<NameValuePair> mhtMethodParam = null;
	protected NTOperationalParameter mNtOperationalParameter = new NTOperationalParameter();
	
	// Parser Class set
	abstract protected void setParser();
	// Parser Method	
	abstract protected void getParsedData(String response);
	
	public NTHandler(HandlerInf handlerInf, List<NameValuePair> params, String url, int resultCode) {
		super(handlerInf);
		NTOperationalParameter ntOperationalParameter = new NTOperationalParameter();
		mOperationalResult.setNTOperationalParam(ntOperationalParameter);
		this.mUrl = Config.HTTP + Config.DNS_NAME + url;
		mOperationalResult.getNTOperationalParam().setMhtUrlParameters(params);
		mhtMethodParam = mOperationalResult.getNTOperationalParam().getMhtUrlParameters();
		// Unique Constant Code
		mOperationalResult.setResultCode(resultCode); 
	}

	/**
	 * Forms request from method parameters.
	 */
	protected void addRequestParameter() {
		mhtMethodParam = mOperationalResult.getNTOperationalParam()
				.getMhtUrlParameters();

		if (mhtMethodParam != null && mhtMethodParam.size() > 0) {

			Iterator<NameValuePair> itrMethodParam1 = mhtMethodParam.iterator();
			
			String param = "";
			
			while (itrMethodParam1.hasNext()) {
				NameValuePair pair = itrMethodParam1.next(); //
				try {
					Logger.d(TAG,"pair value="+pair.getValue()+"\nname="+pair.getName());
					param += pair.getName() + "=" + URLEncoder.encode(pair.getValue(), "UTF-8") + "&";
					Logger.d(TAG,"params="+param);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			param = param.substring(0, param.length() - 1);
			
			// Form final URL string
			msbRequest.append(param.toString());
			Logger.d(TAG, "request:"+msbRequest);
		}

	}

	/**
	 * Method- Creates request URL -> Checks Network Availability -> Opens
	 * connection -> receive response of WS -> Calls respective parser method
	 */
	@Override
	protected Void doInBackground(Void... params) {

		return getResponse();
	}
	public Void getResponse() {
		addRequestParameter();

		/**
		 * Code to check network availability
		 */
		boolean isNetworkAvailable = true;

		isNetworkAvailable = Util
				.isNetworkConnectionAvailable(MPOSApplication.getContext());
		boolean hostAvailable=Util.isURLReachable("http://windows.technologicle.com");//Config.HTTP + Config.DNS_NAME);
		Log.d(TAG, "##PULSAR## ISREACHABLE RESPOSNE::"+hostAvailable);
		if (isNetworkAvailable && hostAvailable) {
			networkConnector = new NetworkConnector();
			is = networkConnector.sendGetRequest(this, mUrl,
					msbRequest.toString(), mOperationalResult, false);

			if (is == null) {
				Log.d(TAG, "##PULSAR## OPERATIONAL ERROR");
				mOperationalResult
						.setRequestResponseCode(OperationalResult.ERROR);
			} else {
				parseWsResult();
			}
		} else {
			Log.d(TAG, "##PULSAR## OPERATIONAL NETWORK ERROR");
			mOperationalResult
					.setRequestResponseCode(OperationalResult.NETWORK_ERROR);
		}
		
		return null;
	}

	/**
	 * Parse web service result.
	 */
	public void parseWsResult() {
		Logger.d(TAG, is.toString());
		mNTParser.getParsedData(is);
	}

	/**
	 * Method- To remove initial strings from error_code and convert it to
	 * integer and return.
	 * 
	 * @param String
	 *            errorCode
	 * @return integer erroCode
	 */
	public int convertErrorCode(String errorCode) {
		int length = errorCode.length();
		String mError;
		if (length <= 4) {
			mError = errorCode;
		} else {
			mError = errorCode.substring(length - 4);
		}
		int errorNumber;
		try {
			errorNumber = Integer.parseInt(mError);
		} catch (Exception e) {
			errorNumber = -1;
			Logger.w(TAG, e.toString());
		}
		return errorNumber;
	}

}
