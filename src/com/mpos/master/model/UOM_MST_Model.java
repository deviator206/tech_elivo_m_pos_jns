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

public class UOM_MST_Model extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String uom_code;
	private String uom_name;
	private String uom_user_name;

	public String getUom_code() {
		return uom_code;
	}

	public void setUom_code(String uom_code) {
		this.uom_code = uom_code;
	}

	public String getUom_name() {
		return uom_name;
	}

	public void setUom_name(String uom_name) {
		this.uom_name = uom_name;
	}

	public String getUom_user_name() {
		return uom_user_name;
	}

	public void setUom_user_name(String uom_user_name) {
		this.uom_user_name = uom_user_name;
	}

	@Override
	public String toString() {
		return "UOM_MST_Model [uom_code=" + uom_code + ", uom_name=" + uom_name
				+ ", uom_user_name=" + uom_user_name + "]";
	}

	public static ContentValues getcontentvalues(UOM_MST_Model model) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.CODE, model.getUom_code());
		values.put(DBConstants.NAME, model.getUom_name());
		values.put(DBConstants.USR_NAME, model.getUom_user_name());
		return values;
	}
}
