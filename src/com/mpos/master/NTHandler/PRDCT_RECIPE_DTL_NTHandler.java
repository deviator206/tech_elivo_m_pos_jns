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
import com.mpos.home.activity.HomeActivity;
import com.mpos.transactions.model.PRDCT_RECIPE_DTL_TXN_Model;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class PRDCT_RECIPE_DTL_NTHandler extends NTHandler {

	public static final String TAG = Constants.APP_TAG
			+ PRDCT_RECIPE_DTL_NTHandler.class.getSimpleName();

	protected Bundle bundle = new Bundle();

	private final String PRDCT_CODE = "prdctCode";
	private final String COST = "cost";
	private final String RCP_QTY = "rcpQty";
	private final String STK_QTY = "stkQty";
	private final String TLRNCE_QTY = "tlrnceQty";
	private final String STK_UOM_CODE = "stkUomCode";
	private final String RCP_MSR = "rcpMeasure";
	private final String RCP_UOM_CODE = "rcpUomCode";
	private final String RCP_CODE = "recipeCode";
	private final String STK_TLRNCE_QTY = "stkTlrnceQty";

	public PRDCT_RECIPE_DTL_NTHandler(HandlerInf handlerInf,
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

		ArrayList<PRDCT_RECIPE_DTL_TXN_Model> dataArr;
		try {
			JSONArray jArr = new JSONArray(response);
			dataArr = getProductRecipeArr(jArr);
			bundle.putSerializable(Constants.RESULTCODEBEAN, dataArr);
			mOperationalResult.setResult(bundle);
		} catch (JSONException e) {
			e.printStackTrace();
			Logger.w(TAG, e.toString());
			mOperationalResult.setApiName("Product Recipe");
			mOperationalResult
					.setRequestResponseCode(OperationalResult.DATA_PROTOCOL_ERROR);

		}

	}

	/***
	 * Method- To create PRDCT_Recipe models and fill it in array
	 * 
	 * @param jArr
	 * @return
	 * @throws JSONException
	 */
	private ArrayList<PRDCT_RECIPE_DTL_TXN_Model> getProductRecipeArr(
			JSONArray jArr) throws JSONException {
		ArrayList<PRDCT_RECIPE_DTL_TXN_Model> recipeArr = new ArrayList<PRDCT_RECIPE_DTL_TXN_Model>();

		for (int i = 0; i < jArr.length(); i++) {
			PRDCT_RECIPE_DTL_TXN_Model model = new PRDCT_RECIPE_DTL_TXN_Model();
			JSONObject jObj = jArr.getJSONObject(i);

			model.setPrdct_code(jObj.optString(PRDCT_CODE).trim());
			model.setRcp_cost(Double.parseDouble(jObj.optString(COST).trim()));
			model.setRcp_measure(jObj.optString(RCP_MSR).trim());
			model.setRcp_qty(Double.parseDouble(jObj.optString(RCP_QTY).trim()));
			model.setRcp_uom_code(jObj.optString(RCP_UOM_CODE).trim());
			model.setRecipe_code(jObj.optString(RCP_CODE).trim());
			model.setStk_qty(Double.parseDouble(jObj.optString(STK_QTY).trim()));
			model.setStk_tlrnce_qty(Double.parseDouble(jObj.optString(
					STK_TLRNCE_QTY).trim()));
			model.setStk_uom_code(jObj.optString(STK_UOM_CODE).trim());
			model.setTlrnce_qty(Double.parseDouble(jObj.optString(
					STK_TLRNCE_QTY).trim()));

			recipeArr.add(model);
		}
		return recipeArr;
	}

}
