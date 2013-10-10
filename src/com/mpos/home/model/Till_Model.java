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

public class Till_Model extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String cmpny_code;
	private String till_no;
	private String reg_type;
	private String mchne_name;
	private String max_zed_no;
	private String run_date;
	private String sql_db_name;
	private String sql_server_name;
	private String default_branch_code;
	private String curr_shift_Code;
	private String next_shift_code;
	
	private String port;
	private String port_type;
	private String printer_type;
	private String printer_model;
	

	
	

	public String getCmpny_code() {
		return cmpny_code;
	}

	public void setCmpny_code(String cmpny_code) {
		this.cmpny_code = cmpny_code;
	}

	public String getTill_no() {
		return till_no;
	}

	public void setTill_no(String till_no) {
		this.till_no = till_no;
	}

	public String getReg_type() {
		return reg_type;
	}

	public void setReg_type(String reg_type) {
		this.reg_type = reg_type;
	}

	public String getMchne_name() {
		return mchne_name;
	}

	public void setMchne_name(String mchne_name) {
		this.mchne_name = mchne_name;
	}

	public String getMax_zed_no() {
		return max_zed_no;
	}

	public void setMax_zed_no(String max_zed_no) {
		this.max_zed_no = max_zed_no;
	}

	public String getRun_date() {
		return run_date;
	}

	public void setRun_date(String run_date) {
		this.run_date = run_date;
	}

	public String getSql_db_name() {
		return sql_db_name;
	}

	public void setSql_db_name(String sql_db_name) {
		this.sql_db_name = sql_db_name;
	}

	public String getSql_server_name() {
		return sql_server_name;
	}

	public void setSql_server_name(String sql_server_name) {
		this.sql_server_name = sql_server_name;
	}

	public String getDefault_branch_code() {
		return default_branch_code;
	}

	public void setDefault_branch_code(String default_branch_code) {
		this.default_branch_code = default_branch_code;
	}

	public String getCurr_shift_Code() {
		return curr_shift_Code;
	}

	public void setCurr_shift_Code(String curr_shift_Code) {
		this.curr_shift_Code = curr_shift_Code;
	}

	public String getNext_shift_code() {
		return next_shift_code;
	}

	public void setNext_shift_code(String next_shift_code) {
		this.next_shift_code = next_shift_code;
	}

	@Override
	public String toString() {
		return "Till_Model [cmpny_code=" + cmpny_code + ", till_no=" + till_no
				+ ", reg_type=" + reg_type + ", mchne_name=" + mchne_name
				+ ", max_zed_no=" + max_zed_no + ", run_date=" + run_date
				+ ", sql_db_name=" + sql_db_name + ", sql_server_name="
				+ sql_server_name + ", default_branch_code="
				+ default_branch_code + ", curr_shift_Code=" + curr_shift_Code
				+ ", next_shift_code=" + next_shift_code + "]";
	}
	
	

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getPort_type() {
		return port_type;
	}

	public void setPort_type(String port_type) {
		this.port_type = port_type;
	}

	public String getPrinter_type() {
		return printer_type;
	}

	public void setPrinter_type(String printer_type) {
		this.printer_type = printer_type;
	}

	public String getPrinter_model() {
		return printer_model;
	}

	public void setPrinter_model(String printer_model) {
		this.printer_model = printer_model;
	}

}
