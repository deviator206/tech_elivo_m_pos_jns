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

public class VAT_MST_Model extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String vat_code;
	private double vat_prcnt;
	private String vat_user_name = "";

	public String getVat_user_name() {
		return vat_user_name;
	}

	public void setVat_user_name(String vat_user_name) {
		this.vat_user_name = vat_user_name;
	}

	public String getVat_code() {
		return vat_code;
	}

	public void setVat_code(String vat_code) {
		this.vat_code = vat_code;
	}

	public double getVat_prcnt() {
		return vat_prcnt;
	}

	public void setVat_prcnt(double vat_prcnt) {
		this.vat_prcnt = vat_prcnt;
	}

	@Override
	public String toString() {
		return "VAT_MST_Model [vat_code=" + vat_code + ", vat_prcnt="
				+ vat_prcnt + ", vat_user_name=" + vat_user_name + "]";
	}

	public static ContentValues getcontentvalues(VAT_MST_Model model) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.VAT_CODE, model.getVat_code());
		values.put(DBConstants.VAT_PRCNT, model.getVat_prcnt());
		values.put(DBConstants.USR_NAME, model.getVat_user_name());
		return values;
	}

}
