/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * USAP PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mpos.framework.common;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;

/**
 * Description-
 * 
 * @author vinayakh
 * 
 */
public class NTOperationalParameter extends OperationParameter {

	public static final String TAG = "NTOperationalParameter";

	private String mszURL = null;
	private Hashtable<String, String> mhtRequestParameters = new Hashtable<String, String>();
	// private Map<String, String> mhtUrlParameters = new LinkedHashMap<String,
	// String>();
	private LinkedHashMap<String, String> mhtMethodParameters = new LinkedHashMap<String, String>();

	private List<NameValuePair> mhtUrlParameters = new LinkedList<NameValuePair>();

	public String getMszURL() {
		return mszURL;
	}

	public void setMszURL(String mszURL) {
		this.mszURL = mszURL;
	}

	public Hashtable<String, String> getMhtRequestParameters() {
		return mhtRequestParameters;
	}

	public void setMhtRequestParameters(
			Hashtable<String, String> mhtRequestParameters) {
		this.mhtRequestParameters = mhtRequestParameters;
	}

	public List<NameValuePair> getMhtUrlParameters() {
		return mhtUrlParameters;
	}

	public void setMhtUrlParameters(List<NameValuePair> mhtUrlParameters) {
		this.mhtUrlParameters = mhtUrlParameters;
	}

	public LinkedHashMap<String, String> getMhtMethodParameters() {
		return mhtMethodParameters;
	}

	public void setMhtMethodParameters(
			LinkedHashMap<String, String> mhtMethodParameters) {
		this.mhtMethodParameters = mhtMethodParameters;
	}

	@Override
	public String toString() {
		return "NTOperationalParameter [mszURL=" + mszURL
				+ ", mhtRequestParameters=" + mhtRequestParameters
				+ ", mhtUrlParameters=" + mhtUrlParameters
				+ ", mhtMethodParameters=" + mhtMethodParameters + "]";
	}
	

}