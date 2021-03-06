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
import com.mpos.master.model.CRDCT_CARD_MST_Model;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class CRDCT_CardNTHandler extends NTHandler {

	public static final String TAG = Constants.APP_TAG
			+ CurrencyNTHandler.class.getSimpleName();

	private final String NAME = "crdtCardName";
	private final String CODE = "crdtCardCode";
	private final String CC_CMSN = "crdtCardCmsn";

	protected Bundle bundle = new Bundle();

	public CRDCT_CardNTHandler(HandlerInf handlerInf,
			List<NameValuePair> params, String url, int resultCode) {
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
		ArrayList<CRDCT_CARD_MST_Model> dataArr;
		try {
			JSONArray jArr = new JSONArray(response);
			dataArr = getCRDCT_CArr(jArr);
			bundle.putSerializable(Constants.RESULTCODEBEAN, dataArr);
			mOperationalResult.setResult(bundle);
		} catch (JSONException e) {
			e.printStackTrace();
			Logger.w(TAG, e.toString());
			//Log.d(TAG, "## pulsar ## CRDCT_NT_HANDLER");
			//mOperationalResult.setRequestResponseCode(OperationalResult.ERROR);
			mOperationalResult.setApiName("Credit Card");
			mOperationalResult.setRequestResponseCode(OperationalResult.DATA_PROTOCOL_ERROR);
		}
	}

	/**
	 * Method- To create CRDCT_CARD_MST models and fill it in array
	 * 
	 * @param jArr
	 * @return
	 * @throws JSONException
	 */
	private ArrayList<CRDCT_CARD_MST_Model> getCRDCT_CArr(JSONArray jArr)
			throws JSONException {
		ArrayList<CRDCT_CARD_MST_Model> ccArr = new ArrayList<CRDCT_CARD_MST_Model>();

		for (int i = 0; i < jArr.length(); i++) {
			CRDCT_CARD_MST_Model model = new CRDCT_CARD_MST_Model();
			JSONObject jObj = jArr.getJSONObject(i);

			model.setCc_name(jObj.optString(NAME).trim());
			model.setCc_code(jObj.optString(CODE).trim());
			model.setCc_cmsn(jObj.optString(CC_CMSN).trim());
			ccArr.add(model);
		}
		return ccArr;
	}

}
