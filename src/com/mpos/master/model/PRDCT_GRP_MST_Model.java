/*
 * Copyright (C) 2013  All Rights Reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mpos.master.model;

import java.io.Serializable;

import android.content.ContentValues;

import com.mpos.framework.common.DBConstants;

/**
 * Description-
 * 

 */
public class PRDCT_GRP_MST_Model extends BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String grp_code;
	private String grp_name;

	public String getGrp_code() {
		return grp_code;
	}

	public void setGrp_code(String grp_code) {
		this.grp_code = grp_code;
	}

	public String getGrp_name() {
		return grp_name;
	}

	public void setGrp_name(String grp_name) {
		this.grp_name = grp_name;
	}

	@Override
	public String toString() {
		return "PRDCT_GRP_MST_Model [grp_code=" + grp_code + ", grp_name="
				+ grp_name + "]";
	}

	public static ContentValues getcontentvalues(PRDCT_GRP_MST_Model model) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.GRP_CODE, model.getGrp_code());
		values.put(DBConstants.GRP_NAME, model.getGrp_name());
		return values;
	}

}
