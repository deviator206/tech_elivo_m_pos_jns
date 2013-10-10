/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * USAP PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mpos.framework.common;

import java.io.Serializable;

import android.os.Bundle;

/**
 * Description-
 * 
 * 
 */
public class OperationalResult implements Serializable {

	public static final String TAG = Constants.APP_TAG
			+ OperationalResult.class.getSimpleName();

	private static final long serialVersionUID = 1L;
	public static final int SUCCESS = 0;
	public static final int WARN = 1;
	public static final int ERROR = 2;
	public static final int DB_ERROR = 3;
	public static final int NETWORK_ERROR = 4;
	public static final int MASTER_ERROR = 5;
	
	public static final int DATA_PROTOCOL_ERROR = 10;
	
	private String apiName="default";
	

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	private Object dBModel = null;
	private String tableName;

	private DBOperationlParameter mDBDbOperationlParameter = null;
	private NTOperationalParameter mNTOperationalParameter = null;

	private Bundle mBundle = new Bundle();
	private StringBuffer message = new StringBuffer();

	// This is the code which tells about response belongs to which request.
	private int mResultCode = 0;

	// This is the code which tells about which DB operation in progress.
	private int operationalCode = 0;

	// This is the code which tells about successful error_code returned in WS
	// response.
	private int mResponseErrorCode = SUCCESS; // By Default Success
												// Code

	// This is the code which tells about ?.
	private int mRequestResponseCode = SUCCESS;// By Default Success
												// Code

	public int getRequestResponseCode() {
		return mRequestResponseCode;
	}

	public void setRequestResponseCode(int mRequestResponseCode) {
		this.mRequestResponseCode = mRequestResponseCode;
	}

	public int getResponseErrorCode() {
		return mResponseErrorCode;
	}

	public void setResponseErrorCode(int mResponseErrorCode) {
		this.mResponseErrorCode = mResponseErrorCode;
	}

	public String getMessage() {
		return message.toString();
	}

	public int getOperationalCode() {
		return operationalCode;
	}

	public Bundle getResult() {
		return mBundle;
	}

	public void setMessage(String message) {

		this.message.append(message);
		if (this.message.length() > 0)
			this.message.append("\n");
	}

	public void setOperationalCode(int opCode) {
		operationalCode = opCode;
	}

	public void setResult(Bundle bundle) {
		mBundle = bundle;
	}

	public int getResultCode() {
		return mResultCode;
	}

	public void setResultCode(int resultCode) {
		mResultCode = resultCode;
	}

	public DBOperationlParameter getDBOperationalParam() {
		return mDBDbOperationlParameter;
	}

	public void setDBOperationalParam(
			DBOperationlParameter dbOperationlParameter) {
		this.mDBDbOperationlParameter = dbOperationlParameter;
	}

	public NTOperationalParameter getNTOperationalParam() {
		return mNTOperationalParameter;
	}

	public void setNTOperationalParam(
			NTOperationalParameter ntOperationalParameter) {
		this.mNTOperationalParameter = ntOperationalParameter;
	}

	public Object getdBModel() {
		return dBModel;
	}

	public void setdBModel(Object dBModel) {
		this.dBModel = dBModel;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String toString() {
		return "OperationalResult [dBModel=" + dBModel + ", tableName="
				+ tableName + ", mDBDbOperationlParameter="
				+ mDBDbOperationlParameter + ", mNTOperationalParameter="
				+ mNTOperationalParameter + ", mBundle=" + mBundle
				+ ", message=" + message + ", mResultCode=" + mResultCode
				+ ", operationalCode=" + operationalCode
				+ ", mResponseErrorCode=" + mResponseErrorCode
				+ ", mRequestResponseCode=" + mRequestResponseCode + "]";
	}

}
