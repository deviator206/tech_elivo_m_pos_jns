/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.master.model;

import android.content.ContentValues;

import com.mpos.framework.common.DBConstants;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class MULTI_PTY_FLT_MST_Model extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String cmpny_code;
	private String branch_code;
	private String user_name;
	private String till_no;
	private String pty_flt_run_date;
	private String pty_sys_date;

	private String cur_abbr;
	private double curAmt;
	private double ex_rate;
	private String ex_operator;
	private double loc_amt;

	private String pty_flt_type;
	private String user_name_scnd;
	private int pty_sr_no;
	private String transca_no;

	public String getCmpny_code() {
		return cmpny_code;
	}

	public void setCmpny_code(String cmpny_code) {
		this.cmpny_code = cmpny_code;
	}

	public String getBranch_code() {
		return branch_code;
	}

	public void setBranch_code(String branch_code) {
		this.branch_code = branch_code;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getTill_no() {
		return till_no;
	}

	public void setTill_no(String till_no) {
		this.till_no = till_no;
	}

	public String getPty_flt_run_date() {
		return pty_flt_run_date;
	}

	public void setPty_flt_run_date(String pty_flt_run_date) {
		this.pty_flt_run_date = pty_flt_run_date;
	}

	public String getPty_sys_date() {
		return pty_sys_date;
	}

	public void setPty_sys_date(String pty_sys_date) {
		this.pty_sys_date = pty_sys_date;
	}

	public String getCur_abbr() {
		return cur_abbr;
	}

	public void setCur_abbr(String cur_abbr) {
		this.cur_abbr = cur_abbr;
	}

	public double getCurAmt() {
		return curAmt;
	}

	public void setCurAmt(double curAmt) {
		this.curAmt = curAmt;
	}

	public double getEx_rate() {
		return ex_rate;
	}

	public void setEx_rate(double ex_rate) {
		this.ex_rate = ex_rate;
	}

	public String getEx_operator() {
		return ex_operator;
	}

	public void setEx_operator(String ex_operator) {
		this.ex_operator = ex_operator;
	}

	public double getLoc_amt() {
		return loc_amt;
	}

	public void setLoc_amt(double loc_amt) {
		this.loc_amt = loc_amt;
	}

	public String getPty_flt_type() {
		return pty_flt_type;
	}

	public void setPty_flt_type(String pty_flt_type) {
		this.pty_flt_type = pty_flt_type;
	}

	public String getUser_name_scnd() {
		return user_name_scnd;
	}

	public void setUser_name_scnd(String user_name_scnd) {
		this.user_name_scnd = user_name_scnd;
	}

	public int getPty_sr_no() {
		return pty_sr_no;
	}

	public void setPty_sr_no(int pty_sr_no) {
		this.pty_sr_no = pty_sr_no;
	}

	public String getTransca_no() {
		return transca_no;
	}

	public void setTransca_no(String transca_no) {
		this.transca_no = transca_no;
	}

	@Override
	public String toString() {
		return "MULTI_PTY_FLT_MST_Model [cmpny_code=" + cmpny_code
				+ ", branch_code=" + branch_code + ", user_name=" + user_name
				+ ", till_no=" + till_no + ", pty_flt_run_date="
				+ pty_flt_run_date + ", pty_sys_date=" + pty_sys_date
				+ ", cur_abbr=" + cur_abbr + ", curAmt=" + curAmt
				+ ", ex_rate=" + ex_rate + ", ex_operator=" + ex_operator
				+ ", loc_amt=" + loc_amt + ", pty_flt_type=" + pty_flt_type
				+ ", user_name_scnd=" + user_name_scnd + ", pty_sr_no="
				+ pty_sr_no + ", transca_no=" + transca_no + "]";
	}

	public static ContentValues getcontentvalues(MULTI_PTY_FLT_MST_Model model) {
		ContentValues values = new ContentValues();

		values.put(DBConstants.MPF_BRNCH_CODE, model.getBranch_code());
		values.put(DBConstants.MPF_CMPNY_CODE, model.getCmpny_code());
		values.put(DBConstants.MPF_RUN_DATE, model.getPty_flt_run_date());
		values.put(DBConstants.MPF_SYS_DATE, model.getPty_sys_date());
		values.put(DBConstants.MPF_TILL_NO, model.getTill_no());
		values.put(DBConstants.MPF_TXN_NO, model.getTransca_no());
		values.put(DBConstants.MPF_TYPE, model.getPty_flt_type());
		values.put(DBConstants.MPF_USERNM, model.getUser_name());
		values.put(DBConstants.MPF_USR_NM_SCND, model.getUser_name_scnd());
		values.put(DBConstants.MPTY_SR_NO, model.getPty_sr_no());

		values.put(DBConstants.MPF_PTY_FLT_CUR_ABBR, model.getCur_abbr());
		values.put(DBConstants.MPF_PTY_FLT_OPERATOR, model.getEx_operator());
		values.put(DBConstants.MPF_PTY_FLT_CUR_AMT, model.getCurAmt());
		values.put(DBConstants.MPF_PTY_FLT_EX_RT, model.getEx_rate());
		values.put(DBConstants.MPF_PTY_FLT_LOC_AMT, model.getLoc_amt());

		return values;
	}

}
