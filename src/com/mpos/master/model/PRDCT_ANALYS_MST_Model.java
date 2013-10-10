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
public class PRDCT_ANALYS_MST_Model extends BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String analys_code;
	private String analys_name;
	private String fk_dpt_code;

	public String getAnalys_code() {
		return analys_code;
	}

	public void setAnalys_code(String analys_code) {
		this.analys_code = analys_code;
	}

	public String getAnalys_name() {
		return analys_name;
	}

	public void setAnalys_name(String analys_name) {
		this.analys_name = analys_name;
	}

	public String getFk_dpt_code() {
		return fk_dpt_code;
	}

	public void setFk_dpt_code(String fk_dpt_code) {
		this.fk_dpt_code = fk_dpt_code;
	}

	@Override
	public String toString() {
		return "PRDCT_ANALYS_MST_Model [analys_code=" + analys_code
				+ ", analys_name=" + analys_name + ", fk_dpt_code="
				+ fk_dpt_code + "]";
	}

	public static ContentValues getcontentvalues(PRDCT_ANALYS_MST_Model model) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.ANALYS_CODE, model.getAnalys_code());
		values.put(DBConstants.ANALYA_NAME, model.getAnalys_name());
		values.put(DBConstants.ANALYS_FK_DPT_CODE, model.getFk_dpt_code());
		return values;
	}
}
