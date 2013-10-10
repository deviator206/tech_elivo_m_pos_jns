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

public class PRDCT_INSTRC_MST_Model extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String prdct_code;
	private String instrctn_code;

	public String getPrdct_code() {
		return prdct_code;
	}

	public void setPrdct_code(String prdct_code) {
		this.prdct_code = prdct_code;
	}

	public String getInstrctn_code() {
		return instrctn_code;
	}

	public void setInstrctn_code(String instrctn_code) {
		this.instrctn_code = instrctn_code;
	}

	@Override
	public String toString() {
		return "PRDCT_INSTRC_MST_Model [prdct_code=" + prdct_code
				+ ", instrctn_code=" + instrctn_code + "]";
	}

	public static ContentValues getcontentvalues(PRDCT_INSTRC_MST_Model model) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.PRDCT_PRD_CODE, model.getPrdct_code());
		values.put(DBConstants.PRDCT_INSTRCTN_CODE, model.getInstrctn_code());
		return values;
	}

}
