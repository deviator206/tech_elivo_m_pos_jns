package com.mpos.master.model;

import java.io.Serializable;

import android.content.ContentValues;

import com.mpos.framework.common.DBConstants;

public class PRDCT_MST_Model extends BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String PRDCT_VAT_CODE;
	private String PRDCT_SUB_CLASS;
	private String PRDCT_UNIT;
	private double PRDCT_QTY;
	private double PRDCT_SELL_PRCE;
	private String PRDCT_SHRT_DSCRPTN;
	private String PRDCT_SPPLR;
	private String PRDCT_DSCRPTN;
	private String PRDCT_FIX_QTY;
	private String PRDCT_FIXED_PRCE;
	private String PRDCT_GRP_CODE;
	private String PRDCT_IMG;
	private String PRDCT_LNG_DSCRPTN;
	private double PRDCT_MIN_QTY;
	private double PRDCT_PCKNG;
	private double DISP_ORDER_NO;
	private String PRDCT_ANLYS_CODE;
	private String PRDCT_BIN_LOC_CODE;
	private String PRDCT_CLR;
	private String PRDCT_CODE;
	private double PRDCT_COST_PRCE;
	private String PRDCT_CP_VLTN;
	private String PRDCT_DPT_CODE;

	public String getPRDCT_VAT_CODE() {
		return PRDCT_VAT_CODE;
	}

	public void setPRDCT_VAT_CODE(String pRDCT_VAT_CODE) {
		PRDCT_VAT_CODE = pRDCT_VAT_CODE;
	}

	public String getPRDCT_SUB_CLASS() {
		return PRDCT_SUB_CLASS;
	}

	public void setPRDCT_SUB_CLASS(String pRDCT_SUB_CLASS) {
		PRDCT_SUB_CLASS = pRDCT_SUB_CLASS;
	}

	public String getPRDCT_UNIT() {
		return PRDCT_UNIT;
	}

	public void setPRDCT_UNIT(String pRDCT_UNIT) {
		PRDCT_UNIT = pRDCT_UNIT;
	}

	public double getPRDCT_QTY() {
		return PRDCT_QTY;
	}

	public void setPRDCT_QTY(double pRDCT_QTY) {
		PRDCT_QTY = pRDCT_QTY;
	}

	public double getPRDCT_SELL_PRCE() {
		return PRDCT_SELL_PRCE;
	}

	public void setPRDCT_SELL_PRCE(double pRDCT_SELL_PRCE) {
		PRDCT_SELL_PRCE = pRDCT_SELL_PRCE;
	}

	public String getPRDCT_SHRT_DSCRPTN() {
		return PRDCT_SHRT_DSCRPTN;
	}

	public void setPRDCT_SHRT_DSCRPTN(String pRDCT_SHRT_DSCRPTN) {
		PRDCT_SHRT_DSCRPTN = pRDCT_SHRT_DSCRPTN;
	}

	public String getPRDCT_SPPLR() {
		return PRDCT_SPPLR;
	}

	public void setPRDCT_SPPLR(String pRDCT_SPPLR) {
		PRDCT_SPPLR = pRDCT_SPPLR;
	}

	public String getPRDCT_DSCRPTN() {
		return PRDCT_DSCRPTN;
	}

	public void setPRDCT_DSCRPTN(String pRDCT_DSCRPTN) {
		PRDCT_DSCRPTN = pRDCT_DSCRPTN;
	}

	public String getPRDCT_FIX_QTY() {
		return PRDCT_FIX_QTY;
	}

	public void setPRDCT_FIX_QTY(String pRDCT_FIX_QTY) {
		PRDCT_FIX_QTY = pRDCT_FIX_QTY;
	}

	public String getPRDCT_FIXED_PRCE() {
		return PRDCT_FIXED_PRCE;
	}

	public void setPRDCT_FIXED_PRCE(String pRDCT_FIXED_PRCE) {
		PRDCT_FIXED_PRCE = pRDCT_FIXED_PRCE;
	}

	public String getPRDCT_GRP_CODE() {
		return PRDCT_GRP_CODE;
	}

	public void setPRDCT_GRP_CODE(String pRDCT_GRP_CODE) {
		PRDCT_GRP_CODE = pRDCT_GRP_CODE;
	}

	public String getPRDCT_IMG() {
		return PRDCT_IMG;
	}

	public void setPRDCT_IMG(String pRDCT_IMG) {
		PRDCT_IMG = pRDCT_IMG;
	}

	public String getPRDCT_LNG_DSCRPTN() {
		return PRDCT_LNG_DSCRPTN;
	}

	public void setPRDCT_LNG_DSCRPTN(String pRDCT_LNG_DSCRPTN) {
		PRDCT_LNG_DSCRPTN = pRDCT_LNG_DSCRPTN;
	}

	public double getPRDCT_MIN_QTY() {
		return PRDCT_MIN_QTY;
	}

	public void setPRDCT_MIN_QTY(double pRDCT_MIN_QTY) {
		PRDCT_MIN_QTY = pRDCT_MIN_QTY;
	}

	public double getPRDCT_PCKNG() {
		return PRDCT_PCKNG;
	}

	public void setPRDCT_PCKNG(double pRDCT_PCKNG) {
		PRDCT_PCKNG = pRDCT_PCKNG;
	}

	public double getDISP_ORDER_NO() {
		return DISP_ORDER_NO;
	}

	public void setDISP_ORDER_NO(double dISP_ORDER_NO) {
		DISP_ORDER_NO = dISP_ORDER_NO;
	}

	public String getPRDCT_ANLYS_CODE() {
		return PRDCT_ANLYS_CODE;
	}

	public void setPRDCT_ANLYS_CODE(String pRDCT_ANLYS_CODE) {
		PRDCT_ANLYS_CODE = pRDCT_ANLYS_CODE;
	}

	public String getPRDCT_BIN_LOC_CODE() {
		return PRDCT_BIN_LOC_CODE;
	}

	public void setPRDCT_BIN_LOC_CODE(String pRDCT_BIN_LOC_CODE) {
		PRDCT_BIN_LOC_CODE = pRDCT_BIN_LOC_CODE;
	}

	public String getPRDCT_CLR() {
		return PRDCT_CLR;
	}

	public void setPRDCT_CLR(String pRDCT_CLR) {
		PRDCT_CLR = pRDCT_CLR;
	}

	public String getPRDCT_CODE() {
		return PRDCT_CODE;
	}

	public void setPRDCT_CODE(String pRDCT_CODE) {
		PRDCT_CODE = pRDCT_CODE;
	}

	public double getPRDCT_COST_PRCE() {
		return PRDCT_COST_PRCE;
	}

	public void setPRDCT_COST_PRCE(double pRDCT_COST_PRCE) {
		PRDCT_COST_PRCE = pRDCT_COST_PRCE;
	}

	public String getPRDCT_CP_VLTN() {
		return PRDCT_CP_VLTN;
	}

	public void setPRDCT_CP_VLTN(String pRDCT_CP_VLTN) {
		PRDCT_CP_VLTN = pRDCT_CP_VLTN;
	}

	public String getPRDCT_DPT_CODE() {
		return PRDCT_DPT_CODE;
	}

	public void setPRDCT_DPT_CODE(String pRDCT_DPT_CODE) {
		PRDCT_DPT_CODE = pRDCT_DPT_CODE;
	}

	@Override
	public String toString() {
		return "PRDCT_MST_Model [PRDCT_VAT_CODE=" + PRDCT_VAT_CODE
				+ ", PRDCT_SUB_CLASS=" + PRDCT_SUB_CLASS + ", PRDCT_UNIT="
				+ PRDCT_UNIT + ", PRDCT_QTY=" + PRDCT_QTY
				+ ", PRDCT_SELL_PRCE=" + PRDCT_SELL_PRCE
				+ ", PRDCT_SHRT_DSCRPTN=" + PRDCT_SHRT_DSCRPTN
				+ ", PRDCT_SPPLR=" + PRDCT_SPPLR + ", PRDCT_DSCRPTN="
				+ PRDCT_DSCRPTN + ", PRDCT_FIX_QTY=" + PRDCT_FIX_QTY
				+ ", PRDCT_FIXED_PRCE=" + PRDCT_FIXED_PRCE
				+ ", PRDCT_GRP_CODE=" + PRDCT_GRP_CODE + ", PRDCT_IMG="
				+ PRDCT_IMG + ", PRDCT_LNG_DSCRPTN=" + PRDCT_LNG_DSCRPTN
				+ ", PRDCT_MIN_QTY=" + PRDCT_MIN_QTY + ", PRDCT_PCKNG="
				+ PRDCT_PCKNG + ", DISP_ORDER_NO=" + DISP_ORDER_NO
				+ ", PRDCT_ANLYS_CODE=" + PRDCT_ANLYS_CODE
				+ ", PRDCT_BIN_LOC_CODE=" + PRDCT_BIN_LOC_CODE + ", PRDCT_CLR="
				+ PRDCT_CLR + ", PRDCT_CODE=" + PRDCT_CODE
				+ ", PRDCT_COST_PRCE=" + PRDCT_COST_PRCE + ", PRDCT_CP_VLTN="
				+ PRDCT_CP_VLTN + ", PRDCT_DPT_CODE=" + PRDCT_DPT_CODE + "]";
	}

	public static ContentValues getcontentvalues(PRDCT_MST_Model model) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.PRDCT_ANLYS_CODE, model.getPRDCT_ANLYS_CODE());
		values.put(DBConstants.PRDCT_BIN_LOC_CODE,
				model.getPRDCT_BIN_LOC_CODE());
		values.put(DBConstants.PRDCT_CLR, model.getPRDCT_CLR());
		values.put(DBConstants.PRDCT_CODE, model.getPRDCT_CODE());
		values.put(DBConstants.PRDCT_COST_PRCE, model.getPRDCT_COST_PRCE());
		values.put(DBConstants.PRDCT_CP_VLTN, model.getPRDCT_CP_VLTN());
		values.put(DBConstants.PRDCT_DPT_CODE, model.getPRDCT_DPT_CODE());
		values.put(DBConstants.PRDCT_DSCRPTN, model.getPRDCT_DSCRPTN());
		values.put(DBConstants.PRDCT_FIX_QTY, model.getPRDCT_FIX_QTY());
		values.put(DBConstants.PRDCT_FIXED_PRCE, model.getPRDCT_FIXED_PRCE());
		values.put(DBConstants.PRDCT_GRP_CODE, model.getPRDCT_GRP_CODE());
		values.put(DBConstants.PRDCT_IMG, model.getPRDCT_IMG());
		values.put(DBConstants.PRDCT_LNG_DSCRPTN, model.getPRDCT_LNG_DSCRPTN());
		values.put(DBConstants.PRDCT_MIN_QTY, model.getPRDCT_MIN_QTY());
		values.put(DBConstants.PRDCT_PCKNG, model.getPRDCT_PCKNG());
		values.put(DBConstants.PRDCT_QTY, model.getPRDCT_QTY());
		values.put(DBConstants.PRDCT_SELL_PRCE, model.getPRDCT_SELL_PRCE());
		values.put(DBConstants.PRDCT_SHRT_DSCRPTN,
				model.getPRDCT_SHRT_DSCRPTN());
		values.put(DBConstants.PRDCT_SPPLR, model.getPRDCT_SPPLR());
		values.put(DBConstants.PRDCT_SUB_CLASS, model.getPRDCT_SUB_CLASS());
		values.put(DBConstants.PRDCT_UNIT, model.getPRDCT_UNIT());
		values.put(DBConstants.PRDCT_VAT_CODE, model.getPRDCT_VAT_CODE());
		values.put(DBConstants.DISP_ORDER_NO, model.getDISP_ORDER_NO());
		return values;
	}

}
