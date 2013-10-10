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
public class PRDCT_BINLOC_MST_Model extends BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String binloc_code;
	private String binloc_name;
	private String fk_analys_code;

	public String getBinloc_code() {
		return binloc_code;
	}

	public void setBinloc_code(String binloc_code) {
		this.binloc_code = binloc_code;
	}

	public String getBinloc_name() {
		return binloc_name;
	}

	public void setBinloc_name(String binloc_name) {
		this.binloc_name = binloc_name;
	}

	public String getFk_analys_code() {
		return fk_analys_code;
	}

	public void setFk_analys_code(String fk_analys_code) {
		this.fk_analys_code = fk_analys_code;
	}

	@Override
	public String toString() {
		return "PRDCT_BINLOC_MST_Model [binloc_code=" + binloc_code
				+ ", binloc_name=" + binloc_name + ", fk_analys_code="
				+ fk_analys_code + "]";
	}

	public static ContentValues getcontentvalues(PRDCT_BINLOC_MST_Model model) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.BINLOC_CODE, model.getBinloc_code());
		values.put(DBConstants.BINLOC_NAME, model.getBinloc_name());
		values.put(DBConstants.BINLOC_FK_ANALYS_CODE, model.getFk_analys_code());
		return values;
	}

}
