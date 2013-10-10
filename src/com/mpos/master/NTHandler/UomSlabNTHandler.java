/*
 * Copyright (C) 2013 jaggarnaut-studios Pvt. Ltd All Rights Reserved.
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
import com.mpos.master.model.UOM_SLAB_MST_MODEL;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class UomSlabNTHandler extends NTHandler {

	public static final String TAG = Constants.APP_TAG
			+ UomSlabNTHandler.class.getSimpleName();

	private final String PRDCT_SELL_PRCE = "prdctSellPrce";
	private final String PRDCT_CODE = "prdctCode";
	private final String UOM_CODE = "uomCode";
	private final String PRDCT_COST_PRCE = "prdctCostPrce";

	protected Bundle bundle = new Bundle();

	public UomSlabNTHandler(HandlerInf handlerInf, List<NameValuePair> params,
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
		ArrayList<UOM_SLAB_MST_MODEL> dataArr;
		try {
			JSONArray jArr = new JSONArray(response);
			dataArr = getUOMSlabArr(jArr);
			bundle.putSerializable(Constants.RESULTCODEBEAN, dataArr);
			mOperationalResult.setResult(bundle);
		} catch (JSONException e) {
			e.printStackTrace();
			Logger.w(TAG, e.toString());
			mOperationalResult.setApiName("UOMSlab");
			mOperationalResult.setRequestResponseCode(OperationalResult.DATA_PROTOCOL_ERROR);
		}
	}

	/**
	 * Method- To create UOM_SLab MST models and fill it in array
	 * 
	 * @param jArr
	 * @return
	 * @throws JSONException
	 */
	private ArrayList<UOM_SLAB_MST_MODEL> getUOMSlabArr(JSONArray jArr)
			throws JSONException {
		ArrayList<UOM_SLAB_MST_MODEL> uomSlabArr = new ArrayList<UOM_SLAB_MST_MODEL>();

		for (int i = 0; i < jArr.length(); i++) {
			UOM_SLAB_MST_MODEL model = new UOM_SLAB_MST_MODEL();
			JSONObject jObj = jArr.getJSONObject(i);
			model.setUom_Code(jObj.optString(UOM_CODE).trim());
			model.setPrdct_Code(jObj.optString(PRDCT_CODE).trim());
			model.setPrdct_Cost_Price(jObj.optString(PRDCT_COST_PRCE).trim());
			model.setPrdct_Sell_Price(jObj.optString(PRDCT_SELL_PRCE).trim());

			uomSlabArr.add(model);
		}
		return uomSlabArr;
	}

}
