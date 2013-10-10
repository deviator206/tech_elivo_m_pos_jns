package com.mpos.master.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;

import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBConstants;
import com.mpos.framework.common.Logger;

public class BILL_Mst_Model implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String CMPNY_CODE;
	private String BRNCH_CODE;
	private String BILL_SCNCD;
	private String TXN_TYPE;
	private String BILL_RUN_DATE; // timestamp
	private String TILL_NO;
	private String BILL_NO;
	private String BILL_SYS_DATE; // timestamp
	private String BILL_VAT_EXMPT;
	private double BILL_AMNT;
	private double AMNT_PAID;
	private double CHNG_GVN;
	private double BILL_VAT_AMNT;
	private String USR_NAME;
	private String BILL_STATUS;
	private String BILL_SCND;
	private String BILL_AMENED;
	private String CARDNO;
	private String SHIFT_CODE;
	private String TBLE_CODE;
	private String BILL_AMND_STATUS;
	private String BILL_LOCKED;

	private boolean isSelected = false;

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

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

	public String getTILL_NO() {
		return TILL_NO;
	}

	public void setTILL_NO(String tILL_NO) {
		TILL_NO = tILL_NO;
	}

	public String getBILL_NO() {
		return BILL_NO;
	}

	public void setBILL_NO(String bILL_NO) {
		BILL_NO = bILL_NO;
	}

	public String getBILL_SYS_DATE() {
		return BILL_SYS_DATE;
	}

	public void setBILL_SYS_DATE(String bILL_SYS_DATE) {
		BILL_SYS_DATE = bILL_SYS_DATE;
	}

	public String getBILL_VAT_EXMPT() {
		return BILL_VAT_EXMPT;
	}

	public void setBILL_VAT_EXMPT(String bILL_VAT_EXMPT) {
		BILL_VAT_EXMPT = bILL_VAT_EXMPT;
	}

	public double getBILL_AMNT() {
		return BILL_AMNT;
	}

	public void setBILL_AMNT(double bILL_AMNT) {
		BILL_AMNT = bILL_AMNT;
	}

	public double getAMNT_PAID() {
		return AMNT_PAID;
	}

	public void setAMNT_PAID(double aMNT_PAID) {
		AMNT_PAID = aMNT_PAID;
	}

	public double getCHNG_GVN() {
		return CHNG_GVN;
	}

	public void setCHNG_GVN(double cHNG_GVN) {
		CHNG_GVN = cHNG_GVN;
	}

	public double getBILL_VAT_AMNT() {
		return BILL_VAT_AMNT;
	}

	public void setBILL_VAT_AMNT(double bILL_VAT_AMNT) {
		BILL_VAT_AMNT = bILL_VAT_AMNT;
	}

	public String getUSR_NAME() {
		return USR_NAME;
	}

	public void setUSR_NAME(String uSR_NAME) {
		USR_NAME = uSR_NAME;
	}

	public String getBILL_STATUS() {
		return BILL_STATUS;
	}

	public void setBILL_STATUS(String bILL_STATUS) {
		BILL_STATUS = bILL_STATUS;
	}

	public String getBILL_SCND() {
		return BILL_SCND;
	}

	public void setBILL_SCND(String bILL_SCND) {
		BILL_SCND = bILL_SCND;
	}

	public String getBILL_AMENED() {
		return BILL_AMENED;
	}

	public void setBILL_AMENED(String bILL_AMENED) {
		BILL_AMENED = bILL_AMENED;
	}

	public String getCARDNO() {
		return CARDNO;
	}

	public void setCARDNO(String cARDNO) {
		CARDNO = cARDNO;
	}

	public String getSHIFT_CODE() {
		return SHIFT_CODE;
	}

	public void setSHIFT_CODE(String sHIFT_CODE) {
		SHIFT_CODE = sHIFT_CODE;
	}

	public String getTBLE_CODE() {
		return TBLE_CODE;
	}

	public void setTBLE_CODE(String tBLE_CODE) {
		TBLE_CODE = tBLE_CODE;
	}

	public String getBILL_AMND_STATUS() {
		return BILL_AMND_STATUS;
	}

	public void setBILL_AMND_STATUS(String bILL_AMND_STATUS) {
		BILL_AMND_STATUS = bILL_AMND_STATUS;
	}

	public String getBILL_LOCKED() {
		return BILL_LOCKED;
	}

	public void setBILL_LOCKED(String bILL_LOCKED) {
		BILL_LOCKED = bILL_LOCKED;
	}

	@Override
	public String toString() {
		return "BIll_Mst_Model [CMPNY_CODE=" + CMPNY_CODE + ", BRNCH_CODE="
				+ BRNCH_CODE + ", BILL_SCNCD=" + BILL_SCNCD + ", TXN_TYPE="
				+ TXN_TYPE + ", BILL_RUN_DATE=" + BILL_RUN_DATE + ", TILL_NO="
				+ TILL_NO + ", BILL_NO=" + BILL_NO + ", BILL_SYS_DATE="
				+ BILL_SYS_DATE + ", BILL_VAT_EXMPT=" + BILL_VAT_EXMPT
				+ ", BILL_AMNT=" + BILL_AMNT + ", AMNT_PAID=" + AMNT_PAID
				+ ", CHNG_GVN=" + CHNG_GVN + ", BILL_VAT_AMNT=" + BILL_VAT_AMNT
				+ ", USR_NAME=" + USR_NAME + ", BILL_STATUS=" + BILL_STATUS
				+ ", BILL_SCND=" + BILL_SCND + ", BILL_AMENED=" + BILL_AMENED
				+ ", CARDNO=" + CARDNO + ", SHIFT_CODE=" + SHIFT_CODE
				+ ", TBLE_CODE=" + TBLE_CODE + ", BILL_AMND_STATUS="
				+ BILL_AMND_STATUS + ", BILL_LOCKED=" + BILL_LOCKED + "]";
	}

	public static ContentValues getcontentvalues(BILL_Mst_Model model) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.AMNT_PAID, model.getAMNT_PAID());
		values.put(DBConstants.BILL_AMENED, model.getBILL_AMENED());
		values.put(DBConstants.BILL_AMND_STATUS, model.getBILL_AMND_STATUS());
		values.put(DBConstants.BILL_AMNT, model.getAMNT_PAID());
		values.put(DBConstants.BILL_LOCKED, model.getBILL_LOCKED());
		values.put(DBConstants.BILL_NO, model.getBILL_NO());
		values.put(DBConstants.BILL_RUN_DATE, model.getBILL_RUN_DATE());
		values.put(DBConstants.BILL_SCNCD, model.getBILL_SCNCD());
		values.put(DBConstants.BILL_SCND, model.getBILL_SCND());
		values.put(DBConstants.BILL_STATUS, model.getBILL_STATUS());
		values.put(DBConstants.BILL_SYS_DATE, model.getBILL_SYS_DATE());
		values.put(DBConstants.BILL_VAT_AMNT, model.getBILL_VAT_AMNT());
		values.put(DBConstants.BILL_VAT_EXMPT, model.getBILL_VAT_EXMPT());
		values.put(DBConstants.BRNCH_CODE, model.getBRNCH_CODE());
		values.put(DBConstants.CARDNO, model.getCARDNO());
		values.put(DBConstants.CHNG_GVN, model.getCHNG_GVN());
		values.put(DBConstants.CMPNY_CODE, model.getCMPNY_CODE());
		values.put(DBConstants.SHIFT_CODE, model.getSHIFT_CODE());
		values.put(DBConstants.TBLE_CODE, model.getTBLE_CODE());
		values.put(DBConstants.TILL_NO, model.getTILL_NO());
		values.put(DBConstants.TXN_TYPE, model.getTXN_TYPE());
		values.put(DBConstants.USR_NAME, model.getUSR_NAME());

		return values;
	}

	public static ContentValues getUpdatevalues(BILL_Mst_Model model) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.AMNT_PAID, model.getAMNT_PAID());
		values.put(DBConstants.BILL_AMENED, model.getBILL_AMENED());
		values.put(DBConstants.BILL_AMND_STATUS, model.getBILL_AMND_STATUS());
		values.put(DBConstants.BILL_AMNT, model.getAMNT_PAID());
		values.put(DBConstants.BILL_LOCKED, model.getBILL_LOCKED());
		values.put(DBConstants.BILL_STATUS, model.getBILL_STATUS());
		values.put(DBConstants.BILL_VAT_AMNT, model.getBILL_VAT_AMNT());
		values.put(DBConstants.BILL_VAT_EXMPT, model.getBILL_VAT_EXMPT());
		values.put(DBConstants.CARDNO, model.getCARDNO());
		values.put(DBConstants.CHNG_GVN, model.getCHNG_GVN());
		values.put(DBConstants.TXN_TYPE, model.getTXN_TYPE());
		values.put(DBConstants.USR_NAME, model.getUSR_NAME());
		values.put(DBConstants.BILL_SYS_DATE, model.getBILL_SYS_DATE());
		return values;
	}

	public static ContentValues getUpdateTXNTYPEContentvalue(
			BILL_Mst_Model model) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.TXN_TYPE, model.getTXN_TYPE());
		values.put(DBConstants.BILL_SYS_DATE, model.getBILL_SYS_DATE());
		return values;
	}

	/**
	 * Method- To return JSON data
	 * 
	 * @param billMstModel
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject getJsonData(BILL_Mst_Model billMstModel)
			throws JSONException {
		JSONObject jsonObj = new JSONObject();

		SimpleDateFormat dateFormat = new SimpleDateFormat(
				Constants.DATE_FORMAT);
		Logger.d("Date: ", billMstModel.getBILL_RUN_DATE());
		String formatRunDate = Constants.getFormattedDate(billMstModel
				.getBILL_RUN_DATE());
		jsonObj.put("billRunDate", formatRunDate);

		try {
			String formatSysDate = dateFormat.format(dateFormat
					.parse(billMstModel.getBILL_SYS_DATE()));
			jsonObj.put("billSysDate", formatSysDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		jsonObj.put("billVatExmpt", billMstModel.getBILL_VAT_EXMPT());
		jsonObj.put("billVatAmnt", billMstModel.getBILL_VAT_AMNT());
		jsonObj.put("billStatus", billMstModel.getBILL_STATUS());
		jsonObj.put("billAmened", billMstModel.getBILL_AMENED());
		jsonObj.put("billScncd", billMstModel.getBILL_SCNCD());
		jsonObj.put("cmpnyCode", billMstModel.getCMPNY_CODE());
		jsonObj.put("brnchCode", billMstModel.getBRNCH_CODE());
		jsonObj.put("txnType", billMstModel.getTXN_TYPE());
		jsonObj.put("tillNo", billMstModel.getTILL_NO());
		jsonObj.put("billNo", billMstModel.getBILL_NO());
		jsonObj.put("billAmnt", billMstModel.getBILL_AMNT());
		jsonObj.put("amntPaid", billMstModel.getAMNT_PAID());
		jsonObj.put("chngGvn", billMstModel.getCHNG_GVN());
		jsonObj.put("usrName", billMstModel.getUSR_NAME());
		jsonObj.put("billScnd", billMstModel.getBILL_SCND());
		jsonObj.put("cardno", billMstModel.getCARDNO());
		jsonObj.put("shiftCode", billMstModel.getSHIFT_CODE());
		jsonObj.put("tbleCode", billMstModel.getTBLE_CODE());
		jsonObj.put("uploadFlg", "Y");

		return jsonObj;
	}

}
