/*
 * Copyright (C) 2013 jaggarnaut-studios Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.transactions.model;

import android.content.ContentValues;

import com.mpos.framework.common.DBConstants;
import com.mpos.master.model.BaseModel;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class PRDCT_RECIPE_DTL_TXN_Model extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String recipe_code;
	private String rcp_measure;
	private String prdct_code;
	private String rcp_uom_code;
	private double rcp_qty;
	private double rcp_cost;
	private String stk_uom_code;
	private double stk_qty;
	private double tlrnce_qty;
	private double stk_tlrnce_qty;

	public String getRecipe_code() {
		return recipe_code;
	}

	public void setRecipe_code(String recipe_code) {
		this.recipe_code = recipe_code;
	}

	public String getRcp_measure() {
		return rcp_measure;
	}

	public void setRcp_measure(String rcp_measure) {
		this.rcp_measure = rcp_measure;
	}

	public String getPrdct_code() {
		return prdct_code;
	}

	public void setPrdct_code(String prdct_code) {
		this.prdct_code = prdct_code;
	}

	public String getRcp_uom_code() {
		return rcp_uom_code;
	}

	public void setRcp_uom_code(String rcp_uom_code) {
		this.rcp_uom_code = rcp_uom_code;
	}

	public double getRcp_qty() {
		return rcp_qty;
	}

	public void setRcp_qty(double rcp_qty) {
		this.rcp_qty = rcp_qty;
	}

	public double getRcp_cost() {
		return rcp_cost;
	}

	public void setRcp_cost(double rcp_cost) {
		this.rcp_cost = rcp_cost;
	}

	public String getStk_uom_code() {
		return stk_uom_code;
	}

	public void setStk_uom_code(String stk_uom_code) {
		this.stk_uom_code = stk_uom_code;
	}

	public double getStk_qty() {
		return stk_qty;
	}

	public void setStk_qty(double stk_qty) {
		this.stk_qty = stk_qty;
	}

	public double getTlrnce_qty() {
		return tlrnce_qty;
	}

	public void setTlrnce_qty(double tlrnce_qty) {
		this.tlrnce_qty = tlrnce_qty;
	}

	public double getStk_tlrnce_qty() {
		return stk_tlrnce_qty;
	}

	public void setStk_tlrnce_qty(double stk_tlrnce_qty) {
		this.stk_tlrnce_qty = stk_tlrnce_qty;
	}

	@Override
	public String toString() {
		return "PRDCT_RECIPE_DTL_MST_Model [recipe_code=" + recipe_code
				+ ", rcp_measure=" + rcp_measure + ", prdct_code=" + prdct_code
				+ ", rcp_uom_code=" + rcp_uom_code + ", rcp_qty=" + rcp_qty
				+ ", rcp_cost=" + rcp_cost + ", stk_uom_code=" + stk_uom_code
				+ ", stk_qty=" + stk_qty + ", tlrnce_qty=" + tlrnce_qty
				+ ", stk_tlrnce_qty=" + stk_tlrnce_qty + "]";
	}

	public static ContentValues getcontentvalues(
			PRDCT_RECIPE_DTL_TXN_Model model) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.PRDCT_RECIPE_CODE, model.getPrdct_code());
		values.put(DBConstants.PRDCT_RCP_MEASURE, model.getRcp_measure());
		values.put(DBConstants.PRDCT_RCP_UOM_CODE, model.getRcp_uom_code());
		values.put(DBConstants.PRDCT_RCP_QTY, model.getRcp_qty());
		values.put(DBConstants.PRDCT_RECIPE_COST, model.getRcp_cost());
		values.put(DBConstants.PRDCT_STK_UOM_CODE, model.getStk_uom_code());
		values.put(DBConstants.PRDCT_STK_QTY, model.getStk_qty());
		values.put(DBConstants.PRDCT_TLRNCE_QTY, model.getTlrnce_qty());
		values.put(DBConstants.PRDCT_STK_TLRNCE_QTY, model.getStk_tlrnce_qty());
		values.put(DBConstants.PRDCT_CODE, model.getPrdct_code());
		return values;
	}

}
