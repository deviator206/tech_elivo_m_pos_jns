/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
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
import com.mpos.home.activity.HomeActivity;
import com.mpos.master.model.PRDCT_INSTRC_MST_Model;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class PRDCT_Instruction_NTHandler extends NTHandler {

	public static final String TAG = Constants.APP_TAG
			+ PRDCT_Instruction_NTHandler.class.getSimpleName();

	protected Bundle bundle = new Bundle();

	private final String PRDCT_CODE = "prdctCode";
	private final String INSTRU_CODE = "instrctnCode";

	public PRDCT_Instruction_NTHandler(HandlerInf handlerInf,
			List<NameValuePair> params, String url, int resultCode) {
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

		ArrayList<PRDCT_INSTRC_MST_Model> dataArr;

		try {
			JSONArray jArr = new JSONArray(response);
			dataArr = getPrdctInstruArr(jArr);
			bundle.putSerializable(Constants.RESULTCODEBEAN, dataArr);
			mOperationalResult.setResult(bundle);
		} catch (JSONException e) {
			// e.printStackTrace();
			Logger.w(TAG, e.toString());
			mOperationalResult.setApiName("Product Instruction");
			mOperationalResult
					.setRequestResponseCode(OperationalResult.DATA_PROTOCOL_ERROR);
		}

	}

	/***
	 * Method- To create PRDCT_GRP models and fill it in array
	 * 
	 * @param jArr
	 * @return
	 * @throws JSONException
	 */
	private ArrayList<PRDCT_INSTRC_MST_Model> getPrdctInstruArr(JSONArray jArr)
			throws JSONException {
		ArrayList<PRDCT_INSTRC_MST_Model> instrArr = new ArrayList<PRDCT_INSTRC_MST_Model>();

		for (int i = 0; i < jArr.length(); i++) {
			PRDCT_INSTRC_MST_Model model = new PRDCT_INSTRC_MST_Model();
			JSONObject jObj = jArr.getJSONObject(i);

			model.setPrdct_code(jObj.optString(PRDCT_CODE).trim());
			model.setInstrctn_code(jObj.optString(INSTRU_CODE).trim());
			instrArr.add(model);
		}
		return instrArr;
	}

}
