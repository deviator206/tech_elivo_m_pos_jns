/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * USAP PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/**
 * 
 */
package com.mpos.master.model;

import android.content.ContentValues;

import com.mpos.framework.common.DBConstants;

/**
 * Description-
 * 
 */

public class PrdctImageMstModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String prdct_code;
	private String prdct_img;

	/**
	 * @return the prdct_code
	 */
	public String getPrdct_code() {
		return prdct_code;
	}

	/**
	 * @param prdct_code
	 *            the prdct_code to set
	 */
	public void setPrdct_code(String prdct_code) {
		this.prdct_code = prdct_code;
	}

	/**
	 * @return the prdct_img
	 */
	public String getPrdct_img() {
		return prdct_img;
	}

	/**
	 * @param prdct_img
	 *            the prdct_img to set
	 */
	public void setPrdct_img(String prdct_img) {
		this.prdct_img = prdct_img;
	}

	@Override
	public String toString() {
		return "PrdctImageMstModel [prdct_code=" + prdct_code + ", prdct_img="
				+ prdct_img + "]";
	}

	public static ContentValues getcontentvalues(PrdctImageMstModel model) {
		ContentValues values = new ContentValues();
		//byte[] bloc = Base64.decode(model.getPrdct_img(), Base64.DEFAULT);
		values.put(DBConstants.PRDCT_IMG, model.getPrdct_img());
		return values;
	}

}
