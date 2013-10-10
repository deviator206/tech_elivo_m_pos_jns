/*
 * Copyright (C) 2013 jaggarnaut-studios Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
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
import com.mpos.master.model.UOM_MST_Model;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class UomNTHandler extends NTHandler {

	public static final String TAG = Constants.APP_TAG
			+ UomNTHandler.class.getSimpleName();

	private final String NAME = "name";
	private final String CODE = "code";
	private final String USR_NAME = "user_name";

	protected Bundle bundle = new Bundle();

	public UomNTHandler(HandlerInf handlerInf, List<NameValuePair> params,
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
		ArrayList<UOM_MST_Model> dataArr;
		try {
			JSONArray jArr = new JSONArray(response);
			dataArr = getUOMArr(jArr);
			bundle.putSerializable(Constants.RESULTCODEBEAN, dataArr);
			mOperationalResult.setResult(bundle);
		} catch (JSONException e) {
			e.printStackTrace();
			Logger.w(TAG, e.toString());
			//Log.d(TAG, "## pulsar ## UoM_NT_HANDLER");
			//mOperationalResult.setRequestResponseCode(OperationalResult.ERROR);
			
			mOperationalResult.setApiName("Uom");
			mOperationalResult.setRequestResponseCode(OperationalResult.DATA_PROTOCOL_ERROR);
		}
	}

	/**
	 * Method- To create UOM_MST models and fill it in array
	 * 
	 * @param jArr
	 * @return
	 * @throws JSONException
	 */
	private ArrayList<UOM_MST_Model> getUOMArr(JSONArray jArr)
			throws JSONException {
		ArrayList<UOM_MST_Model> uomArr = new ArrayList<UOM_MST_Model>();

		for (int i = 0; i < jArr.length(); i++) {
			UOM_MST_Model model = new UOM_MST_Model();
			JSONObject jObj = jArr.getJSONObject(i);

			model.setUom_name(jObj.optString(NAME).trim());
			model.setUom_code(jObj.optString(CODE).trim());
			model.setUom_user_name(jObj.optString(USR_NAME).trim());

			uomArr.add(model);
		}
		return uomArr;
	}

}
