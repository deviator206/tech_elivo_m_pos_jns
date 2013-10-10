/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * USAP PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/**
 * 
 */
package com.mpos.payment.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;

import com.mpos.framework.common.DBConstants;
import com.mpos.master.model.BILL_Mst_Model;
import com.mpos.master.model.BaseModel;

/**
 * Description-
 * 
 */

public class MULTI_CHNG_MST_Model extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String paymentNo;
	private String currAbbr;
	private double chngAmt;
	private double exRate;
	private String operator;
	private double locAmt;

	/**
	 * @return the paymentNo
	 */
	public String getPaymentNo() {
		return paymentNo;
	}

	/**
	 * @param paymentNo
	 *            the paymentNo to set
	 */
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	/**
	 * @return the currAbbr
	 */
	public String getCurrAbbr() {
		return currAbbr;
	}

	/**
	 * @param currAbbr
	 *            the currAbbr to set
	 */
	public void setCurrAbbr(String currAbbr) {
		this.currAbbr = currAbbr;
	}

	/**
	 * @return the chngAmt
	 */
	public double getChngAmt() {
		return chngAmt;
	}

	/**
	 * @param chngAmt
	 *            the chngAmt to set
	 */
	public void setChngAmt(double chngAmt) {
		this.chngAmt = chngAmt;
	}

	/**
	 * @return the exRate
	 */
	public double getExRate() {
		return exRate;
	}

	/**
	 * @param exRate
	 *            the exRate to set
	 */
	public void setExRate(double exRate) {
		this.exRate = exRate;
	}

	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator
	 *            the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return the locAmt
	 */
	public double getLocAmt() {
		return locAmt;
	}

	/**
	 * @param locAmt
	 *            the locAmt to set
	 */
	public void setLocAmt(double locAmt) {
		this.locAmt = locAmt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MULTI_CHNG_MST_Model [paymentNo=" + paymentNo + ", currAbbr="
				+ currAbbr + ", chngAmt=" + chngAmt + ", exRate=" + exRate
				+ ", operator=" + operator + ", locAmt=" + locAmt + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		MULTI_CHNG_MST_Model that = (MULTI_CHNG_MST_Model) o;
		if (this.getCurrAbbr().equalsIgnoreCase(that.getCurrAbbr())
				&& this.getPaymentNo().equalsIgnoreCase(that.getPaymentNo())) {
			return true;
		}
		return false;
	}

	public static ContentValues getcontentvalues(MULTI_CHNG_MST_Model model) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.MCM_CHNG_AMNT, model.getChngAmt());
		values.put(DBConstants.MCM_CURR_ABBR, model.getCurrAbbr());
		values.put(DBConstants.MCM_EX_RATE, model.getExRate());
		values.put(DBConstants.MCM_LOC_AMNT, model.getLocAmt());
		values.put(DBConstants.MCM_OPERATOR, model.getOperator());
		values.put(DBConstants.MCM_PYMNT_NMBR, model.getPaymentNo());
		return values;
	}

	/**
	 * Method- To return JSON data
	 * 
	 * @param mFlxPosTxnModel
	 * 
	 * @param billMstModel
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject getJsonData(
			MULTI_CHNG_MST_Model multi_CHNG_MST_Model,
			BILL_Mst_Model billMstModel) throws JSONException {
		JSONObject issueMltiChangeMst = new JSONObject();

		issueMltiChangeMst.put("cmpnyCode", billMstModel.getCMPNY_CODE());
		issueMltiChangeMst.put("brnchCode", billMstModel.getBRNCH_CODE());
		issueMltiChangeMst.put("uploadFlg", "Y");
		issueMltiChangeMst
				.put("pymntNmbr", multi_CHNG_MST_Model.getPaymentNo());
		issueMltiChangeMst.put("currAbbr", multi_CHNG_MST_Model.getCurrAbbr());
		issueMltiChangeMst.put("exRate", multi_CHNG_MST_Model.getExRate());
		issueMltiChangeMst.put("operator", multi_CHNG_MST_Model.getOperator());
		issueMltiChangeMst.put("locAmnt", multi_CHNG_MST_Model.getLocAmt());
		issueMltiChangeMst.put("chngAmnt", multi_CHNG_MST_Model.getChngAmt());

		return issueMltiChangeMst;
	}

}
