/*
 * Copyright (C) 2013 jaggarnaut-studios Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.master.model;

import android.content.ContentValues;

import com.mpos.framework.common.DBConstants;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class SCAN_CODE_MST_Model extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double dscnt_cost_price;
	private double dscnt_price;
	private double qty;
	private String prdct_code;
	private String uom;
	private String scan_code;
	private String user_name = "";

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public double getDscnt_cost_price() {
		return dscnt_cost_price;
	}

	public void setDscnt_cost_price(double dscnt_cost_price) {
		this.dscnt_cost_price = dscnt_cost_price;
	}

	public double getDscnt_price() {
		return dscnt_price;
	}

	public void setDscnt_price(double dscnt_price) {
		this.dscnt_price = dscnt_price;
	}

	public double getQty() {
		return qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}

	public String getPrdct_code() {
		return prdct_code;
	}

	public void setPrdct_code(String prdct_code) {
		this.prdct_code = prdct_code;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getScan_code() {
		return scan_code;
	}

	public void setScan_code(String scan_code) {
		this.scan_code = scan_code;
	}

	@Override
	public String toString() {
		return "SCAN_CODE_MST_Model [dscnt_cost_price=" + dscnt_cost_price
				+ ", dscnt_price=" + dscnt_price + ", qty=" + qty
				+ ", prdct_code=" + prdct_code + ", uom=" + uom
				+ ", scan_code=" + scan_code + ", user_name=" + user_name + "]";
	}

	public static ContentValues getcontentvalues(SCAN_CODE_MST_Model model) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.SCAN_PRDCT_CODE, model.getPrdct_code());
		values.put(DBConstants.SCAN_CODE, model.getScan_code());
		values.put(DBConstants.SCAN_UOM, model.getUom());
		values.put(DBConstants.SCAN_USR_NM, model.getUser_name());
		values.put(DBConstants.SCAN_COST_DSCNT_PRCE,
				model.getDscnt_cost_price());
		values.put(DBConstants.SCAN_DSCNT_PRCE, model.getDscnt_price());
		values.put(DBConstants.SCAN_QTY, model.getQty());
		return values;
	}

}
