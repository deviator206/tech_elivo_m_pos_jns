package com.mpos.payment.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;

import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBConstants;
import com.mpos.framework.common.Logger;

public class PAYMT_MST_MODEL implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String CMPNY_CODE = "";
	public String BRNCH_CODE = "";
	public String PYMNT_NMBR = "";
	public String PAY_RUN_DATE = "";
	public String PAY_SYS_DATE = "";
	public String TXN_TYPE = "";
	public String PAY_MODE = "";
	public double PAY_AMNT;
	public String PRPRTNTE_DISC = "";
	public String PAY_MODE_REF_CODE = "";
	public String PAY_MODE_REF_NO = "";
	public String PAY_DTLS = "";
	public String TILL_NO = "";
	public String USR_NAME = "";
	public String USR_NAME_SCND = "";
	public String CARDNO = "";
	public String PNTS_AWARDED = "";
	public String PNTS_REDEEMED = "";
	public String CCDt = "";
	public String CCAuthCode = "";
	//private DecimalFormat df = new DecimalFormat("0.0000");
	private DecimalFormat df = Constants.getDecimalFormat();
	
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

	public String getPYMNT_NMBR() {
		return PYMNT_NMBR;
	}

	public void setPYMNT_NMBR(String pYMNT_NMBR) {
		PYMNT_NMBR = pYMNT_NMBR;
	}

	public String getPAY_RUN_DATE() {
		return PAY_RUN_DATE;
	}

	public void setPAY_RUN_DATE(String pAY_RUN_DATE) {
		PAY_RUN_DATE = pAY_RUN_DATE;
	}

	public String getPAY_SYS_DATE() {
		return PAY_SYS_DATE;
	}

	public void setPAY_SYS_DATE(String pAY_SYS_DATE) {
		PAY_SYS_DATE = pAY_SYS_DATE;
	}

	public String getTXN_TYPE() {
		return TXN_TYPE;
	}

	public void setTXN_TYPE(String tXN_TYPE) {
		TXN_TYPE = tXN_TYPE;
	}

	public String getPAY_MODE() {
		return PAY_MODE;
	}

	public void setPAY_MODE(String pAY_MODE) {
		PAY_MODE = pAY_MODE;
	}

	public double getPAY_AMNT() {
		return PAY_AMNT;
	}

	public void setPAY_AMNT(double pAY_AMNT) {
		String formate = df.format(pAY_AMNT);
		double finalValue = Double.parseDouble(formate);
		PAY_AMNT = finalValue;
	}

	public String getPRPRTNTE_DISC() {
		return PRPRTNTE_DISC;
	}

	public void setPRPRTNTE_DISC(String pRPRTNTE_DISC) {
		PRPRTNTE_DISC = pRPRTNTE_DISC;
	}

	public String getPAY_MODE_REF_CODE() {
		return PAY_MODE_REF_CODE;
	}

	public void setPAY_MODE_REF_CODE(String pAY_MODE_REF_CODE) {
		PAY_MODE_REF_CODE = pAY_MODE_REF_CODE;
	}

	public String getPAY_MODE_REF_NO() {
		return PAY_MODE_REF_NO;
	}

	public void setPAY_MODE_REF_NO(String pAY_MODE_REF_NO) {
		PAY_MODE_REF_NO = pAY_MODE_REF_NO;
	}

	public String getPAY_DTLS() {
		return PAY_DTLS;
	}

	public void setPAY_DTLS(String pAY_DTLS) {
		PAY_DTLS = pAY_DTLS;
	}

	public String getTILL_NO() {
		return TILL_NO;
	}

	public void setTILL_NO(String tILL_NO) {
		TILL_NO = tILL_NO;
	}

	public String getUSR_NAME() {
		return USR_NAME;
	}

	public void setUSR_NAME(String uSR_NAME) {
		USR_NAME = uSR_NAME;
	}

	public String getUSR_NAME_SCND() {
		return USR_NAME_SCND;
	}

	public void setUSR_NAME_SCND(String uSR_NAME_SCND) {
		USR_NAME_SCND = uSR_NAME_SCND;
	}

	public String getCARDNO() {
		return CARDNO;
	}

	public void setCARDNO(String cARDNO) {
		CARDNO = cARDNO;
	}

	public String getPNTS_AWARDED() {
		return PNTS_AWARDED;
	}

	public void setPNTS_AWARDED(String pNTS_AWARDED) {
		PNTS_AWARDED = pNTS_AWARDED;
	}

	public String getPNTS_REDEEMED() {
		return PNTS_REDEEMED;
	}

	public void setPNTS_REDEEMED(String pNTS_REDEEMED) {
		PNTS_REDEEMED = pNTS_REDEEMED;
	}

	public String getCCDt() {
		return CCDt;
	}

	public void setCCDt(String cCDt) {
		CCDt = cCDt;
	}

	public String getCCAuthCode() {
		return CCAuthCode;
	}

	public void setCCAuthCode(String cCAuthCode) {
		CCAuthCode = cCAuthCode;
	}

	@Override
	public String toString() {
		return "PAYMT_MST_MODEL [CMPNY_CODE=" + CMPNY_CODE + ", BRNCH_CODE="
				+ BRNCH_CODE + ", PYMNT_NMBR=" + PYMNT_NMBR + ", PAY_RUN_DATE="
				+ PAY_RUN_DATE + ", PAY_SYS_DATE=" + PAY_SYS_DATE
				+ ", TXN_TYPE=" + TXN_TYPE + ", PAY_MODE=" + PAY_MODE
				+ ", PAY_AMNT=" + PAY_AMNT + ", PRPRTNTE_DISC=" + PRPRTNTE_DISC
				+ ", PAY_MODE_REF_CODE=" + PAY_MODE_REF_CODE
				+ ", PAY_MODE_REF_NO=" + PAY_MODE_REF_NO + ", PAY_DTLS="
				+ PAY_DTLS + ", TILL_NO=" + TILL_NO + ", USR_NAME=" + USR_NAME
				+ ", USR_NAME_SCND=" + USR_NAME_SCND + ", CARDNO=" + CARDNO
				+ ", PNTS_AWARDED=" + PNTS_AWARDED + ", PNTS_REDEEMED="
				+ PNTS_REDEEMED + ", CCDt=" + CCDt + ", CCAuthCode="
				+ CCAuthCode + "]";
	}

	public static ContentValues getcontentvalues(PAYMT_MST_MODEL model) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.CMPNY_CODE, model.getCMPNY_CODE());
		values.put(DBConstants.BRNCH_CODE, model.getBRNCH_CODE());
		values.put(DBConstants.PYMNT_NMBR, model.getPYMNT_NMBR());
		values.put(DBConstants.PAY_RUN_DATE, model.getPAY_RUN_DATE());
		values.put(DBConstants.PAY_SYS_DATE, model.getPAY_SYS_DATE());
		values.put(DBConstants.TXN_TYPE, model.getTXN_TYPE());
		values.put(DBConstants.PAY_MODE, model.getPAY_MODE());
		values.put(DBConstants.PAY_AMNT, model.getPAY_AMNT());
		values.put(DBConstants.PRPRTNTE_DISC, model.getPRPRTNTE_DISC());
		values.put(DBConstants.PAY_MODE_REF_CODE, model.getPAY_MODE_REF_CODE());
		values.put(DBConstants.PAY_MODE_REF_NO, model.getPAY_MODE_REF_NO());
		values.put(DBConstants.PAY_DTLS, model.getPAY_DTLS());
		values.put(DBConstants.TILL_NO, model.getTILL_NO());
		values.put(DBConstants.USR_NAME, model.getUSR_NAME());
		values.put(DBConstants.USR_NAME_SCND, model.getUSR_NAME_SCND());
		values.put(DBConstants.CARDNO, model.getCARDNO());
		values.put(DBConstants.PNTS_AWARDED, model.getPNTS_AWARDED());
		values.put(DBConstants.PNTS_REDEEMED, model.getPNTS_REDEEMED());
		values.put(DBConstants.CCDt, model.getCCDt());
		values.put(DBConstants.CCAuthCode, model.getCCAuthCode());
		return values;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		PAYMT_MST_MODEL that = (PAYMT_MST_MODEL) o;
		if (this.getPAY_MODE().equalsIgnoreCase(that.getPAY_MODE())
				&& this.getPYMNT_NMBR().equalsIgnoreCase(that.getPYMNT_NMBR())) {
			return true;
		}
		return false;
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
	public static JSONObject getJsonData(PAYMT_MST_MODEL paymt_MST_MODEL)
			throws JSONException {
		JSONObject paymentsMstJsonObject = new JSONObject();

		SimpleDateFormat dateFormat = new SimpleDateFormat(
				Constants.DATE_FORMAT);

		Logger.d("PMT:RunDate: ", "" + paymt_MST_MODEL.getPAY_RUN_DATE());
		String formatRunDate = Constants.getFormattedDate(paymt_MST_MODEL
				.getPAY_RUN_DATE());
		paymentsMstJsonObject.put("payRunDate", formatRunDate);

		Logger.d("PMT:SysDate: ", "" + paymt_MST_MODEL.getPAY_SYS_DATE());
		try {
			String formatSysDate = dateFormat.format(dateFormat
					.parse(paymt_MST_MODEL.getPAY_SYS_DATE()));
			paymentsMstJsonObject.put("paySysDate", formatSysDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		paymentsMstJsonObject.put("prprtnteDisc",
				paymt_MST_MODEL.getPRPRTNTE_DISC());
		paymentsMstJsonObject.put("payModeRefCode",
				paymt_MST_MODEL.getPAY_MODE_REF_CODE());
		paymentsMstJsonObject.put("payModeRefNo",
				paymt_MST_MODEL.getPAY_MODE_REF_NO());
		paymentsMstJsonObject.put("usrNameScnd",
				paymt_MST_MODEL.getUSR_NAME_SCND());
		paymentsMstJsonObject.put("pntsAwarded",
				paymt_MST_MODEL.getPNTS_AWARDED());
		paymentsMstJsonObject.put("pntsRedeemed",
				paymt_MST_MODEL.getPNTS_REDEEMED());
		paymentsMstJsonObject
				.put("ccauthcode", paymt_MST_MODEL.getCCAuthCode());
		paymentsMstJsonObject.put("cmpnyCode", paymt_MST_MODEL.getCMPNY_CODE());
		paymentsMstJsonObject.put("brnchCode", paymt_MST_MODEL.getBRNCH_CODE());
		paymentsMstJsonObject.put("txnType", paymt_MST_MODEL.getTXN_TYPE());
		paymentsMstJsonObject.put("tillNo", paymt_MST_MODEL.getTILL_NO());
		paymentsMstJsonObject.put("usrName", paymt_MST_MODEL.getUSR_NAME());
		paymentsMstJsonObject.put("cardno", paymt_MST_MODEL.getCARDNO());
		paymentsMstJsonObject.put("uploadFlg", "Y");
		paymentsMstJsonObject.put("pymntNmbr", paymt_MST_MODEL.getPYMNT_NMBR());
		paymentsMstJsonObject.put("payMode", paymt_MST_MODEL.getPAY_MODE());
		paymentsMstJsonObject.put("payAmnt", paymt_MST_MODEL.getPAY_AMNT());
		paymentsMstJsonObject.put("payDtls", paymt_MST_MODEL.getPAY_DTLS());
		paymentsMstJsonObject.put("ccdt", paymt_MST_MODEL.getCCDt());
		paymentsMstJsonObject.put("pymntSrNo", ""); //
		paymentsMstJsonObject.put("sapFlg", ""); //

		return paymentsMstJsonObject;
	}

}
