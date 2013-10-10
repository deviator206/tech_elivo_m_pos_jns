/*
 * Copyright (C) 2013  All Rights Reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mpos.framework.common;

import java.util.Arrays;
import java.util.Hashtable;

/**
 * Description-
 * 

 * 
 */
public class DBOperationlParameter extends OperationParameter {

	public static final String TAG = Constants.APP_TAG
			+ DBOperationlParameter.class.getSimpleName();

	public static final int SELECT = 0;
	public static final int INSERT = 1;
	public static final int UPDATE = 2;
	public static final int DELETE = 3;
	public static final int AVAILABLE = 4;
	public static final int GET_COUNT = 5;
	public static final int DEFAULT_VEHICLE = 6;
	public static final int CLEAR_CACHE = 7;
	public static final int COPY_TABLE = 8;
	public static final int DROP_TABLE = 9;

	private int operation = SELECT; // DEFAULT operation.

	private int szQuery = 0;
	private String[] mQueryParam = null;
	private String[] mRequestedColumn = null;
	private Hashtable<String, String> htQueryParameters = new Hashtable<String, String>();

	public int getQuery() {
		return szQuery;
	}

	public void setQuery(int szQuery) {
		this.szQuery = szQuery;
	}

	public String[] getQueryParam() {
		return mQueryParam;
	}

	public void setQueryParam(String[] queryParam) {
		this.mQueryParam = queryParam;
	}

	public Hashtable<String, String> getQueryOperationalParameter() {
		return htQueryParameters;
	}

	public void setQueryOperationalParameter(
			Hashtable<String, String> htReqParameters) {
		htQueryParameters = htReqParameters;
	}

	public int getOperation() {
		return operation;
	}

	public void setOperation(int operation) {
		this.operation = operation;
	}

	public void setRequestedColumn(String[] requestedColumn) {
		this.mRequestedColumn = requestedColumn;
	}

	public String[] getRequestedColumn() {
		return mRequestedColumn;
	}

	@Override
	public String toString() {
		return "DBOperationlParameter [operation=" + operation + ", szQuery="
				+ szQuery + ", mQueryParam=" + Arrays.toString(mQueryParam)
				+ ", mRequestedColumn=" + Arrays.toString(mRequestedColumn)
				+ ", htQueryParameters=" + htQueryParameters + "]";
	}

}
