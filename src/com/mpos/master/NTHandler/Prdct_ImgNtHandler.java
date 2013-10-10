/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * USAP PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/**
 * 
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
import com.mpos.master.model.PrdctImageMstModel;

/**
 * Description-
 * 
 */

public class Prdct_ImgNtHandler extends NTHandler {

	public static final String TAG = Constants.APP_TAG
			+ Prdct_ImgNtHandler.class.getSimpleName();

	protected Bundle bundle = new Bundle();

	private final String PRDCT_CODE = "prdctCode";
	private final String PRDCT_IMG = "prdctImg";

	/**
	 * @param handlerInf
	 * @param params
	 * @param url
	 * @param resultCode
	 */
	public Prdct_ImgNtHandler(HandlerInf handlerInf,
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

		ArrayList<PrdctImageMstModel> dataArr;

		try {
			JSONArray jArr = new JSONArray(response);
			dataArr = getProductImgArr(jArr);
			bundle.putSerializable(Constants.RESULTCODEBEAN, dataArr);
			mOperationalResult.setResult(bundle);
		} catch (JSONException e) {
			Logger.d(TAG, "Images finished");
			e.printStackTrace();
			Logger.w(TAG, e.toString());
			//Log.d(TAG, "## pulsar ## GRPNTPOS_MSNT_HANDLER");
			//mOperationalResult.setRequestResponseCode(OperationalResult.ERROR);
			mOperationalResult.setApiName("Product Image");
			mOperationalResult.setRequestResponseCode(OperationalResult.DATA_PROTOCOL_ERROR);
		}
	}

	/**
	 * Method- To create PRDCT_MST models and fill it in array
	 * 
	 * @param jArr
	 * @return
	 */
	private ArrayList<PrdctImageMstModel> getProductImgArr(JSONArray jArr) {
		ArrayList<PrdctImageMstModel> prdctImgArr = new ArrayList<PrdctImageMstModel>();

		for (int i = 0; i < jArr.length(); i++) {
			JSONObject jObj = jArr.optJSONObject(i);
			if (jObj != null) {
				PrdctImageMstModel imageMstModel = new PrdctImageMstModel();
				imageMstModel.setPrdct_code(jObj.optString(PRDCT_CODE));
				imageMstModel.setPrdct_img(jObj.optString(PRDCT_IMG));
				prdctImgArr.add(imageMstModel);
			}
		}

		return prdctImgArr;
	}

}
