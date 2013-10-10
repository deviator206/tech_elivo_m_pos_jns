/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.login.model;

import com.mpos.master.model.BaseModel;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class ResponseModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code = "";
	private String response = "";
	private String details = "";

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "ResponseModel [code=" + code + ", response=" + response
				+ ", details=" + details + "]";
	}

}
