/*
 * Copyright (C) 2013 jaggarnaut-studios Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.master.model;

import java.io.Serializable;

import android.content.ContentValues;

import com.mpos.framework.common.DBConstants;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class SLMN_MST_Model implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String slmn_name;
	private boolean is_slmn_active;
	private String slmn_region_code;
	private String slmn_branch_code;
	private String slmn_code;
	private String slmn_from_dt;
	private String slmn_to_dt;
	private boolean is_slmn_upload_flg;
	private String slmn_old_code;
	private boolean isSlected;
	
	public String getSlmn_name() {
		return slmn_name;
	}

	public void setSlmn_name(String slmn_name) {
		this.slmn_name = slmn_name;
	}

	public boolean isIs_slmn_active() {
		return is_slmn_active;
	}

	public void setIs_slmn_active(boolean is_slmn_active) {
		this.is_slmn_active = is_slmn_active;
	}

	public String getSlmn_region_code() {
		return slmn_region_code;
	}

	public void setSlmn_region_code(String slmn_region_code) {
		this.slmn_region_code = slmn_region_code;
	}

	public String getSlmn_branch_code() {
		return slmn_branch_code;
	}

	public void setSlmn_branch_code(String slmn_branch_code) {
		this.slmn_branch_code = slmn_branch_code;
	}

	public String getSlmn_code() {
		return slmn_code;
	}

	public void setSlmn_code(String slmn_code) {
		this.slmn_code = slmn_code;
	}

	public String getSlmn_from_dt() {
		return slmn_from_dt;
	}

	public void setSlmn_from_dt(String slmn_from_dt) {
		this.slmn_from_dt = slmn_from_dt;
	}

	public String getSlmn_to_dt() {
		return slmn_to_dt;
	}

	public void setSlmn_to_dt(String slmn_to_dt) {
		this.slmn_to_dt = slmn_to_dt;
	}

	public boolean isIs_slmn_upload_flg() {
		return is_slmn_upload_flg;
	}

	public void setIs_slmn_upload_flg(boolean is_slmn_upload_flg) {
		this.is_slmn_upload_flg = is_slmn_upload_flg;
	}

	public String getSlmn_old_code() {
		return slmn_old_code;
	}

	public void setSlmn_old_code(String slmn_old_code) {
		this.slmn_old_code = slmn_old_code;
	}

	@Override
	public String toString() {
		return "SLMN_MST_Model [slmn_name=" + slmn_name + ", is_slmn_active="
				+ is_slmn_active + ", slmn_region_code=" + slmn_region_code
				+ ", slmn_branch_code=" + slmn_branch_code + ", slmn_code="
				+ slmn_code + ", slmn_from_dt=" + slmn_from_dt
				+ ", slmn_to_dt=" + slmn_to_dt + ", is_slmn_upload_flg="
				+ is_slmn_upload_flg + ", slmn_old_code=" + slmn_old_code + "]";
	}

	public static ContentValues getcontentvalues(SLMN_MST_Model model) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.SM_CODE, model.getSlmn_code());
		values.put(DBConstants.Name, model.getSlmn_name());
		values.put(DBConstants.REGION_CODE, model.getSlmn_region_code());
		values.put(DBConstants.BRNCH_CODE, model.getSlmn_branch_code());
		values.put(DBConstants.ACTIVE, model.is_slmn_active);
		values.put(DBConstants.FROM_DT, model.getSlmn_from_dt());
		values.put(DBConstants.TO_DT, model.getSlmn_to_dt());
		values.put(DBConstants.Upload_Flg, model.isIs_slmn_upload_flg());
		return values;
	}

	public boolean isSlected() {
		return isSlected;
	}

	public void setSlected(boolean isSlected) {
		this.isSlected = isSlected;
	}

	

}
