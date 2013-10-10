/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.master.model;

import android.content.ContentValues;

import com.mpos.framework.common.DBConstants;
import com.mpos.framework.common.Logger;
import com.mpos.transactions.model.Bill_INSTRCTN_TXN_Model;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class INSTRCTN_MST_Model extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String instrctn_code;
	private String instrctn_desc;

	public String getInstrctn_code() {
		return instrctn_code;
	}

	public void setInstrctn_code(String instrctn_code) {
		this.instrctn_code = instrctn_code;
	}

	public String getInstrctn_desc() {
		return instrctn_desc;
	}

	public void setInstrctn_desc(String instrctn_desc) {
		this.instrctn_desc = instrctn_desc;
	}

	@Override
	public String toString() {
		return "INSTRCTN_MST_Model [instrctn_code=" + instrctn_code
				+ ", instrctn_desc=" + instrctn_desc + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		INSTRCTN_MST_Model that = (INSTRCTN_MST_Model) o;
		if (o != null) {
			if (instrctn_code != null && that.instrctn_code != null) {
				if (this.getInstrctn_code().trim()
						.equalsIgnoreCase(that.getInstrctn_code().trim())) {
					// &&
					// this.getRow_id().trim().equalsIgnoreCase(that.getRow_id().trim())
					return true;
				}
			}
		}
		return false;
	}

	public static ContentValues getcontentvalues(INSTRCTN_MST_Model model) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.INSTRCTN_CODE, model.getInstrctn_code());
		values.put(DBConstants.INSTRCTN_DESC, model.getInstrctn_desc());
		return values;
	}

}
