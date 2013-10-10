/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.home.model;

import com.mpos.master.model.BaseModel;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class Company_Dtls_Models extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String regType;
	private String cmpny_name;
	private String cmpny_pin;
	private String cmpny_fax;
	private String cmpny_branch_no;
	private String cmpny_regstrn_no;
	private String cmpny_Address_1;
	private String cmpny_address_2;
	private String cmpny_address_3;
	private String cmpny_tele;
	

	public String getRegType() {
		return regType;
	}

	public void setRegType(String regType) {
		this.regType = regType;
	}

	public String getCmpny_name() {
		return cmpny_name;
	}

	public void setCmpny_name(String cmpny_name) {
		this.cmpny_name = cmpny_name;
	}

	public String getCmpny_pin() {
		return cmpny_pin;
	}

	public void setCmpny_pin(String cmpny_pin) {
		this.cmpny_pin = cmpny_pin;
	}

	public String getCmpny_fax() {
		return cmpny_fax;
	}

	public void setCmpny_fax(String cmpny_fax) {
		this.cmpny_fax = cmpny_fax;
	}

	public String getCmpny_branch_no() {
		return cmpny_branch_no;
	}

	public void setCmpny_branch_no(String cmpny_branch_no) {
		this.cmpny_branch_no = cmpny_branch_no;
	}

	public String getCmpny_regstrn_no() {
		return cmpny_regstrn_no;
	}

	public void setCmpny_regstrn_no(String cmpny_regstrn_no) {
		this.cmpny_regstrn_no = cmpny_regstrn_no;
	}

	public String getCmpny_Address_1() {
		return cmpny_Address_1;
	}

	public void setCmpny_Address_1(String cmpny_Address_1) {
		this.cmpny_Address_1 = cmpny_Address_1;
	}

	public String getCmpny_address_2() {
		return cmpny_address_2;
	}

	public void setCmpny_address_2(String cmpny_address_2) {
		this.cmpny_address_2 = cmpny_address_2;
	}

	public String getCmpny_address_3() {
		return cmpny_address_3;
	}

	public void setCmpny_address_3(String cmpny_address_3) {
		this.cmpny_address_3 = cmpny_address_3;
	}

	public String getCmpny_tele() {
		return cmpny_tele;
	}

	public void setCmpny_tele(String cmpny_tele) {
		this.cmpny_tele = cmpny_tele;
	}

	@Override
	public String toString() {
		return "Company_Dtls_Models [regType=" + regType + ", cmpny_name="
				+ cmpny_name + ", cmpny_pin=" + cmpny_pin + ", cmpny_fax="
				+ cmpny_fax + ", cmpny_branch_no=" + cmpny_branch_no
				+ ", cmpny_regstrn_no=" + cmpny_regstrn_no
				+ ", cmpny_Address_1=" + cmpny_Address_1 + ", cmpny_address_2="
				+ cmpny_address_2 + ", cmpny_address_3=" + cmpny_address_3
				+ ", cmpny_tele=" + cmpny_tele + "]";
	}

}
