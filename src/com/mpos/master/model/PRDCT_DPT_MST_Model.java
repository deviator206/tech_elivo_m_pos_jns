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
public class PRDCT_DPT_MST_Model extends BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String dpt_code;
	private String dpt_name;
	private String fk_grp_code;

	public String getDpt_code() {
		return dpt_code;
	}

	public void setDpt_code(String dpt_code) {
		this.dpt_code = dpt_code;
	}

	public String getDpt_name() {
		return dpt_name;
	}

	public void setDpt_name(String dpt_name) {
		this.dpt_name = dpt_name;
	}

	public String getFk_grp_code() {
		return fk_grp_code;
	}

	public void setFk_grp_code(String fk_grp_code) {
		this.fk_grp_code = fk_grp_code;
	}

	@Override
	public String toString() {
		return "PRDCT_DPT_MST_Model [dpt_code=" + dpt_code + ", dpt_name="
				+ dpt_name + ", fk_grp_code=" + fk_grp_code + "]";
	}

	public static ContentValues getcontentvalues(PRDCT_DPT_MST_Model model) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.DPT_CODE, model.getDpt_code());
		values.put(DBConstants.DPT_NAME, model.getDpt_name());
		values.put(DBConstants.DPT_FK_GRP_CODE, model.getFk_grp_code());
		return values;
	}

}
