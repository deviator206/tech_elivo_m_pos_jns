/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * USAP PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/**
 * 
 */
package com.mpos.transactions.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;

import com.mpos.framework.common.DBConstants;
import com.mpos.framework.common.Logger;
import com.mpos.master.model.BaseModel;
import com.mpos.payment.model.MULTY_PMT_MST_Model;

/**
 * Description-
 * 
 */

public class Bill_INSTRCTN_TXN_Model extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String companyCode;
	private String branchCode;
	private String billScnd;
	private String prdctCode;
	private String pck;
	private String instrcCode;
	private String extraInstrcn;
	private String prdctVoid;
	private double instrnQty;
	private String row_id;

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBillScnd() {
		return billScnd;
	}

	public void setBillScnd(String billScnd) {
		this.billScnd = billScnd;
	}

	public String getPrdctCode() {
		return prdctCode;
	}

	public void setPrdctCode(String prdctCode) {
		this.prdctCode = prdctCode;
	}

	public String getPck() {
		return pck;
	}

	public void setPck(String pck) {
		this.pck = pck;
	}

	public String getInstrcCode() {
		return instrcCode;
	}

	public void setInstrcCode(String instrcCode) {
		this.instrcCode = instrcCode;
	}

	public String getExtraInstrcn() {
		return extraInstrcn;
	}

	public void setExtraInstrcn(String extraInstrcn) {
		this.extraInstrcn = extraInstrcn;
	}

	public String getPrdctVoid() {
		return prdctVoid;
	}

	public void setPrdctVoid(String prdctVoid) {
		this.prdctVoid = prdctVoid;
	}

	public double getInstrnQty() {
		return instrnQty;
	}

	public void setInstrnQty(double instrnQty) {
		this.instrnQty = instrnQty;
	}

	@Override
	public String toString() {
		return "Bill_INSTRCTN_TXN_Model [companyCode=" + companyCode
				+ ", branchCode=" + branchCode + ", billScnd=" + billScnd
				+ ", prdctCode=" + prdctCode + ", pck=" + pck + ", instrcCode="
				+ instrcCode + ", extraInstrcn=" + extraInstrcn
				+ ", prdctVoid=" + prdctVoid + ", instrnQty=" + instrnQty + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		Bill_INSTRCTN_TXN_Model that = (Bill_INSTRCTN_TXN_Model) o;
		if (o != null) {
			Logger.v("this.getPrdctCode().trim()" + this.getPrdctCode().trim(),
					"that.getPrdctCode().trim()" + that.getPrdctCode().trim());
			if (this.getPrdctCode().trim()
					.equalsIgnoreCase(that.getPrdctCode().trim()) && this.getRow_id().trim()
					.equalsIgnoreCase(that.getRow_id().trim())) {
				// &&
				// this.getRow_id().trim().equalsIgnoreCase(that.getRow_id().trim())
				return true;
			}
		}
		return false;
	}

	public static ContentValues getcontentvalues(Bill_INSTRCTN_TXN_Model model) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.BILL_INSTRCTN_TXN_ROW_ID, model.getRow_id());
		values.put(DBConstants.BILL_INSTRCTN_TXN_BILL_SCND, model.getBillScnd());
		values.put(DBConstants.BILL_INSTRCTN_TXN_BRANCH_CODE,
				model.getBranchCode());
		values.put(DBConstants.BILL_INSTRCTN_TXN_CMP_CODE,
				model.getCompanyCode());
		values.put(DBConstants.BILL_INSTRCTN_TXN_EXTRA_INSTRCTN,
				model.getExtraInstrcn());

		values.put(DBConstants.BILL_INSTRCTN_TXN_INSTRCTN_CODE,
				model.getInstrcCode());
		values.put(DBConstants.BILL_INSTRCTN_TXN_PCK, model.getPck());
		values.put(DBConstants.BILL_INSTRCTN_TXN_PRDCT_CODE,
				model.getPrdctCode());
		values.put(DBConstants.BILL_INSTRCTN_TXN_PRDCT_VOID,
				model.getPrdctVoid());

		values.put(DBConstants.BILL_INSTRCTN_TXN_INSTRCN_QTY,
				model.getInstrnQty());

		return values;
	}

	/**
	 * Method- To return JSON data
	 * 
	 * @param bill_Instrc_txn_Model
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject getJsonData(
			Bill_INSTRCTN_TXN_Model bill_Instrc_txn_Model) throws JSONException {
		JSONObject billInstrcJsonObj = new JSONObject();

		billInstrcJsonObj.put("instrctnCode",
				bill_Instrc_txn_Model.getInstrcCode());
		billInstrcJsonObj.put("extraInstrctns",
				bill_Instrc_txn_Model.getExtraInstrcn());
		billInstrcJsonObj.put("instrctnQty",
				bill_Instrc_txn_Model.getInstrnQty());
		billInstrcJsonObj.put("billScncd", bill_Instrc_txn_Model.getBillScnd());
		billInstrcJsonObj.put("cmpnyCode",
				bill_Instrc_txn_Model.getCompanyCode());
		billInstrcJsonObj.put("brnchCode",
				bill_Instrc_txn_Model.getBranchCode());
		billInstrcJsonObj
				.put("prdctCode", bill_Instrc_txn_Model.getPrdctCode());
		billInstrcJsonObj
				.put("prdctVoid", bill_Instrc_txn_Model.getPrdctVoid());
		billInstrcJsonObj.put("pack", bill_Instrc_txn_Model.getPck());

		return billInstrcJsonObj;
	}

	public String getRow_id() {
		return row_id;
	}

	public void setRow_id(String row_id) {
		this.row_id = row_id;
	}

}
