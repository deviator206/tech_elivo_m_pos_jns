/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.home.model;

import android.content.ContentValues;

import com.mpos.framework.common.DBConstants;
import com.mpos.master.model.BaseModel;
import com.mpos.payment.model.MULTY_PMT_MST_Model;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class Company_PolicyModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String reg_type;
	private String reg_name;
	private String cmpny_ply;
	private String cmpny_ply_value;

	public String getReg_name() {
		return reg_name;
	}

	public void setReg_name(String reg_name) {
		this.reg_name = reg_name;
	}

	public String getReg_type() {
		return reg_type;
	}

	public void setReg_type(String reg_type) {
		this.reg_type = reg_type;
	}

	public String getCmpny_ply() {
		return cmpny_ply;
	}

	public void setCmpny_ply(String cmpny_ply) {
		this.cmpny_ply = cmpny_ply;
	}

	public String getCmpny_ply_value() {
		return cmpny_ply_value;
	}

	public void setCmpny_ply_value(String cmpny_ply_value) {
		this.cmpny_ply_value = cmpny_ply_value;
	}

	@Override
	public String toString() {
		return "Company_PolicyModel [reg_type=" + reg_type + ", reg_name="
				+ reg_name + ", cmpny_ply=" + cmpny_ply + ", cmpny_ply_value="
				+ cmpny_ply_value + "]";
	}

	public static ContentValues getcontentvalues(Company_PolicyModel model) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.CONFIG_REG_TYPE, model.getReg_type());
		values.put(DBConstants.CONFIG_REG_NAME, model.getCmpny_ply());
		values.put(DBConstants.CONFIG_REG_VALUE, model.getCmpny_ply_value());
		values.put(DBConstants.CONFIG_REG_EXT_VALUE, "");
		return values;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		Company_PolicyModel that = (Company_PolicyModel) o;
		if (this.getCmpny_ply().trim()
				.equalsIgnoreCase(that.getCmpny_ply().trim())) {
			return true;
		}
		return false;
	}
}
