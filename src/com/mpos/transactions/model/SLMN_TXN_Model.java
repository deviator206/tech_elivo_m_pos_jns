package com.mpos.transactions.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;

import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBConstants;

public class SLMN_TXN_Model implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String companyCode;
	private String branchCode;
	private String bill_scncd;
	private String slm_code;
	private String sys_run_date = "";
	private String till_num;
	private String username;

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBill_scncd() {
		return bill_scncd;
	}

	public void setBill_scncd(String bill_scncd) {
		this.bill_scncd = bill_scncd;
	}

	public String getSlm_code() {
		return slm_code;
	}

	public void setSlm_code(String slm_code) {
		this.slm_code = slm_code;
	}

	public String getSys_run_date() {
		return sys_run_date;
	}

	public void setSys_run_date(String sys_run_date) {
		this.sys_run_date = sys_run_date;
	}

	public String getTill_num() {
		return till_num;
	}

	public void setTill_num(String till_num) {
		this.till_num = till_num;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public static ContentValues getcontentvalues(SLMN_TXN_Model model) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.CMPNY_CODE, model.getCompanyCode());
		values.put(DBConstants.BRNCH_CODE, model.getBranchCode());
		values.put(DBConstants.BILL_SCNCD, model.getBill_scncd());
		values.put(DBConstants.Slm_Code, model.getSlm_code());
		values.put(DBConstants.Sys_run_date, model.getSys_run_date());
		values.put(DBConstants.TILL_NO, model.getTill_num());
		values.put(DBConstants.User_Name, model.getUsername());
		return values;
	}

	@Override
	public String toString() {
		return "SLMN_TXN_Model [companyCode=" + companyCode + ", branchCode="
				+ branchCode + ", bill_scncd=" + bill_scncd + ", slm_code="
				+ slm_code + ", sys_run_date=" + sys_run_date + ", till_num="
				+ till_num + ", username=" + username + "]";
	}

	/**
	 * Method- To return JSON data
	 * 
	 * @param mSlmnTxnModel
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject getJsonData(SLMN_TXN_Model mSlmnTxnModel)
			throws JSONException {
		JSONObject slsmanTxnJsonObj = new JSONObject();

		String formatRunDate = Constants.getFormattedDate(mSlmnTxnModel
				.getSys_run_date());
		slsmanTxnJsonObj.put("sysRunDate", formatRunDate);

		slsmanTxnJsonObj.put("billScncd", mSlmnTxnModel.getBill_scncd());
		slsmanTxnJsonObj.put("cmpnyCode", mSlmnTxnModel.getCompanyCode());
		slsmanTxnJsonObj.put("brnchCode", mSlmnTxnModel.getBranchCode());
		slsmanTxnJsonObj.put("tillNo", mSlmnTxnModel.getTill_num());
		slsmanTxnJsonObj.put("uploadFlg", "Y");
		slsmanTxnJsonObj.put("slmCode", mSlmnTxnModel.getSlm_code());
		slsmanTxnJsonObj.put("userName", mSlmnTxnModel.getUsername());

		return slsmanTxnJsonObj;
	}

}
