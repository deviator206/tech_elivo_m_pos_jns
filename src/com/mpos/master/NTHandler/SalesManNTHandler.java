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
import com.mpos.master.model.SLMN_MST_Model;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class SalesManNTHandler extends NTHandler {

	public static final String TAG = Constants.APP_TAG
			+ SalesManNTHandler.class.getSimpleName();

	private final String NAME = "name";
	private final String ACTIVE = "active";
	private final String REGION_CODE = "regionCode";
	private final String BRANCH_CODE = "brnchCode";
	private final String SLMN_CODE = "smCode";
	private final String FROM_DT = "fromDt";
	private final String TO_DT = "toDt";
	private final String UPLOAD_FLG = "uploadFlg";
	private final String OLD_CODE = "oldCode";
	private final String TRUE = "true";
	private final String Y = "Y";

	protected Bundle bundle = new Bundle();

	public SalesManNTHandler(HandlerInf handlerInf, List<NameValuePair> params,
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
		ArrayList<SLMN_MST_Model> dataArr;
		try {
			JSONArray jArr = new JSONArray(response);
			dataArr = getSalesManArr(jArr);
			bundle.putSerializable(Constants.RESULTCODEBEAN, dataArr);
			mOperationalResult.setResult(bundle);
		} catch (JSONException e) {
			e.printStackTrace();
			Logger.w(TAG, e.toString());
			//Log.d(TAG, "## pulsar ## SALESMAN_NT_HANDLER");
			//mOperationalResult.setRequestResponseCode(OperationalResult.ERROR);
			mOperationalResult.setApiName("Sales Man");
			mOperationalResult.setRequestResponseCode(OperationalResult.DATA_PROTOCOL_ERROR);
		}
	}

	/**
	 * Method- To create SLMN_MST models and fill it in array
	 * 
	 * @param jArr
	 * @return
	 * @throws JSONException
	 */
	private ArrayList<SLMN_MST_Model> getSalesManArr(JSONArray jArr)
			throws JSONException {
		ArrayList<SLMN_MST_Model> slmnArr = new ArrayList<SLMN_MST_Model>();

		for (int i = 0; i < jArr.length(); i++) {
			SLMN_MST_Model model = new SLMN_MST_Model();
			JSONObject jObj = jArr.getJSONObject(i);

			model.setSlmn_name(jObj.optString(NAME).trim());
			if (jObj.optString(ACTIVE).equalsIgnoreCase(TRUE)) {
				model.setIs_slmn_active(true);
			} else {
				model.setIs_slmn_active(false);
			}
			model.setSlmn_region_code(jObj.optString(REGION_CODE).trim());
			model.setSlmn_branch_code(jObj.optString(BRANCH_CODE).trim());
			model.setSlmn_code(jObj.optString(SLMN_CODE).trim());
			model.setSlmn_from_dt(jObj.optString(FROM_DT).trim());
			model.setSlmn_to_dt(jObj.optString(TO_DT).trim());
			if (jObj.optString(UPLOAD_FLG).equalsIgnoreCase(Y)) {
				model.setIs_slmn_upload_flg(true);
			} else {
				model.setIs_slmn_upload_flg(false);
			}

			slmnArr.add(model);
		}
		return slmnArr;
	}

}
