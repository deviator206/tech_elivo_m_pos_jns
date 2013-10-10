/*
 * Copyright (C) 2013  All Rights Reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

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

/**
 * Description-
 * 

 */

public class HeldTransactionModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String CMPNY_CODE;
	private String BRNCH_CODE;
	private String BILL_SCNCD;
	private String TXN_TYPE;
	private String BILL_RUN_DATE;
	private String PRDCT_CODE;
	private String PRDCT_LNG_DSCRPTN;
	private double PRDCT_PRC;
	private double PRDCT_QNTY;
	private double PRDCT_DSCNT;
	private double PRDCT_AMNT;
	private double PRDCT_PYMNT_MODE_DSCNT;
	private String PRDCT_VAT_CODE;
	private double PRDCT_VAT_AMNT;
	private String PRDCT_VOID;
	private String USR_ANTHNTCTD;
	private String PRDCT_SCND;
	private String SLS_MAN_CODE;
	private String BILL_PRNTD;
	private String PRDCT_VAT_EXMPT;
	private double PRDCT_COST_PRCE;
	private String AUTO_GRN_DONE;
	private String EMPTY_DONE;
	private double ZED_NO;
	private int SR_NO;
	private String PACK;
	private double PACKQTY;
	private double PACKPRCE;
	private double LINEDISC;
	private String HELD_USR;
	private String SCAN_CODE;

	public String getCMPNY_CODE() {
		return CMPNY_CODE;
	}

	public void setCMPNY_CODE(String cMPNY_CODE) {
		CMPNY_CODE = cMPNY_CODE;
	}

	public String getBRNCH_CODE() {
		return BRNCH_CODE;
	}

	public void setBRNCH_CODE(String bRNCH_CODE) {
		BRNCH_CODE = bRNCH_CODE;
	}

	public String getBILL_SCNCD() {
		return BILL_SCNCD;
	}

	public void setBILL_SCNCD(String bILL_SCNCD) {
		BILL_SCNCD = bILL_SCNCD;
	}

	public String getTXN_TYPE() {
		return TXN_TYPE;
	}

	public void setTXN_TYPE(String tXN_TYPE) {
		TXN_TYPE = tXN_TYPE;
	}

	public String getBILL_RUN_DATE() {
		return BILL_RUN_DATE;
	}

	public void setBILL_RUN_DATE(String bILL_RUN_DATE) {
		BILL_RUN_DATE = bILL_RUN_DATE;
	}

	public String getPRDCT_CODE() {
		return PRDCT_CODE;
	}

	public void setPRDCT_CODE(String pRDCT_CODE) {
		PRDCT_CODE = pRDCT_CODE;
	}

	public String getPRDCT_LNG_DSCRPTN() {
		return PRDCT_LNG_DSCRPTN;
	}

	public void setPRDCT_LNG_DSCRPTN(String pRDCT_LNG_DSCRPTN) {
		PRDCT_LNG_DSCRPTN = pRDCT_LNG_DSCRPTN;
	}

	public double getPRDCT_PRC() {
		return PRDCT_PRC;
	}

	public void setPRDCT_PRC(double pRDCT_PRC) {
		PRDCT_PRC = pRDCT_PRC;
	}

	public double getPRDCT_QNTY() {
		return PRDCT_QNTY;
	}

	public void setPRDCT_QNTY(double pRDCT_QNTY) {
		PRDCT_QNTY = pRDCT_QNTY;
	}

	public double getPRDCT_DSCNT() {
		return PRDCT_DSCNT;
	}

	public void setPRDCT_DSCNT(double pRDCT_DSCNT) {
		PRDCT_DSCNT = pRDCT_DSCNT;
	}

	public double getPRDCT_AMNT() {
		return PRDCT_AMNT;
	}

	public void setPRDCT_AMNT(double pRDCT_AMNT) {
		PRDCT_AMNT = pRDCT_AMNT;
	}

	public double getPRDCT_PYMNT_MODE_DSCNT() {
		return PRDCT_PYMNT_MODE_DSCNT;
	}

	public void setPRDCT_PYMNT_MODE_DSCNT(double pRDCT_PYMNT_MODE_DSCNT) {
		PRDCT_PYMNT_MODE_DSCNT = pRDCT_PYMNT_MODE_DSCNT;
	}

	public String getPRDCT_VAT_CODE() {
		return PRDCT_VAT_CODE;
	}

	public void setPRDCT_VAT_CODE(String pRDCT_VAT_CODE) {
		PRDCT_VAT_CODE = pRDCT_VAT_CODE;
	}

	public double getPRDCT_VAT_AMNT() {
		return PRDCT_VAT_AMNT;
	}

	public void setPRDCT_VAT_AMNT(double pRDCT_VAT_AMNT) {
		PRDCT_VAT_AMNT = pRDCT_VAT_AMNT;
	}

	public String getPRDCT_VOID() {
		return PRDCT_VOID;
	}

	public void setPRDCT_VOID(String pRDCT_VOID) {
		PRDCT_VOID = pRDCT_VOID;
	}

	public String getUSR_ANTHNTCTD() {
		return USR_ANTHNTCTD;
	}

	public void setUSR_ANTHNTCTD(String uSR_ANTHNTCTD) {
		USR_ANTHNTCTD = uSR_ANTHNTCTD;
	}

	public String getPRDCT_SCND() {
		return PRDCT_SCND;
	}

	public void setPRDCT_SCND(String pRDCT_SCND) {
		PRDCT_SCND = pRDCT_SCND;
	}

	public String getSLS_MAN_CODE() {
		return SLS_MAN_CODE;
	}

	public void setSLS_MAN_CODE(String sLS_MAN_CODE) {
		SLS_MAN_CODE = sLS_MAN_CODE;
	}

	public String getBILL_PRNTD() {
		return BILL_PRNTD;
	}

	public void setBILL_PRNTD(String bILL_PRNTD) {
		BILL_PRNTD = bILL_PRNTD;
	}

	public String getPRDCT_VAT_EXMPT() {
		return PRDCT_VAT_EXMPT;
	}

	public void setPRDCT_VAT_EXMPT(String pRDCT_VAT_EXMPT) {
		PRDCT_VAT_EXMPT = pRDCT_VAT_EXMPT;
	}

	public double getPRDCT_COST_PRCE() {
		return PRDCT_COST_PRCE;
	}

	public void setPRDCT_COST_PRCE(double pRDCT_COST_PRCE) {
		PRDCT_COST_PRCE = pRDCT_COST_PRCE;
	}

	public String getAUTO_GRN_DONE() {
		return AUTO_GRN_DONE;
	}

	public void setAUTO_GRN_DONE(String aUTO_GRN_DONE) {
		AUTO_GRN_DONE = aUTO_GRN_DONE;
	}

	public String getEMPTY_DONE() {
		return EMPTY_DONE;
	}

	public void setEMPTY_DONE(String eMPTY_DONE) {
		EMPTY_DONE = eMPTY_DONE;
	}

	public double getZED_NO() {
		return ZED_NO;
	}

	public void setZED_NO(double zED_NO) {
		ZED_NO = zED_NO;
	}

	/**
	 * @return the sR_NO
	 */
	public int getSR_NO() {
		return SR_NO;
	}

	/**
	 * @param sR_NO
	 *            the sR_NO to set
	 */
	public void setSR_NO(int sR_NO) {
		SR_NO = sR_NO;
	}

	public String getPACK() {
		return PACK;
	}

	public void setPACK(String pACK) {
		PACK = pACK;
	}

	public double getPACKQTY() {
		return PACKQTY;
	}

	public void setPACKQTY(double pACKQTY) {
		PACKQTY = pACKQTY;
	}

	public double getPACKPRCE() {
		return PACKPRCE;
	}

	public void setPACKPRCE(double pACKPRCE) {
		PACKPRCE = pACKPRCE;
	}

	public double getLINEDISC() {
		return LINEDISC;
	}

	public void setLINEDISC(double lINEDISC) {
		LINEDISC = lINEDISC;
	}

	public String getHELD_USR() {
		return HELD_USR;
	}

	public void setHELD_USR(String hELD_USR) {
		HELD_USR = hELD_USR;
	}

	public String getSCAN_CODE() {
		return SCAN_CODE;
	}

	public void setSCAN_CODE(String sCAN_CODE) {
		SCAN_CODE = sCAN_CODE;
	}

	@Override
	public String toString() {
		return "HeldTransactionModel [CMPNY_CODE=" + CMPNY_CODE
				+ ", BRNCH_CODE=" + BRNCH_CODE + ", BILL_SCNCD=" + BILL_SCNCD
				+ ", TXN_TYPE=" + TXN_TYPE + ", BILL_RUN_DATE=" + BILL_RUN_DATE
				+ ", PRDCT_CODE=" + PRDCT_CODE + ", PRDCT_LNG_DSCRPTN="
				+ PRDCT_LNG_DSCRPTN + ", PRDCT_PRC=" + PRDCT_PRC
				+ ", PRDCT_QNTY=" + PRDCT_QNTY + ", PRDCT_DSCNT=" + PRDCT_DSCNT
				+ ", PRDCT_AMNT=" + PRDCT_AMNT + ", PRDCT_PYMNT_MODE_DSCNT="
				+ PRDCT_PYMNT_MODE_DSCNT + ", PRDCT_VAT_CODE=" + PRDCT_VAT_CODE
				+ ", PRDCT_VAT_AMNT=" + PRDCT_VAT_AMNT + ", PRDCT_VOID="
				+ PRDCT_VOID + ", USR_ANTHNTCTD=" + USR_ANTHNTCTD
				+ ", PRDCT_SCND=" + PRDCT_SCND + ", SLS_MAN_CODE="
				+ SLS_MAN_CODE + ", BILL_PRNTD=" + BILL_PRNTD
				+ ", PRDCT_VAT_EXMPT=" + PRDCT_VAT_EXMPT + ", PRDCT_COST_PRCE="
				+ PRDCT_COST_PRCE + ", AUTO_GRN_DONE=" + AUTO_GRN_DONE
				+ ", EMPTY_DONE=" + EMPTY_DONE + ", ZED_NO=" + ZED_NO
				+ ", SR_NO=" + SR_NO + ", PACK=" + PACK + ", PACKQTY="
				+ PACKQTY + ", PACKPRCE=" + PACKPRCE + ", LINEDISC=" + LINEDISC
				+ ", HELD_USR=" + HELD_USR + ", SCAN_CODE=" + SCAN_CODE + "]";
	}

	public static ContentValues getcontentvalues(HeldTransactionModel model) {
		ContentValues values = new ContentValues();

		values.put(DBConstants.AUTO_GRN_DONE, model.getAUTO_GRN_DONE());
		values.put(DBConstants.BILL_PRNTD, model.getBILL_PRNTD());
		values.put(DBConstants.BILL_RUN_DATE, model.getBILL_RUN_DATE());
		values.put(DBConstants.BILL_SCNCD, model.getBILL_SCNCD());
		values.put(DBConstants.BRNCH_CODE, model.getBRNCH_CODE());
		values.put(DBConstants.CMPNY_CODE, model.getCMPNY_CODE());
		values.put(DBConstants.EMPTY_DONE, model.getEMPTY_DONE());
		values.put(DBConstants.HELD_USR, model.getHELD_USR());
		values.put(DBConstants.PACK, model.getPACK());
		values.put(DBConstants.PRDCT_CODE, model.getPRDCT_CODE());
		values.put(DBConstants.PRDCT_LNG_DSCRPTN, model.getPRDCT_LNG_DSCRPTN());
		values.put(DBConstants.PRDCT_SCND, model.getPRDCT_SCND());
		values.put(DBConstants.PRDCT_VAT_CODE, model.getPRDCT_VAT_CODE());
		values.put(DBConstants.PRDCT_VAT_EXMPT, model.getPRDCT_VAT_EXMPT());
		values.put(DBConstants.PRDCT_VOID, model.getPRDCT_VOID());
		values.put(DBConstants.SCAN_CODE, model.getSCAN_CODE());
		values.put(DBConstants.SLS_MAN_CODE, model.getSLS_MAN_CODE());
		values.put(DBConstants.SR_NO, model.getSR_NO());
		values.put(DBConstants.TXN_TYPE, model.getTXN_TYPE());
		values.put(DBConstants.USR_ANTHNTCTD, model.getUSR_ANTHNTCTD());
		values.put(DBConstants.LINEDISC, model.getLINEDISC());
		values.put(DBConstants.PACKPRCE, model.getPACKPRCE());
		values.put(DBConstants.PACKQTY, model.getPACKQTY());
		values.put(DBConstants.PRDCT_AMNT, model.getPRDCT_AMNT());
		values.put(DBConstants.PRDCT_COST_PRCE, model.getPRDCT_COST_PRCE());
		values.put(DBConstants.PRDCT_DSCNT, model.getPRDCT_DSCNT());
		values.put(DBConstants.PRDCT_PRC, model.getPRDCT_PRC());
		values.put(DBConstants.PRDCT_PYMNT_MODE_DSCNT,
				model.getPRDCT_PYMNT_MODE_DSCNT());
		values.put(DBConstants.PRDCT_QNTY, model.getPRDCT_QNTY());
		values.put(DBConstants.PRDCT_VAT_AMNT, model.getPRDCT_VAT_AMNT());
		values.put(DBConstants.ZED_NO, model.getZED_NO());

		return values;
	}

	/**
	 * Method- To return JSON data
	 * 
	 * @param multy_PMT_MST_Model
	 * 
	 * @param billMstModel
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject getJsonData(
			HeldTransactionModel heldTransactionModel) throws JSONException {
		JSONObject heldTxnJsonObj = new JSONObject();

		String formatRunDate = Constants.getFormattedDate(heldTransactionModel
				.getBILL_RUN_DATE());
		heldTxnJsonObj.put("billRunDate", formatRunDate);

		heldTxnJsonObj.put("prdctLngDscrptn",
				heldTransactionModel.getPRDCT_LNG_DSCRPTN());
		heldTxnJsonObj.put("prdctDscnt", heldTransactionModel.getPRDCT_DSCNT());
		heldTxnJsonObj.put("prdctVatCode",
				heldTransactionModel.getPRDCT_VAT_CODE());
		heldTxnJsonObj.put("prdctVatAmnt",
				heldTransactionModel.getPRDCT_VAT_AMNT());
		heldTxnJsonObj.put("usrAnthntctd",
				heldTransactionModel.getUSR_ANTHNTCTD());
		heldTxnJsonObj
				.put("slsManCode", heldTransactionModel.getSLS_MAN_CODE());
		heldTxnJsonObj.put("prdctVatExmpt",
				heldTransactionModel.getPRDCT_VAT_EXMPT());
		heldTxnJsonObj.put("prdctCostPrce",
				heldTransactionModel.getPRDCT_COST_PRCE());
		heldTxnJsonObj.put("autoGrnDone",
				heldTransactionModel.getAUTO_GRN_DONE());
		heldTxnJsonObj.put("prdctPymntModeDscnt",
				heldTransactionModel.getPRDCT_PYMNT_MODE_DSCNT());
		heldTxnJsonObj.put("billScncd", heldTransactionModel.getBILL_SCNCD());
		heldTxnJsonObj.put("cmpnyCode", heldTransactionModel.getCMPNY_CODE());
		heldTxnJsonObj.put("brnchCode", heldTransactionModel.getBRNCH_CODE());
		heldTxnJsonObj.put("txnType", heldTransactionModel.getTXN_TYPE());
		heldTxnJsonObj.put("uploadFlg", "Y");
		heldTxnJsonObj.put("prdctCode", heldTransactionModel.getPRDCT_CODE());
		heldTxnJsonObj.put("prdctPrc", heldTransactionModel.getPRDCT_PRC());
		heldTxnJsonObj.put("prdctQnty", heldTransactionModel.getPRDCT_PRC());
		heldTxnJsonObj.put("prdctAmnt", heldTransactionModel.getPRDCT_AMNT());
		heldTxnJsonObj.put("prdctVoid", heldTransactionModel.getPRDCT_VOID());
		heldTxnJsonObj.put("prdctScnd", heldTransactionModel.getPRDCT_SCND());
		heldTxnJsonObj.put("billPrntd", heldTransactionModel.getBILL_PRNTD());
		heldTxnJsonObj.put("emptyDone", heldTransactionModel.getEMPTY_DONE());
		heldTxnJsonObj.put("zedNo", heldTransactionModel.getZED_NO());
		heldTxnJsonObj.put("srNo", heldTransactionModel.getSR_NO());
		heldTxnJsonObj.put("pack", heldTransactionModel.getPACK());
		heldTxnJsonObj.put("packqty", heldTransactionModel.getPACKQTY());
		heldTxnJsonObj.put("packprce", heldTransactionModel.getPACKPRCE());
		heldTxnJsonObj.put("linedisc", heldTransactionModel.getLINEDISC());
		heldTxnJsonObj.put("heldUsr", heldTransactionModel.getHELD_USR());
		heldTxnJsonObj.put("scanCode", heldTransactionModel.getSCAN_CODE());

		return heldTxnJsonObj;
	}

}
