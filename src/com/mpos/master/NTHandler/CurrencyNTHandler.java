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
import com.mpos.master.model.CURRENCY_MST_MODEL;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class CurrencyNTHandler extends NTHandler {

	public static final String TAG = Constants.APP_TAG
			+ CurrencyNTHandler.class.getSimpleName();

	private final String NAME = "name";
	private final String SYMBOL = "symbol";
	private final String ABBR_NAME = "abbrName";
	private final String UNIT = "unit";
	private final String POSTN = "postn";
	private final String SUB_UNIT = "subUnit";
	private final String PRESC = "presc";
	private final String OPERATOR = "operator";
	private final String EXRATE = "exRate";

	protected Bundle bundle = new Bundle();

	public CurrencyNTHandler(HandlerInf handlerInf, List<NameValuePair> params,
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
		ArrayList<CURRENCY_MST_MODEL> dataArr;
		try {
			JSONArray jArr = new JSONArray(response);
			dataArr = getCurrencyArr(jArr);
			bundle.putSerializable(Constants.RESULTCODEBEAN, dataArr);
			mOperationalResult.setResult(bundle);
		} catch (JSONException e) {
			e.printStackTrace();
			Logger.w(TAG, e.toString());
			//Log.d(TAG, "## pulsar ## CURRENCY_NT_HANDLER");
			//mOperationalResult.setRequestResponseCode(OperationalResult.ERROR);
			mOperationalResult.setApiName("Currency");
			mOperationalResult.setRequestResponseCode(OperationalResult.DATA_PROTOCOL_ERROR);
		}
	}

	/**
	 * Method- To create CURRENCY_MST models and fill it in array
	 * 
	 * @param jArr
	 * @return
	 * @throws JSONException
	 */
	private ArrayList<CURRENCY_MST_MODEL> getCurrencyArr(JSONArray jArr)
			throws JSONException {
		ArrayList<CURRENCY_MST_MODEL> currArr = new ArrayList<CURRENCY_MST_MODEL>();

		for (int i = 0; i < jArr.length(); i++) {
			CURRENCY_MST_MODEL model = new CURRENCY_MST_MODEL();
			JSONObject jObj = jArr.getJSONObject(i);

			model.setCurr_name(jObj.optString(NAME).trim());
			model.setCurr_symbol(jObj.optString(SYMBOL).trim());
			model.setCurr_abbrName(jObj.optString(ABBR_NAME).trim());
			model.setCurr_postn(jObj.optInt(POSTN));
			model.setCurr_presc(jObj.optString(PRESC).trim());
			model.setCurr_subUnit(jObj.optString(SUB_UNIT).trim());
			model.setCurr_unit(jObj.optString(UNIT).trim());
			model.setCurr_operator(jObj.optString(OPERATOR).trim());
			try {
				model.setCurr_exrate(Float.parseFloat(jObj.optString(EXRATE)));
			} catch (NumberFormatException ex) {
				model.setCurr_exrate(1);
			}
			model.setCurr_base_flag("N");
			if (i == 0) {
				model.setCurr_base_flag("Y");
			}
			currArr.add(model);
		}
		return currArr;
	}

}
