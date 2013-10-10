/*
 * Copyright (C) 2013  All Rights Reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
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
import com.mpos.master.model.PRDCT_MST_Model;

/**
 * Description-
 * 
 */

public class ProductMstNTHandler extends NTHandler {

	public static final String TAG = Constants.APP_TAG
			+ ProductMstNTHandler.class.getSimpleName();

	protected Bundle bundle = new Bundle();

	private final String PRDCT_DSCR = "prdctDscrptn";
	private final String PRDCT_LNG_DSCR = "prdctLngDscrptn";
	private final String PRDCT_SHRT_DSCR = "prdctShrtDscrptn";
	private final String PRDCT_PCKNG = "prdctPckng";
	private final String PRDCT_VAT_CODE = "prdctVatCode";
	private final String PRDCT_COST_PRICE = "prdctCostPrce";
	private final String PRDCT_SELL_PRICE = "prdctSellPrce";
	private final String PRDCT_MIN_QTY = "prdctMinQty";
	private final String PRDCT_FIX_QTY = "prdctFixQty";
	private final String PRDCT_SPPLR = "prdctSpplr";
	private final String PRDCT_BIN_LOC_CODE = "prdctBinLocCode";
	private final String PRDCT_DPT_CODE = "prdctDptCode";
	private final String PRDCT_CPVLTN = "prdctCpVltn";
	private final String PRDCT_SUB_CLASS = "prdctSubClass";
	private final String PRDCT_GRP_CODE = "prdctGrpCode";
	private final String PRDCT_ANALYS_CODE = "prdctAnlysCode";
	private final String PRDCT_DISP_ORDER_NO = "dispOrderNo";
	private final String PRDCT_CLR = "prdctClr";
	private final String PRDCT_UNIT = "prdctUnit";
	private final String PRDCT_FIX_PRCE = "prdctFixPrce";
	private final String PRDCT_CODE = "prdctCode";
	private final String PRDCT_QTY = "prdctQty";

	public ProductMstNTHandler(HandlerInf handlerInf,
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

		ArrayList<PRDCT_MST_Model> dataArr;

		try {
			JSONArray jArr = new JSONArray(response);
			dataArr = getProductMasterArr(jArr);
			bundle.putSerializable(Constants.RESULTCODEBEAN, dataArr);
			mOperationalResult.setResult(bundle);
		} catch (JSONException e) {
			e.printStackTrace();
			Logger.w(TAG, e.toString());
			//Log.d(TAG, "## pulsar ## PRDCT_MSNT_NT_HANDLER");
			//mOperationalResult.setRequestResponseCode(OperationalResult.ERROR);
			mOperationalResult.setApiName("Product Master");
			mOperationalResult.setRequestResponseCode(OperationalResult.DATA_PROTOCOL_ERROR);
		}

	}

	/***
	 * Method- To create PRDCT_MST models and fill it in array
	 * 
	 * @param jArray
	 * @return
	 */
	private ArrayList<PRDCT_MST_Model> getProductMasterArr(JSONArray jArray) {
		ArrayList<PRDCT_MST_Model> prdctArr = new ArrayList<PRDCT_MST_Model>();
		for (int i = 0; i < jArray.length(); i++) {
			PRDCT_MST_Model model = new PRDCT_MST_Model();
			JSONObject prdctObj = jArray.optJSONObject(i);

			model.setDISP_ORDER_NO(Double.parseDouble(prdctObj.optString(
					PRDCT_DISP_ORDER_NO).trim()));

			model.setPRDCT_ANLYS_CODE(prdctObj.optString(PRDCT_ANALYS_CODE)
					.trim());
			model.setPRDCT_BIN_LOC_CODE(prdctObj.optString(PRDCT_BIN_LOC_CODE)
					.trim());
			System.out.println("##%$ " + prdctObj.optString(PRDCT_CLR).trim());
			model.setPRDCT_CLR(prdctObj.optString(PRDCT_CLR).trim());
			model.setPRDCT_CODE(prdctObj.optString(PRDCT_CODE).trim());
			model.setPRDCT_COST_PRCE(Double.parseDouble(prdctObj.optString(
					PRDCT_COST_PRICE).trim()));
			model.setPRDCT_CP_VLTN(prdctObj.optString(PRDCT_CPVLTN).trim());
			model.setPRDCT_DPT_CODE(prdctObj.optString(PRDCT_DPT_CODE).trim());
			model.setPRDCT_DSCRPTN(prdctObj.optString(PRDCT_DSCR).trim());
			model.setPRDCT_FIX_QTY(prdctObj.optString(PRDCT_FIX_QTY).trim());
			model.setPRDCT_GRP_CODE(prdctObj.optString(PRDCT_GRP_CODE).trim());

			// model.setPRDCT_IMG(prdctObj.optString(PRDCT_IMG));

			model.setPRDCT_FIXED_PRCE(prdctObj.optString(PRDCT_FIX_PRCE).trim());
			model.setPRDCT_LNG_DSCRPTN(prdctObj.optString(PRDCT_LNG_DSCR)
					.trim());

			model.setPRDCT_MIN_QTY(Float.parseFloat(prdctObj.optString(
					PRDCT_MIN_QTY).trim()));

			model.setPRDCT_PCKNG(Double.parseDouble(prdctObj.optString(
					PRDCT_PCKNG).trim()));

			model.setPRDCT_QTY(Double.parseDouble(prdctObj.optString(PRDCT_QTY)
					.trim()));
			model.setPRDCT_SELL_PRCE(Double.parseDouble(prdctObj.optString(
					PRDCT_SELL_PRICE).trim()));
			model.setPRDCT_SHRT_DSCRPTN(prdctObj.optString(PRDCT_SHRT_DSCR)
					.trim());
			model.setPRDCT_SPPLR(prdctObj.optString(PRDCT_SPPLR).trim());
			model.setPRDCT_SUB_CLASS(prdctObj.optString(PRDCT_SUB_CLASS).trim());
			model.setPRDCT_UNIT(prdctObj.optString(PRDCT_UNIT).trim());
			model.setPRDCT_VAT_CODE(prdctObj.optString(PRDCT_VAT_CODE).trim());

			prdctArr.add(model);
		}

		return prdctArr;
	}
}
