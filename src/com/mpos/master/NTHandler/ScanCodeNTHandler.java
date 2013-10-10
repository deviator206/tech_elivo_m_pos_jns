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
import com.mpos.master.model.SCAN_CODE_MST_Model;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class ScanCodeNTHandler extends NTHandler {

	public static final String TAG = Constants.APP_TAG
			+ CurrencyNTHandler.class.getSimpleName();

	private String DSCNT_COST_PRICE = "dscntCostPrice";
	private String DSCNT_PRICE = "dscntPrice";
	private String QTY = "qty";
	private String PRDCT_CODE = "prdctCode";
	private String UOM = "uom";
	private String SCAN_CODE = "scanCode";

	protected Bundle bundle = new Bundle();

	public ScanCodeNTHandler(HandlerInf handlerInf, List<NameValuePair> params,
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
		ArrayList<SCAN_CODE_MST_Model> dataArr;
		try {
			JSONArray jArr = new JSONArray(response);
			dataArr = getScanCodeArr(jArr);
			bundle.putSerializable(Constants.RESULTCODEBEAN, dataArr);
			mOperationalResult.setResult(bundle);
		} catch (JSONException e) {
			e.printStackTrace();
			Logger.w(TAG, e.toString());
			//Log.d(TAG, "## pulsar ## SCANCODE_NT_HANDLER");
			//mOperationalResult.setRequestResponseCode(OperationalResult.ERROR);
			mOperationalResult.setApiName("Scan Code");
			mOperationalResult.setRequestResponseCode(OperationalResult.DATA_PROTOCOL_ERROR);
		}
	}

	/**
	 * Method- To create SCAN_CODE_MST models and fill it in array
	 * 
	 * @param jArr
	 * @return
	 * @throws JSONException
	 */
	private ArrayList<SCAN_CODE_MST_Model> getScanCodeArr(JSONArray jArr)
			throws JSONException {
		ArrayList<SCAN_CODE_MST_Model> scanCodeArr = new ArrayList<SCAN_CODE_MST_Model>();

		for (int i = 0; i < jArr.length(); i++) {
			SCAN_CODE_MST_Model model = new SCAN_CODE_MST_Model();
			JSONObject jObj = jArr.getJSONObject(i);

			model.setDscnt_cost_price(Double.parseDouble(jObj
					.optString(DSCNT_COST_PRICE).trim()));
			model.setDscnt_price(Double.parseDouble(jObj.optString(DSCNT_PRICE).trim()));
			model.setQty(Double.parseDouble(jObj.optString(QTY).trim()));
			model.setPrdct_code(jObj.optString(PRDCT_CODE).trim());
			model.setUom(jObj.optString(UOM).trim());
			model.setScan_code(jObj.optString(SCAN_CODE).trim());
			scanCodeArr.add(model);
		}
		return scanCodeArr;
	}

}
