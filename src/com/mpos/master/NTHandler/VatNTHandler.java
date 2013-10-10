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
import com.mpos.master.model.VAT_MST_Model;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class VatNTHandler extends NTHandler {

	public static final String TAG = Constants.APP_TAG
			+ VatNTHandler.class.getSimpleName();

	private final String CODE = "vatCode";
	private final String PRCNT = "vatPrcnt";
	private final String VAT_USR_NAME = "";

	protected Bundle bundle = new Bundle();

	public VatNTHandler(HandlerInf handlerInf, List<NameValuePair> params,
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
		ArrayList<VAT_MST_Model> dataArr;
		try {
			JSONArray jArr = new JSONArray(response);
			dataArr = getVatArr(jArr);
			bundle.putSerializable(Constants.RESULTCODEBEAN, dataArr);
			mOperationalResult.setResult(bundle);
		} catch (JSONException e) {
			e.printStackTrace();
			Logger.w(TAG, e.toString());
			// mOperationalResult.setRequestResponseCode(OperationalResult.ERROR);
			mOperationalResult.setApiName("VAT");
			mOperationalResult
					.setRequestResponseCode(OperationalResult.DATA_PROTOCOL_ERROR);
		}
	}

	/**
	 * Method- To create VAT_MST models and fill it in array
	 * 
	 * @param jArr
	 * @return
	 * @throws JSONException
	 */
	private ArrayList<VAT_MST_Model> getVatArr(JSONArray jArr)
			throws JSONException {
		ArrayList<VAT_MST_Model> vatArr = new ArrayList<VAT_MST_Model>();

		for (int i = 0; i < jArr.length(); i++) {
			VAT_MST_Model model = new VAT_MST_Model();
			JSONObject jObj = jArr.getJSONObject(i);

			model.setVat_code(jObj.optString(CODE).trim());
			model.setVat_prcnt(Double.parseDouble(jObj.optString(PRCNT).trim()));
			vatArr.add(model);
		}
		return vatArr;
	}

}
