/*
 * Copyright (C) 2013  All Rights Reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mpos.home.NThandler;

import java.util.List;

import org.apache.http.NameValuePair;

import android.os.Bundle;

import com.mpos.framework.common.Constants;
import com.mpos.framework.handler.NTHandler;
import com.mpos.framework.inf.HandlerInf;

/**
 * Description-
 * 

 */
public class LoginNTHandler extends NTHandler {
	
	public static final String TAG = Constants.APP_TAG
			+ LoginNTHandler.class.getSimpleName();
	
	protected Bundle bundle = new Bundle();

	public LoginNTHandler(HandlerInf handlerInf, List<NameValuePair> params,
			String url, int resultCode) {
		super(handlerInf, params, url, resultCode);
		// TODO Auto-generated constructor stub
		setParser();
	}

	@Override
	protected void setParser() {
		// set parser object to "this", means respective parser will be called
		mNTParser = this;
	}

	/**
	 * Method- To parse WS response data
	 */
	@Override
	protected void getParsedData(String responseData) {
		String response = responseData.toString();
	}

}
