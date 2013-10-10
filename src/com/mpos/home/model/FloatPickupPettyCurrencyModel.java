package com.mpos.home.model;

import java.io.Serializable;
import java.text.DecimalFormat;

import android.content.ContentValues;

import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBConstants;

public class FloatPickupPettyCurrencyModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String PYMNT_TYPE = "";
	public String CURR_ABBR = "";
	public double PAY_AMNT = 0;
	public double EX_RATE = 1;
	public String OPERATOR = "*";
	public double LOC_AMNT = 0;

	//private DecimalFormat df=new DecimalFormat("0.0000");
	private DecimalFormat df = Constants.getDecimalFormat();
	
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
		return "MULTY_PMT_MST_Model [PYMNT_TYPE=" + getPYMNT_TYPE() + ", CURR_ABBR="
				+ CURR_ABBR + ", PAY_AMNT=" + PAY_AMNT + ", EX_RATE=" + EX_RATE
				+ ", OPERATOR=" + OPERATOR + ", LOC_AMNT=" + LOC_AMNT + "]";
	}

	public static ContentValues getcontentvalues(FloatPickupPettyCurrencyModel model) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.PYMNT_TYPE, model.getPYMNT_TYPE());
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

		FloatPickupPettyCurrencyModel that = (FloatPickupPettyCurrencyModel) o;
		if (this.getCURR_ABBR().equalsIgnoreCase(that.getCURR_ABBR())
				&& this.getPYMNT_TYPE().equalsIgnoreCase(that.getPYMNT_TYPE())) {
			return true;
		}
		return false;
	}

	

	public String getPYMNT_TYPE() {
		return PYMNT_TYPE;
	}

	public void setPYMNT_TYPE(String pYMNT_TYPE) {
		PYMNT_TYPE = pYMNT_TYPE;
	}

}
