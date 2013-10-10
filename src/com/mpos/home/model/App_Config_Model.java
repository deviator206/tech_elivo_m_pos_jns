/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.home.model;

import java.util.ArrayList;

import com.mpos.master.model.BaseModel;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class App_Config_Model extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Company_Dtls_Models company_Dtls_Models;
	private Till_Model till_Model;
	private ArrayList<Company_PolicyModel> cmpnyPlcyModels;

	public Company_Dtls_Models getCompany_Dtls_Models() {
		return company_Dtls_Models;
	}

	public void setCompany_Dtls_Models(Company_Dtls_Models company_Dtls_Models) {
		this.company_Dtls_Models = company_Dtls_Models;
	}

	public Till_Model getTill_Model() {
		return till_Model;
	}

	public void setTill_Model(Till_Model till_Model) {
		this.till_Model = till_Model;
	}

	public ArrayList<Company_PolicyModel> getCmpnyPlcyModels() {
		return cmpnyPlcyModels;
	}

	public void setCmpnyPlcyModels(
			ArrayList<Company_PolicyModel> cmpnyPlcyModels) {
		this.cmpnyPlcyModels = cmpnyPlcyModels;
	}

	@Override
	public String toString() {
		return "App_Config_Model [company_Dtls_Models=" + company_Dtls_Models
				+ ", till_Model=" + till_Model + ", cmpnyPlcyModels="
				+ cmpnyPlcyModels + "]";
	}

}
