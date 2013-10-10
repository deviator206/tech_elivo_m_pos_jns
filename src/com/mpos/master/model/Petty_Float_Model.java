/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.master.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;

import com.mpos.application.MPOSApplication;
import com.mpos.framework.Util.UserSingleton;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBConstants;
import com.mpos.framework.common.Logger;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class Petty_Float_Model extends BaseModel {

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
	private double pty_flt_amt;
	private String pty_flt_dtls = "";
	private String pty_flt_type;
	private String user_name_scnd;
	private int pty_sr_no;
	private String transca_no;

	private String pty_flt_operator;
	private double pty_flt_exRate = 0.0;
	private double pty_flt_loc_amt = 0.0;
	private String pty_flt_curr_Abbr;

	public String getPty_flt_operator() {
		return pty_flt_operator;
	}

	public void setPty_flt_operator(String pty_flt_operator) {
		this.pty_flt_operator = pty_flt_operator;
	}

	public double getPty_flt_exRate() {
		return pty_flt_exRate;
	}

	public void setPty_flt_exRate(double pty_flt_exRate) {
		this.pty_flt_exRate = pty_flt_exRate;
	}

	public double getPty_flt_loc_amt() {
		return pty_flt_loc_amt;
	}

	public void setPty_flt_loc_amt(double pty_flt_loc_amt) {
		this.pty_flt_loc_amt = pty_flt_loc_amt;
	}

	public String getPty_flt_curr_Abbr() {
		return pty_flt_curr_Abbr;
	}

	public void setPty_flt_curr_Abbr(String pty_flt_curr_Abbr) {
		this.pty_flt_curr_Abbr = pty_flt_curr_Abbr;
	}

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

	public double getPty_flt_amt() {
		return pty_flt_amt;
	}

	public void setPty_flt_amt(double pty_flt_amt) {
		this.pty_flt_amt = pty_flt_amt;
	}

	public String getPty_flt_dtls() {
		return pty_flt_dtls;
	}

	public void setPty_flt_dtls(String pty_flt_dtls) {
		this.pty_flt_dtls = pty_flt_dtls;
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
		return "Petty_Float_Model [cmpny_code=" + cmpny_code + ", branch_code="
				+ branch_code + ", user_name=" + user_name + ", till_no="
				+ till_no + ", pty_flt_run_date=" + pty_flt_run_date
				+ ", pty_sys_date=" + pty_sys_date + ", pty_flt_amt="
				+ pty_flt_amt + ", pty_flt_dtls=" + pty_flt_dtls
				+ ", pty_flt_type=" + pty_flt_type + ", user_name_scnd="
				+ user_name_scnd + ", pty_sr_no=" + pty_sr_no + ", transca_no="
				+ transca_no + ", pty_flt_operator=" + pty_flt_operator
				+ ", pty_flt_exRate=" + pty_flt_exRate + ", pty_flt_loc_amt="
				+ pty_flt_loc_amt + ", pty_flt_curr_Abbr=" + pty_flt_curr_Abbr
				+ "]";
	}

	public static ContentValues getcontentvalues(Petty_Float_Model model) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.PF_AMT, model.getPty_flt_amt());
		values.put(DBConstants.PF_BRNCH_CODE, model.getBranch_code());
		values.put(DBConstants.PF_CMPNY_CODE, model.getCmpny_code());
		values.put(DBConstants.PF_DTLS, model.getPty_flt_dtls());
		values.put(DBConstants.PF_RUN_DATE, model.getPty_flt_run_date());
		values.put(DBConstants.PF_SYS_DATE, model.getPty_sys_date());
		values.put(DBConstants.PF_TILL_NO, model.getTill_no());
		values.put(DBConstants.PF_TXN_NO, model.getTransca_no());
		values.put(DBConstants.PF_TYPE, model.getPty_flt_type());
		values.put(DBConstants.PF_USERNM, model.getUser_name());
		values.put(DBConstants.PF_USR_NM_SCND, model.getUser_name_scnd());
		values.put(DBConstants.PTY_SR_NO, model.getPty_sr_no());

		values.put(DBConstants.PF_CURR_ABBRE, model.getPty_flt_curr_Abbr());
		values.put(DBConstants.PF_EX_RATE, model.getPty_flt_exRate());
		values.put(DBConstants.PF_LOC_AMT, model.getPty_flt_loc_amt());
		values.put(DBConstants.PF_OPRATOR, model.getPty_flt_operator());

		return values;
	}

	/**
	 * Method- To return JSON data
	 * 
	 * @param billMstModel
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject getJsonData(Petty_Float_Model pFMstModel)
			throws JSONException {
		JSONObject jsonObj = new JSONObject();

		jsonObj.put("cmpnyCode", pFMstModel.getCmpny_code());
		jsonObj.put("brnchCode", pFMstModel.getBranch_code());
		jsonObj.put("usrName", pFMstModel.getUser_name());
		jsonObj.put("tillNo", pFMstModel.getTill_no());

		Logger.d("PTY_RunDate:", ":" + pFMstModel.getPty_flt_run_date());
		Logger.d("PTY_SysDate:", ":" + pFMstModel.getPty_sys_date());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				Constants.DATE_FORMAT);

		String formatRunDate = Constants.getFormattedDate(pFMstModel
				.getPty_flt_run_date());
		jsonObj.put("ptyFltRunDate", formatRunDate);

		try {
			String formatSysDate = dateFormat.format(dateFormat
					.parse(pFMstModel.getPty_sys_date()));
			jsonObj.put("ptyFltSysDate", formatSysDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// jsonObj.put("transNo", pFMstModel.getTransca_no());
		jsonObj.put(
				"transNo",
				String.valueOf(UserSingleton.getInstance(
						MPOSApplication.getContext()).getFPPCount()));

		jsonObj.put("ptyFltCurAbbr", pFMstModel.getPty_flt_curr_Abbr());
		jsonObj.put("ptyFltCurAmnt",
				String.valueOf(pFMstModel.getPty_flt_amt()));
		jsonObj.put("ptyFltExRt",
				String.valueOf(pFMstModel.getPty_flt_exRate()));
		jsonObj.put("ptyFltOperator", pFMstModel.getPty_flt_operator());
		jsonObj.put("ptyFltLocAmnt",
				String.valueOf(pFMstModel.getPty_flt_loc_amt()));

		// jsonObj.put("ptyFltDtls", pFMstModel.getPty_flt_dtls());
		jsonObj.put("ptyFltType", pFMstModel.getPty_flt_type());
		jsonObj.put("usrNameScnd", pFMstModel.getUser_name_scnd());
		jsonObj.put("ptyMultiSrno", String.valueOf(pFMstModel.getPty_sr_no()));
		jsonObj.put("ptyFltSrNo", String.valueOf(pFMstModel.getPty_sr_no()));
		jsonObj.put("uploadFlg", "Y");

		return jsonObj;
	}

}
