/*
 * Copyright (C) 2013 jaggarnaut-studios Pvt. Ltd. All Rights Reserved.
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

public class CURRENCY_MST_MODEL extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String curr_name;
	private String curr_symbol;
	private String curr_abbrName;
	private String curr_unit;
	private int curr_postn;
	private String curr_subUnit;
	private String curr_presc;
	private String curr_base_flag;
	private String curr_operator;
	private float curr_exrate;
	private boolean isSelected = false;

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getCurr_name() {
		return curr_name;
	}

	public void setCurr_name(String curr_name) {
		this.curr_name = curr_name;
	}

	public String getCurr_symbol() {
		return curr_symbol;
	}

	public void setCurr_symbol(String curr_symbol) {
		this.curr_symbol = curr_symbol;
	}

	public String getCurr_abbrName() {
		return curr_abbrName;
	}

	public void setCurr_abbrName(String curr_abbrName) {
		this.curr_abbrName = curr_abbrName;
	}

	public String getCurr_unit() {
		return curr_unit;
	}

	public void setCurr_unit(String curr_unit) {
		this.curr_unit = curr_unit;
	}

	public int getCurr_postn() {
		return curr_postn;
	}

	public void setCurr_postn(int curr_postn) {
		this.curr_postn = curr_postn;
	}

	public String getCurr_subUnit() {
		return curr_subUnit;
	}

	public void setCurr_subUnit(String curr_subUnit) {
		this.curr_subUnit = curr_subUnit;
	}

	public String getCurr_presc() {
		return curr_presc;
	}

	public void setCurr_presc(String curr_presc) {
		this.curr_presc = curr_presc;
	}

	@Override
	public String toString() {
		return "CURRENCY_MSTR_MODEL [curr_name=" + curr_name + ", curr_symbol="
				+ curr_symbol + ", curr_abbrName=" + curr_abbrName
				+ ", curr_unit=" + curr_unit + ", curr_postn=" + curr_postn
				+ ", curr_subUnit=" + curr_subUnit + ", curr_presc="
				+ curr_presc + "]";
	}

	public static ContentValues getcontentvalues(CURRENCY_MST_MODEL model) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.Abbr_Name, model.getCurr_abbrName());
		values.put(DBConstants.Name, model.getCurr_name());
		values.put(DBConstants.POSTN, model.getCurr_postn());
		values.put(DBConstants.BaseFlag, model.getCurr_base_flag());

		String operator=model.getCurr_operator();
		if(!operator.equalsIgnoreCase("")){
			operator=operator;

		}
		values.put(DBConstants.Operator, operator);
		values.put(DBConstants.ExRate, model.getCurr_exrate());
		values.put(DBConstants.Symbol, model.getCurr_symbol());
		return values;
	}

	public String getCurr_base_flag() {
		return curr_base_flag;
	}

	public void setCurr_base_flag(String curr_base_flag) {
		this.curr_base_flag = curr_base_flag;
	}

	public String getCurr_operator() {
		return curr_operator;
	}

	public void setCurr_operator(String curr_operator) {
		this.curr_operator = curr_operator;
	}

	public float getCurr_exrate() {
		return curr_exrate;
	}

	public void setCurr_exrate(float curr_exrate) {
		this.curr_exrate = curr_exrate;
	}

}
