package com.mpos.transactions.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;

import com.mpos.framework.common.DBConstants;
import com.mpos.master.model.BILL_Mst_Model;

public class FLX_POS_TXNModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String BILL_SCNCD;
	private String BILLHEADING1;
	private String BILLHEADING2;
	private String BILLHEADING3;
	private String BILLHEADING4;
	private String BILLHEADING5;
	private String BILLHEADING6;
	private String BILLHEADING7;
	private String BILLHEADING8;

	public String getBILLHEADING1() {
		return BILLHEADING1;
	}

	public void setBILLHEADING1(String bILLHEADING1) {
		BILLHEADING1 = bILLHEADING1;
	}

	public String getBILLHEADING2() {
		return BILLHEADING2;
	}

	public void setBILLHEADING2(String bILLHEADING2) {
		BILLHEADING2 = bILLHEADING2;
	}

	public String getBILLHEADING3() {
		return BILLHEADING3;
	}

	public void setBILLHEADING3(String bILLHEADING3) {
		BILLHEADING3 = bILLHEADING3;
	}

	public String getBILLHEADING4() {
		return BILLHEADING4;
	}

	public void setBILLHEADING4(String bILLHEADING4) {
		BILLHEADING4 = bILLHEADING4;
	}

	public String getBILLHEADING5() {
		return BILLHEADING5;
	}

	public void setBILLHEADING5(String bILLHEADING5) {
		BILLHEADING5 = bILLHEADING5;
	}

	public String getBILLHEADING6() {
		return BILLHEADING6;
	}

	public void setBILLHEADING6(String bILLHEADING6) {
		BILLHEADING6 = bILLHEADING6;
	}

	public String getBILLHEADING7() {
		return BILLHEADING7;
	}

	public void setBILLHEADING7(String bILLHEADING7) {
		BILLHEADING7 = bILLHEADING7;
	}

	public String getBILLHEADING8() {
		return BILLHEADING8;
	}

	public void setBILLHEADING8(String bILLHEADING8) {
		BILLHEADING8 = bILLHEADING8;
	}

	public static ContentValues getcontentvalues(FLX_POS_TXNModel dataModel) {

		ContentValues values = new ContentValues();
		values.put(DBConstants.BILL_HD_VALUE1, dataModel.getBILLHEADING1());
		values.put(DBConstants.BILL_HD_VALUE2, dataModel.getBILLHEADING2());
		values.put(DBConstants.BILL_HD_VALUE3, dataModel.getBILLHEADING3());
		values.put(DBConstants.BILL_HD_VALUE4, dataModel.getBILLHEADING4());
		values.put(DBConstants.BILL_HD_VALUE5, dataModel.getBILLHEADING5());
		values.put(DBConstants.BILL_HD_VALUE6, dataModel.getBILLHEADING6());
		values.put(DBConstants.BILL_HD_VALUE7, dataModel.getBILLHEADING7());
		values.put(DBConstants.BILL_HD_VALUE8, dataModel.getBILLHEADING8());
		values.put(DBConstants.BILL_SCNCD, dataModel.getBILL_SCNCD());
		return values;
	}

	public String getBILL_SCNCD() {
		return BILL_SCNCD;
	}

	public void setBILL_SCNCD(String bILL_SCNCD) {
		BILL_SCNCD = bILL_SCNCD;
	}

	@Override
	public String toString() {
		return "FLX_POS_TXNModel [BILL_SCNCD=" + BILL_SCNCD + ", BILLHEADING1="
				+ BILLHEADING1 + ", BILLHEADING2=" + BILLHEADING2
				+ ", BILLHEADING3=" + BILLHEADING3 + ", BILLHEADING4="
				+ BILLHEADING4 + ", BILLHEADING5=" + BILLHEADING5
				+ ", BILLHEADING6=" + BILLHEADING6 + ", BILLHEADING7="
				+ BILLHEADING7 + ", BILLHEADING8=" + BILLHEADING8 + "]";
	}

	/**
	 * Method- To return JSON data
	 * 
	 * @param mFlxPosTxnModel
	 * 
	 * @param billMstModel
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject getJsonData(FLX_POS_TXNModel mFlxPosTxnModel,
			BILL_Mst_Model billMstModel) throws JSONException {
		JSONObject flxPosTxnJsonObj = new JSONObject();

		flxPosTxnJsonObj.put("billHdValue1", mFlxPosTxnModel.getBILLHEADING1());
		flxPosTxnJsonObj.put("billHdValue2", mFlxPosTxnModel.getBILLHEADING2());
		flxPosTxnJsonObj.put("billHdValue3", mFlxPosTxnModel.getBILLHEADING3());
		flxPosTxnJsonObj.put("billHdValue4", mFlxPosTxnModel.getBILLHEADING4());
		flxPosTxnJsonObj.put("billHdValue5", mFlxPosTxnModel.getBILLHEADING5());
		flxPosTxnJsonObj.put("billHdValue6", mFlxPosTxnModel.getBILLHEADING6());
		flxPosTxnJsonObj.put("billHdValue7", mFlxPosTxnModel.getBILLHEADING7());
		flxPosTxnJsonObj.put("billHdValue8", mFlxPosTxnModel.getBILLHEADING8());
		flxPosTxnJsonObj.put("billScncd", mFlxPosTxnModel.getBILL_SCNCD());
		flxPosTxnJsonObj.put("cmpnyCode", billMstModel.getCMPNY_CODE());
		flxPosTxnJsonObj.put("brnchCode", billMstModel.getBRNCH_CODE());
		flxPosTxnJsonObj.put("uploadFlg", "Y");

		return flxPosTxnJsonObj;
	}

}
