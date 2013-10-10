/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.transactions.model;

import android.content.ContentValues;

import com.mpos.framework.common.DBConstants;
import com.mpos.master.model.BaseModel;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class TempTXNModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String txn_no;
	private String isUploaded;

	public String getTxn_no() {
		return txn_no;
	}

	public void setTxn_no(String txn_no) {
		this.txn_no = txn_no;
	}

	public String getIsUploaded() {
		return isUploaded;
	}

	public void setIsUploaded(String isUploaded) {
		this.isUploaded = isUploaded;
	}

	@Override
	public String toString() {
		return "TempTXNModel [txn_no=" + txn_no + ", isUploaded=" + isUploaded
				+ "]";
	}

	public static ContentValues getcontentvalues(TempTXNModel model) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.TEMP_TXN_BILL_SCNCD, model.getTxn_no());
		values.put(DBConstants.TEMP_TXN_UPLOAD_FLAG, model.getIsUploaded());
		return values;
	}

}
