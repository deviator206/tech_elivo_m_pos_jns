/*
 * Copyright (C) 2013  All Rights Reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.master.NTHandler;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;

import com.mpos.framework.common.Constants;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.handler.NTHandler;
import com.mpos.framework.inf.HandlerInf;
import com.mpos.master.model.PRDCT_ANALYS_MST_Model;

/**
 * Description-
 * 

 */

public class AnalysisNTHandler extends NTHandler {

	public static final String TAG = Constants.APP_TAG
			+ AnalysisNTHandler.class.getSimpleName();

	protected Bundle bundle = new Bundle();

	private final String ANALYS_NAME = "anlysName";
	private final String ANALYS_CODE = "anlysCode";
	private final String DPT_CODE = "dptCode";

	public AnalysisNTHandler(HandlerInf handlerInf, List<NameValuePair> params,
			String url, int resultCode) {
		super(handlerInf, params, url, resultCode);
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

		ArrayList<PRDCT_ANALYS_MST_Model> dataArr;
		try {
			JSONArray jArr = new JSONArray(response);
			dataArr = getProductAnalysisArr(jArr);
			bundle.putSerializable(Constants.RESULTCODEBEAN, dataArr);
			mOperationalResult.setResult(bundle);
			/*
			 * } else { String errorCode = jObject.getString(JSON);
			 * mOperationalResult
			 * .setResponseErrorCode(convertErrorCode(errorCode)); }
			 */
		} catch (JSONException e) {
			e.printStackTrace();
			Logger.w(TAG, e.toString());
			//Log.d(TAG, "## pulsar ## ANALYSIS_NT_HANDLER");
			//mOperationalResult.setRequestResponseCode(OperationalResult.ERROR);
			mOperationalResult.setApiName("Analysis");
			mOperationalResult
					.setRequestResponseCode(OperationalResult.DATA_PROTOCOL_ERROR);
		}

	}

	/***
	 * Method- To create PRDCT_ANALYS models and fill it in array
	 * 
	 * @param jArr
	 * @return
	 * @throws JSONException
	 */
	private ArrayList<PRDCT_ANALYS_MST_Model> getProductAnalysisArr(
			JSONArray jArr) throws JSONException {
		ArrayList<PRDCT_ANALYS_MST_Model> grpArr = new ArrayList<PRDCT_ANALYS_MST_Model>();

		for (int i = 0; i < jArr.length(); i++) {
			PRDCT_ANALYS_MST_Model model = new PRDCT_ANALYS_MST_Model();
			JSONObject jObj = jArr.getJSONObject(i);

			model.setAnalys_name(jObj.optString(ANALYS_NAME).trim());
			model.setAnalys_code(jObj.optString(ANALYS_CODE).trim());
			model.setFk_dpt_code(jObj.optString(DPT_CODE).trim());
			grpArr.add(model);
		}
		return grpArr;
	}

}
