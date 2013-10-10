/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
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

public class UOM_SLAB_MST_MODEL extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String prdct_Code;
	private String uom_Code;
	private String prdct_Sell_Price;
	private String prdct_Cost_Price;

	public String getPrdct_Code() {
		return prdct_Code;
	}

	public void setPrdct_Code(String prdct_Code) {
		this.prdct_Code = prdct_Code;
	}

	public String getUom_Code() {
		return uom_Code;
	}

	public void setUom_Code(String uom_Code) {
		this.uom_Code = uom_Code;
	}

	public String getPrdct_Sell_Price() {
		return prdct_Sell_Price;
	}

	public void setPrdct_Sell_Price(String prdct_Sell_Price) {
		this.prdct_Sell_Price = prdct_Sell_Price;
	}

	public String getPrdct_Cost_Price() {
		return prdct_Cost_Price;
	}

	public void setPrdct_Cost_Price(String prdct_Cost_Price) {
		this.prdct_Cost_Price = prdct_Cost_Price;
	}

	@Override
	public String toString() {
		return "UOM_SLAB_MST_MODEL [prdct_Code=" + prdct_Code + ", uom_Code="
				+ uom_Code + ", prdct_Sell_Price=" + prdct_Sell_Price
				+ ", prdct_Cost_Price=" + prdct_Cost_Price + "]";
	}

	public static ContentValues getcontentvalues(UOM_SLAB_MST_MODEL model) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.PRDCT_CODE, model.getPrdct_Code());
		values.put(DBConstants.UOM_CODE, model.getUom_Code());
		values.put(DBConstants.PRDCT_COST_PRCE, model.getPrdct_Cost_Price());
		values.put(DBConstants.PRDCT_SELL_PRCE, model.getPrdct_Sell_Price());
		return values;
	}

}
