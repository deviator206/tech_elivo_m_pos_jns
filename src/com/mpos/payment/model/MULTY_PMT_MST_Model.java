package com.mpos.payment.model;

import java.io.Serializable;
import java.text.DecimalFormat;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;

import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBConstants;
import com.mpos.master.model.BILL_Mst_Model;

public class MULTY_PMT_MST_Model implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String PYMNT_NMBR = "";
	public String CURR_ABBR = "";
	public double PAY_AMNT = 0;
	public double EX_RATE = 1;
	public String OPERATOR = "*";
	public double LOC_AMNT = 0;

	//private DecimalFormat df=new DecimalFormat("0.0000");
	private DecimalFormat df = Constants.getDecimalFormat();
	
	public String getPYMNT_NMBR() {
		return PYMNT_NMBR;
	}

	public void setPYMNT_NMBR(String pYMNT_NMBR) {
		PYMNT_NMBR = pYMNT_NMBR;
	}

	public String getCURR_ABBR() {
		return CURR_ABBR;
	}

	public void setCURR_ABBR(String cURR_ABBR) {
		CURR_ABBR = cURR_ABBR;
	}

	public double getPAY_AMNT() {
		return PAY_AMNT;
	}

	public void setPAY_AMNT(double pAY_AMNT) {
		String formate = df.format(pAY_AMNT); 
		double finalValue = Double.parseDouble(formate) ;
		PAY_AMNT = finalValue;
	}

	public double getEX_RATE() {
		return EX_RATE;
	}

	public void setEX_RATE(double eX_RATE) {
		String formate = df.format(eX_RATE); 
		double finalValue = Double.parseDouble(formate) ;
		EX_RATE = finalValue;
	}

	public String getOPERATOR() {
		return OPERATOR;
	}

	public void setOPERATOR(String oPERATOR) {
		OPERATOR = oPERATOR;
	}

	public double getLOC_AMNT() {
		return LOC_AMNT;
	}

	public void setLOC_AMNT(double lOC_AMNT) {
		String formate = df.format(lOC_AMNT); 
		double finalValue = Double.parseDouble(formate) ;
		LOC_AMNT = finalValue;
	}

	@Override
	public String toString() {
		return "MULTY_PMT_MST_Model [PYMNT_NMBR=" + PYMNT_NMBR + ", CURR_ABBR="
				+ CURR_ABBR + ", PAY_AMNT=" + PAY_AMNT + ", EX_RATE=" + EX_RATE
				+ ", OPERATOR=" + OPERATOR + ", LOC_AMNT=" + LOC_AMNT + "]";
	}

	public static ContentValues getcontentvalues(MULTY_PMT_MST_Model model) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.PYMNT_NMBR, model.getPYMNT_NMBR());
		values.put(DBConstants.CURR_ABBR, model.getCURR_ABBR());
		values.put(DBConstants.PAY_AMNT, model.getPAY_AMNT());
		values.put(DBConstants.EX_RATE, model.getEX_RATE());
		values.put(DBConstants.OPERATOR, model.getOPERATOR());
		values.put(DBConstants.LOC_AMNT, model.getLOC_AMNT());

		return values;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		MULTY_PMT_MST_Model that = (MULTY_PMT_MST_Model) o;
		if (this.getCURR_ABBR().equalsIgnoreCase(that.getCURR_ABBR())
				&& this.getPYMNT_NMBR().equalsIgnoreCase(that.getPYMNT_NMBR())) {
			return true;
		}
		return false;
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
			MULTY_PMT_MST_Model multy_PMT_MST_Model, BILL_Mst_Model billMstModel)
			throws JSONException {
		JSONObject MltiPymntMstJsonObj = new JSONObject();

		MltiPymntMstJsonObj.put("cmpnyCode", billMstModel.getCMPNY_CODE());
		MltiPymntMstJsonObj.put("brnchCode", billMstModel.getBRNCH_CODE());
		MltiPymntMstJsonObj.put("uploadFlg", "Y");
		MltiPymntMstJsonObj.put("pymntNmbr",
				multy_PMT_MST_Model.getPYMNT_NMBR());
		MltiPymntMstJsonObj.put("payAmnt", multy_PMT_MST_Model.getPAY_AMNT());
		MltiPymntMstJsonObj.put("currAbbr", multy_PMT_MST_Model.getCURR_ABBR());
		MltiPymntMstJsonObj.put("exRate", multy_PMT_MST_Model.getEX_RATE());
		MltiPymntMstJsonObj.put("operator", multy_PMT_MST_Model.getOPERATOR());
		MltiPymntMstJsonObj.put("locAmnt", multy_PMT_MST_Model.getLOC_AMNT());

		return MltiPymntMstJsonObj;
	}

}
