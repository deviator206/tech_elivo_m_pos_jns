/*
 * Copyright (C) 2013 jaggarnaut-studios Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.master.model;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class CRDCT_CARD_MST_Model extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String cc_code;
	private String cc_name;
	private String cc_cmsn;

	public String getCc_code() {
		return cc_code;
	}

	public void setCc_code(String cc_code) {
		this.cc_code = cc_code;
	}

	public String getCc_name() {
		return cc_name;
	}

	public void setCc_name(String cc_name) {
		this.cc_name = cc_name;
	}

	public String getCc_cmsn() {
		return cc_cmsn;
	}

	public void setCc_cmsn(String cc_cmsn) {
		this.cc_cmsn = cc_cmsn;
	}

	@Override
	public String toString() {
		return "CRDCT_CARD_MST_Model [cc_code=" + cc_code + ", cc_name="
				+ cc_name + ", cc_cmsn=" + cc_cmsn + "]";
	}

}
