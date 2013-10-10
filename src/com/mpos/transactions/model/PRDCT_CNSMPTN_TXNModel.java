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
import com.mpos.master.model.BaseModel;

/**
 * Description-
 * 
 */

public class PRDCT_CNSMPTN_TXNModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String cmp_code;
	private String branch_code;
	private String txn_no;
	private String prdct_code;
	private String stk_uom_code;
	private double stk_qty;
	private double prdct_prce;
	private String prdct_vat_code;
	private double prdct_vat_amt;
	private String prdct_void;
	private String recipe_code;
	private String txn_type;
	private String txn_mode;
	private String pck;

	/**
	 * @return the cmp_code
	 */
	public String getCmp_code() {
		return cmp_code;
	}

	/**
	 * @param cmp_code
	 *            the cmp_code to set
	 */
	public void setCmp_code(String cmp_code) {
		this.cmp_code = cmp_code;
	}

	/**
	 * @return the branch_code
	 */
	public String getBranch_code() {
		return branch_code;
	}

	/**
	 * @param branch_code
	 *            the branch_code to set
	 */
	public void setBranch_code(String branch_code) {
		this.branch_code = branch_code;
	}

	/**
	 * @return the txn_no
	 */
	public String getTxn_no() {
		return txn_no;
	}

	/**
	 * @param txn_no
	 *            the txn_no to set
	 */
	public void setTxn_no(String txn_no) {
		this.txn_no = txn_no;
	}

	/**
	 * @return the prdct_code
	 */
	public String getPrdct_code() {
		return prdct_code;
	}

	/**
	 * @param prdct_code
	 *            the prdct_code to set
	 */
	public void setPrdct_code(String prdct_code) {
		this.prdct_code = prdct_code;
	}

	/**
	 * @return the stk_uom_code
	 */
	public String getStk_uom_code() {
		return stk_uom_code;
	}

	/**
	 * @param stk_uom_code
	 *            the stk_uom_code to set
	 */
	public void setStk_uom_code(String stk_uom_code) {
		this.stk_uom_code = stk_uom_code;
	}

	/**
	 * @return the stk_qty
	 */
	public double getStk_qty() {
		return stk_qty;
	}

	/**
	 * @param stk_qty
	 *            the stk_qty to set
	 */
	public void setStk_qty(double stk_qty) {
		this.stk_qty = stk_qty;
	}

	/**
	 * @return the prdct_prce
	 */
	public double getPrdct_prce() {
		return prdct_prce;
	}

	/**
	 * @param prdct_prce
	 *            the prdct_prce to set
	 */
	public void setPrdct_prce(double prdct_prce) {
		this.prdct_prce = prdct_prce;
	}

	/**
	 * @return the prdct_vat_code
	 */
	public String getPrdct_vat_code() {
		return prdct_vat_code;
	}

	/**
	 * @param prdct_vat_code
	 *            the prdct_vat_code to set
	 */
	public void setPrdct_vat_code(String prdct_vat_code) {
		this.prdct_vat_code = prdct_vat_code;
	}

	/**
	 * @return the prdct_vat_amt
	 */
	public double getPrdct_vat_amt() {
		return prdct_vat_amt;
	}

	/**
	 * @param prdct_vat_amt
	 *            the prdct_vat_amt to set
	 */
	public void setPrdct_vat_amt(double prdct_vat_amt) {
		this.prdct_vat_amt = prdct_vat_amt;
	}

	/**
	 * @return the prdct_void
	 */
	public String getPrdct_void() {
		return prdct_void;
	}

	/**
	 * @param prdct_void
	 *            the prdct_void to set
	 */
	public void setPrdct_void(String prdct_void) {
		this.prdct_void = prdct_void;
	}

	/**
	 * @return the recipe_code
	 */
	public String getRecipe_code() {
		return recipe_code;
	}

	/**
	 * @param recipe_code
	 *            the recipe_code to set
	 */
	public void setRecipe_code(String recipe_code) {
		this.recipe_code = recipe_code;
	}

	/**
	 * @return the txn_type
	 */
	public String getTxn_type() {
		return txn_type;
	}

	/**
	 * @param txn_type
	 *            the txn_type to set
	 */
	public void setTxn_type(String txn_type) {
		this.txn_type = txn_type;
	}

	/**
	 * @return the txn_mode
	 */
	public String getTxn_mode() {
		return txn_mode;
	}

	/**
	 * @param txn_mode
	 *            the txn_mode to set
	 */
	public void setTxn_mode(String txn_mode) {
		this.txn_mode = txn_mode;
	}

	/**
	 * @return the pck
	 */
	public String getPck() {
		return pck;
	}

	/**
	 * @param pck
	 *            the pck to set
	 */
	public void setPck(String pck) {
		this.pck = pck;
	}

	@Override
	public String toString() {
		return "PRDCT_CNSMPTN_TXNModel [cmp_code=" + cmp_code
				+ ", branch_code=" + branch_code + ", txn_no=" + txn_no
				+ ", prdct_code=" + prdct_code + ", stk_uom_code="
				+ stk_uom_code + ", stk_qty=" + stk_qty + ", prdct_prce="
				+ prdct_prce + ", prdct_vat_code=" + prdct_vat_code
				+ ", prdct_vat_amt=" + prdct_vat_amt + ", prdct_void="
				+ prdct_void + ", recipe_code=" + recipe_code + ", txn_type="
				+ txn_type + ", txn_mode=" + txn_mode + ", pck=" + pck + "]";
	}

	public static ContentValues getcontentvalues(PRDCT_CNSMPTN_TXNModel model) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.CNSMPTN_BRNCH_CODE, model.getBranch_code());
		values.put(DBConstants.CNSMPTN_CMPNY_CODE, model.getCmp_code());
		values.put(DBConstants.CNSMPTN_PCK, model.getPck());
		values.put(DBConstants.CNSMPTN_PRDCT_CODE, model.getPrdct_code());
		values.put(DBConstants.CNSMPTN_PRDCT_VAT_CODE,
				model.getPrdct_vat_code());
		values.put(DBConstants.CNSMPTN_RECIPE_CODE, model.getRecipe_code());
		values.put(DBConstants.CNSMPTN_STK_UOM_CODE, model.getStk_uom_code());
		values.put(DBConstants.CNSMPTN_PRDCT_VOID, model.getPrdct_void());
		values.put(DBConstants.CNSMPTN_TXN_MODE, model.getTxn_mode());
		values.put(DBConstants.CNSMPTN_TXN_NO, model.getTxn_no());
		values.put(DBConstants.CNSMPTN_TXN_TYPE, model.getTxn_type());
		values.put(DBConstants.CNSMPTN_PRDCT_PRCE, model.getPrdct_prce());
		values.put(DBConstants.CNSMPTN_PRDCT_VAT_AMNT, model.getPrdct_vat_amt());
		values.put(DBConstants.CNSMPTN_STK_QTY, model.getStk_qty());
		return values;
	}

	/**
	 * Method- To return JSON data
	 * 
	 * @param billConsumptionJsonObj
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject getJsonData(
			PRDCT_CNSMPTN_TXNModel prdct_Cnsmptn_txn_Model)
			throws JSONException {
		JSONObject billConsumptionJsonObj = new JSONObject();

		billConsumptionJsonObj.put("prdctVatCode",
				prdct_Cnsmptn_txn_Model.getPrdct_vat_code());
		billConsumptionJsonObj.put("prdctVatAmnt",
				prdct_Cnsmptn_txn_Model.getPrdct_vat_amt());
		billConsumptionJsonObj.put("stkUomCode",
				prdct_Cnsmptn_txn_Model.getStk_uom_code());
		billConsumptionJsonObj.put("recipeCode",
				prdct_Cnsmptn_txn_Model.getRecipe_code());
		billConsumptionJsonObj.put("cmpnyCode",
				prdct_Cnsmptn_txn_Model.getCmp_code());
		billConsumptionJsonObj.put("brnchCode",
				prdct_Cnsmptn_txn_Model.getBranch_code());
		billConsumptionJsonObj.put("txnType",
				prdct_Cnsmptn_txn_Model.getTxn_type());
		billConsumptionJsonObj.put("prdctCode",
				prdct_Cnsmptn_txn_Model.getPrdct_code());
		billConsumptionJsonObj.put("prdctVoid",
				prdct_Cnsmptn_txn_Model.getPrdct_void());
		billConsumptionJsonObj
				.put("txnNo", prdct_Cnsmptn_txn_Model.getTxn_no());
		billConsumptionJsonObj.put("stkQty",
				prdct_Cnsmptn_txn_Model.getStk_qty());
		billConsumptionJsonObj.put("prdctPrce",
				prdct_Cnsmptn_txn_Model.getPrdct_prce());
		billConsumptionJsonObj.put("txnMode",
				prdct_Cnsmptn_txn_Model.getTxn_mode());
		billConsumptionJsonObj.put("pck", prdct_Cnsmptn_txn_Model.getPck());

		return billConsumptionJsonObj;
	}

}
