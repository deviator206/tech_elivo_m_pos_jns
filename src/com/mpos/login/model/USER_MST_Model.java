/*
 * Copyright (C) 2013  All Rights Reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.login.model;

import java.io.Serializable;

import android.content.ContentValues;

import com.mpos.framework.common.DBConstants;

/**
 * Description- 
 * 

 */

public class USER_MST_Model implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String USR_SCNCD;
	private String USR_NM;
	private String USR_PWD;
	private String USR_BIO;
	private String USR_CRTN_DATE;
	private String USR_FULL_NAME;
	private String USR_GRP_ID;
	private String USR_ID;
	private String USR_MODFD_BY;
	
	public String getUSR_SCNCD() {
		return USR_SCNCD;
	}
	public void setUSR_SCNCD(String uSR_SCNCD) {
		USR_SCNCD = uSR_SCNCD;
	}
	public String getUSR_NM() {
		return USR_NM;
	}
	public void setUSR_NM(String uSR_NM) {
		USR_NM = uSR_NM;
	}
	public String getUSR_PWD() {
		return USR_PWD;
	}
	public void setUSR_PWD(String uSR_PWD) {
		USR_PWD = uSR_PWD;
	}
	public String getUSR_BIO() {
		return USR_BIO;
	}
	public void setUSR_BIO(String uSR_BIO) {
		USR_BIO = uSR_BIO;
	}
	public String getUSR_CRTN_DATE() {
		return USR_CRTN_DATE;
	}
	public void setUSR_CRTN_DATE(String uSR_CRTN_DATE) {
		USR_CRTN_DATE = uSR_CRTN_DATE;
	}
	public String getUSR_FULL_NAME() {
		return USR_FULL_NAME;
	}
	public void setUSR_FULL_NAME(String uSR_FULL_NAME) {
		USR_FULL_NAME = uSR_FULL_NAME;
	}
	public String getUSR_GRP_ID() {
		return USR_GRP_ID;
	}
	public void setUSR_GRP_ID(String uSR_GRP_ID) {
		USR_GRP_ID = uSR_GRP_ID;
	}
	public String getUSR_ID() {
		return USR_ID;
	}
	public void setUSR_ID(String uSR_ID) {
		USR_ID = uSR_ID;
	}
	public String getUSR_MODFD_BY() {
		return USR_MODFD_BY;
	}
	public void setUSR_MODFD_BY(String uSR_MODFD_BY) {
		USR_MODFD_BY = uSR_MODFD_BY;
	}
	@Override
	public String toString() {
		return "USER_MST_Model [USR_SCNCD=" + USR_SCNCD + ", USR_NM=" + USR_NM
				+ ", USR_PWD=" + USR_PWD + ", USR_BIO=" + USR_BIO
				+ ", USR_CRTN_DATE=" + USR_CRTN_DATE + ", USR_FULL_NAME="
				+ USR_FULL_NAME + ", USR_GRP_ID=" + USR_GRP_ID + ", USR_ID="
				+ USR_ID + ", USR_MODFD_BY=" + USR_MODFD_BY + "]";
	}
	
	
	public static ContentValues getcontentvalues(USER_MST_Model model) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.USR_BIO, model.getUSR_BIO());
		values.put(DBConstants.USR_CRTN_DATE,
				model.getUSR_CRTN_DATE());
		values.put(DBConstants.USR_FULL_NAME, model.getUSR_FULL_NAME());
		values.put(DBConstants.USR_GRP_ID, model.getUSR_GRP_ID());
		values.put(DBConstants.USR_ID, model.getUSR_ID());
		values.put(DBConstants.USR_MODFD_BY, model.getUSR_MODFD_BY());
		values.put(DBConstants.USR_NM, model.getUSR_NM());
		values.put(DBConstants.USR_PWD, model.getUSR_PWD());
		values.put(DBConstants.USR_SCNCD, model.getUSR_SCNCD());
		return values;
	}

}
