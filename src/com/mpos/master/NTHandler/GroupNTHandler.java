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
import com.mpos.master.model.PRDCT_GRP_MST_Model;

/**
 * Description-
 * 

 */

public class GroupNTHandler extends NTHandler {

	public static final String TAG = Constants.APP_TAG
			+ GroupNTHandler.class.getSimpleName();

	protected Bundle bundle = new Bundle();

	private final String GRP_NAME = "grpName";
	private final String GRP_CODE = "grpCode";

	public GroupNTHandler(HandlerInf handlerInf, List<NameValuePair> params,
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

		ArrayList<PRDCT_GRP_MST_Model> dataArr;
		
		try {
			JSONArray jArr = new JSONArray(response);
			dataArr = getProductGroupArr(jArr);
			bundle.putSerializable(Constants.RESULTCODEBEAN, dataArr);
			mOperationalResult.setResult(bundle);
		} catch (JSONException e) {
			e.printStackTrace();
			//Logger.w(TAG, e.toString());
			//Log.d(TAG, "## pulsar ## GRPNTPOS_MSNT_HANDLER");
			mOperationalResult.setApiName("Group");
			mOperationalResult.setRequestResponseCode(OperationalResult.DATA_PROTOCOL_ERROR);
			//mOperationalResult.setRequestResponseCode(OperationalResult.ERROR);
		}

	}

	/***
	 * Method- To create PRDCT_GRP models and fill it in array
	 * 
	 * @param jArr
	 * @return
	 * @throws JSONException
	 */
	private ArrayList<PRDCT_GRP_MST_Model> getProductGroupArr(JSONArray jArr)
			throws JSONException {
		ArrayList<PRDCT_GRP_MST_Model> grpArr = new ArrayList<PRDCT_GRP_MST_Model>();

		for (int i = 0; i < jArr.length(); i++) {
			PRDCT_GRP_MST_Model model = new PRDCT_GRP_MST_Model();
			JSONObject jObj = jArr.getJSONObject(i);

			model.setGrp_name(jObj.optString(GRP_NAME).trim());
			model.setGrp_code(jObj.optString(GRP_CODE).trim());
			grpArr.add(model);
		}
		return grpArr;
	}

}
