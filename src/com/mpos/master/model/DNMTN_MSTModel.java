package com.mpos.master.model;

import java.io.Serializable;

import android.content.ContentValues;

import com.mpos.framework.common.DBConstants;

public class DNMTN_MSTModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double VALUE;
	private String CURR_CODE;
	private String UNIT;
	private String userName;
	private String createdOn;
	
	public double getVALUE() {
		return VALUE;
	}
	public void setVALUE(double vALUE) {
		VALUE = vALUE;
	}
	public String getCURR_CODE() {
		return CURR_CODE;
	}
	public void setCURR_CODE(String cURR_CODE) {
		CURR_CODE = cURR_CODE;
	}
	public String getUNIT() {
		return UNIT;
	}
	public void setUNIT(String uNIT) {
		UNIT = uNIT;
	}
	
		
	public static ContentValues getcontentvalues(DNMTN_MSTModel model) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.CURR_CODE, model.getCURR_CODE());
		values.put(DBConstants.UNIT, model.getUNIT());
		values.put(DBConstants.USER_NAME, model.getUserName());
		values.put(DBConstants.CREATED_ON, model.getCreatedOn());
		values.put(DBConstants.VALUE, model.getVALUE());
		return values;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	

}
